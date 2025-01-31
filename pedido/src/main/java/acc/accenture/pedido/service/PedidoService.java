package acc.accenture.pedido.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import acc.accenture.pedido.config.PedidoProducer;
import acc.accenture.pedido.config.RabbitMQConfig;
import acc.accenture.pedido.dtos.Email;
import acc.accenture.pedido.dtos.PedidoItemDTO;
import acc.accenture.pedido.dtos.PedidoItemResponse;
import acc.accenture.pedido.dtos.PedidoRequest;
import acc.accenture.pedido.dtos.PedidoResponse;
import acc.accenture.pedido.model.Pedido;
import acc.accenture.pedido.model.PedidoItem;
import acc.accenture.pedido.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {

  private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);
  private final PedidoRepository pedidoRepository;
  private final PedidoProducer pedidoProducer;

  public PedidoService(PedidoRepository pedidoRepository, PedidoProducer pedidoProducer) {
    this.pedidoRepository = pedidoRepository;
    this.pedidoProducer = pedidoProducer;
  }

  @Transactional
  public PedidoResponse criarPedido(PedidoRequest request) {
    logger.debug("Criando pedido: {}", request.getDescricao());

    Assert.hasText(request.getDescricao(), "A descrição do pedido não pode ser vazia.");
    Assert.notNull(request.getValor(), "O valor do pedido não pode ser nulo.");
    Assert.isTrue(request.getValor().compareTo(BigDecimal.ZERO) > 0, "O valor do pedido deve ser maior que zero.");
    Assert.notNull(request.getVendedorId(), "O ID do vendedor não pode ser nulo.");
    Assert.notNull(request.getClienteId(), "O ID do cliente não pode ser nulo.");
    Assert.notEmpty(request.getItens(), "O pedido deve ter pelo menos um item.");

    Pedido pedido = new Pedido();
    pedido.setDescricao(request.getDescricao());
    pedido.setValor(request.getValor());
    pedido.setDataHora(LocalDateTime.now());
    pedido.setVendedorId(request.getVendedorId());
    pedido.setClienteId(request.getClienteId());

    List<PedidoItem> itens = request.getItens().stream()
        .map(itemRequest -> {
          // Verificar o estoque antes de criar o item
          PedidoItemDTO produto = pedidoRepository.checagemEstoque(itemRequest.getProdutoId());
          if (produto.getQuantidade() < itemRequest.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente para o produto ID: " + itemRequest.getProdutoId());
          }

          PedidoItem item = new PedidoItem(pedido, itemRequest.getProdutoId(), itemRequest.getQuantidade());

          // Atualizar estoque
          pedidoRepository.atualizarEstoque(itemRequest.getProdutoId(), itemRequest.getQuantidade());

          return item;
        })
        .collect(Collectors.toList());

    pedido.setItens(itens);

    Pedido savedPedido = pedidoRepository.save(pedido);
    pedidoRepository.insertHistorico(savedPedido.getId());
    logger.info("Pedido salvo com ID: {}", savedPedido.getId());

    // Enviar o pedido ao RabbitMQ
    pedidoProducer.enviarPedido(savedPedido);

    // Enviar e-mail de confirmação
    enviarEmailConfirmacaoPedido(savedPedido);

    return mapToResponse(savedPedido);
  }

  private void enviarEmailConfirmacaoPedido(Pedido pedido) {
    // Usar o método do repositório para obter o e-mail do cliente
    String emailCliente = pedidoRepository.pegarEmail(pedido.getClienteId()).get();

    if (emailCliente == null) {
      logger.warn("Não foi possível enviar o e-mail: Cliente ou e-mail não disponível.");
      return;
    }

    String titulo = "Confirmação de Pedido #" + pedido.getId();
    String conteudo = "Olá, \n\n" + "Seu pedido foi realizado com sucesso!\n"
        + "Descrição: " + pedido.getDescricao() + "\n"
        + "Valor: R$ " + pedido.getValor() + "\n"
        + "Data: " + pedido.getDataHora() + "\n\n"
        + "Obrigado por comprar conosco!";

    Random random = new Random();
    int confirmationCode = random.nextInt(900000) + 100000;
    Email emailDTO = new Email(emailCliente, titulo, conteudo, confirmationCode);

    // Enviar o e-mail com um método específico para e-mails
    try {
      pedidoProducer.enviarEmail(RabbitMQConfig.PEDIDO_EMAIL, "Equipe4.email", emailDTO);
      logger.info("E-mail de confirmação enviado para: {}", emailCliente);
    } catch (Exception e) {
      logger.error("Erro ao enviar e-mail de confirmação: {}", e.getMessage(), e);
    }
  }

  public List<PedidoResponse> listarPedidos() {
    return pedidoRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  public Page<PedidoResponse> listarPedidos(Pageable pageable) {
    return pedidoRepository.findAll(pageable).map(this::mapToResponse);
  }

  public ResponseEntity<PedidoResponse> buscarPedidoPorId(Long id) {
    Pedido pedido = pedidoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado para o ID: " + id));

    return ResponseEntity.ok(mapToResponse(pedido));
  }

  private PedidoResponse mapToResponse(Pedido pedido) {
    PedidoResponse response = new PedidoResponse();
    response.setId(pedido.getId());
    response.setDescricao(pedido.getDescricao());
    response.setValor(pedido.getValor());
    response.setDataHora(pedido.getDataHora());
    response.setVendedorId(pedido.getVendedorId());
    response.setClienteId(pedido.getClienteId());

    List<PedidoItemResponse> itemResponses = pedido.getItens().stream()
        .map(item -> new PedidoItemResponse(item.getProdutoId(), item.getQuantidade()))
        .collect(Collectors.toList());

    response.setItens(itemResponses);
    return response;
  }

  @Transactional
  public void atualizarStatusPedido(Long idPedido, Long idCliente, Long novoStatus) {
    if (!pedidoRepository.existsById(idPedido)) {
      throw new EntityNotFoundException("Pedido não encontrado para o ID: " + idPedido);
    }

    logger.info("Atualizando status do pedido ID: {} para status {}", idPedido, novoStatus);
    pedidoRepository.atualizarStatusPedido(idCliente.intValue(), idPedido.intValue(), novoStatus);
    logger.info("Status do pedido ID: {} atualizado com sucesso!", idPedido);
  }

}

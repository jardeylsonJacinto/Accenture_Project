package acc.accenture.pedido.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
  private final RabbitTemplate rabbitTemplate;

  public PedidoService(PedidoRepository pedidoRepository, RabbitTemplate rabbitTemplate) {
    this.pedidoRepository = pedidoRepository;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Transactional
  public PedidoResponse criarPedido(PedidoRequest request) {
    logger.debug("Criando pedido: {}", request.getDescricao());

    // Validações obrigatórias
    Assert.hasText(request.getDescricao(), "A descrição do pedido não pode ser vazia.");
    Assert.notNull(request.getValor(), "O valor do pedido não pode ser nulo.");
    Assert.isTrue(request.getValor().compareTo(BigDecimal.ZERO) > 0, "O valor do pedido deve ser maior que zero.");
    Assert.notNull(request.getVendedorId(), "O ID do vendedor não pode ser nulo.");
    Assert.notNull(request.getClienteId(), "O ID do cliente não pode ser nulo.");
    Assert.notEmpty(request.getItens(), "O pedido deve ter pelo menos um item.");

    // Criando pedido
    Pedido pedido = new Pedido();
    pedido.setDescricao(request.getDescricao());
    pedido.setValor(request.getValor());
    pedido.setDataHora(LocalDateTime.now());
    pedido.setVendedorId(request.getVendedorId());
    pedido.setClienteId(request.getClienteId());

    // Mapeando itens
    List<PedidoItem> itens = request.getItens().stream()
        .map(itemRequest -> new PedidoItem(pedido, itemRequest.getProdutoId(), itemRequest.getQuantidade()))
        .collect(Collectors.toList());

    pedido.setItens(itens);

    // Salvando o pedido
    Pedido savedPedido = pedidoRepository.save(pedido);
    logger.info("Pedido salvo com ID: {}", savedPedido.getId());

    // Enviando mensagem ao RabbitMQ com retry
    enviarMensagemRabbit(savedPedido);

    return mapToResponse(savedPedido);
  }

  private void enviarMensagemRabbit(Pedido pedido) {
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3)); // Tenta 3 vezes antes de falhar

    try {
      retryTemplate.execute(context -> {
        rabbitTemplate.convertAndSend("pedido.exchange", "pedido.created", pedido);
        logger.info("Mensagem enviada ao RabbitMQ para pedido ID: {}", pedido.getId());
        return null;
      });
    } catch (Exception e) {
      logger.error("Erro ao enviar mensagem ao RabbitMQ após 3 tentativas", e);
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
}

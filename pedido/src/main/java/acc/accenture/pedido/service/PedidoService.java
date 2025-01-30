package acc.accenture.pedido.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import acc.accenture.pedido.dtos.PedidoRequest;
import acc.accenture.pedido.dtos.PedidoResponse;
import acc.accenture.pedido.model.Pedido;
import acc.accenture.pedido.repository.PedidoRepository;

@Service
public class PedidoService {

  private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);
  private final PedidoRepository pedidoRepository;
  private final RabbitTemplate rabbitTemplate;

  public PedidoService(PedidoRepository pedidoRepository, RabbitTemplate rabbitTemplate) {
    this.pedidoRepository = pedidoRepository;
    this.rabbitTemplate = rabbitTemplate;
  }

  public PedidoResponse criarPedido(PedidoRequest request) {
    logger.debug("Criando pedido com descrição: " + request.getDescricao());

    Pedido pedido = new Pedido();
    pedido.setDescricao(request.getDescricao());
    pedido.setValor(request.getValor());
    pedido.setDataHora(LocalDateTime.now());
    // Map Vendedor, Cliente, and Items from request

    Pedido savedPedido = pedidoRepository.save(pedido);
    logger.debug("Pedido salvo com ID: " + savedPedido.getId());

    try {
      rabbitTemplate.convertAndSend("pedido.exchange", "pedido.created", savedPedido);
      logger.debug("Mensagem enviada ao RabbitMQ para pedido ID: " + savedPedido.getId());
    } catch (Exception e) {
      logger.error("Erro ao enviar mensagem ao RabbitMQ", e);
    }

    return mapToResponse(savedPedido);
  }

  private PedidoResponse mapToResponse(Pedido pedido) {
    PedidoResponse response = new PedidoResponse();
    response.setId(pedido.getId());
    response.setDescricao(pedido.getDescricao());
    response.setValor(pedido.getValor());
    response.setDataHora(pedido.getDataHora());
    // Map Vendedor, Cliente, and Items to response
    return response;
  }

  public List<PedidoResponse> listarPedidos() {
    return pedidoRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  public PedidoResponse buscarPedidoPorId(Long id) {
    Pedido pedido = pedidoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    return mapToResponse(pedido);
  }
}

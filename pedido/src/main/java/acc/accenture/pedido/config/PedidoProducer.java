package acc.accenture.pedido.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import acc.accenture.pedido.dtos.Email;
import acc.accenture.pedido.model.Pedido;

@Component
public class PedidoProducer {
  private static final Logger logger = LoggerFactory.getLogger(PedidoProducer.class);
  private final RabbitTemplate rabbitTemplate;

  public PedidoProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void enviarPedido(Pedido pedido) {
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3)); // Tenta 3 vezes antes de falhar

    try {
      retryTemplate.execute(context -> {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PEDIDO_EXCHANGE, "pedido.created", pedido);
        logger.info("Pedido enviado para RabbitMQ - ID: {}", pedido.getId());
        return null;
      });
    } catch (Exception e) {
      logger.error("Erro ao enviar pedido ao RabbitMQ após 3 tentativas", e);
    }
  }

  // Método exclusivo para o envio de e-mails
  public void enviarEmail(String exchangeEmail, String routingKey, Email emailDTO) {
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3)); // Tenta 3 vezes antes de falhar

    try {
      retryTemplate.execute(context -> {
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(emailDTO);
        rabbitTemplate.convertAndSend(exchangeEmail, routingKey, queuePayloadString);
        logger.info("E-mail enviado para RabbitMQ - Exchange: {}, RoutingKey: {}", exchangeEmail, routingKey);
        return null;
      });
    } catch (Exception e) {
      logger.error("Erro ao enviar e-mail ao RabbitMQ após 3 tentativas", e);
    }
  }
}

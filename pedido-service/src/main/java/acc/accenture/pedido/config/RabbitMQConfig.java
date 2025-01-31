package acc.accenture.pedido.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String PEDIDO_EXCHANGE = "pedido.exchange.grupo4";
  public static final String PEDIDO_EMAIL = "Equipe4.email"; // Nome do Exchange de E-mail
  public static final String PEDIDO_QUEUE = "pedido.queue.grupo4";

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(PEDIDO_EXCHANGE);
  }

  @Bean
  public DirectExchange exchangeEmail() {
    return new DirectExchange(PEDIDO_EMAIL); // Exchange de E-mail
  }

  @Bean
  public Queue queue() {
    return new Queue(PEDIDO_QUEUE, true);
  }

  @Bean
  public Binding pedidosBinding(Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("pedido.created");
  }

  // Fila e binding para e-mail
  @Bean
  public Queue emailQueue() {
    return new Queue("Equipe4.email", true); // Nome correto da fila de e-mail
  }

  @Bean
  public Binding emailBinding(Queue emailQueue, DirectExchange exchangeEmail) {
    return BindingBuilder.bind(emailQueue).to(exchangeEmail).with("Equipe4.email"); // RoutingKey de E-mail
  }

}

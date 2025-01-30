package acc.accenture.pedido.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String PEDIDO_EXCHANGE = "pedido.exchange";
  public static final String PEDIDO_QUEUE = "pedido.queue";

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(PEDIDO_EXCHANGE);
  }

  @Bean
  public Queue queue() {
    return new Queue(PEDIDO_QUEUE, true);
  }

  @Bean
  public Binding pedidosBinding(Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("pedido.created");
  }
}

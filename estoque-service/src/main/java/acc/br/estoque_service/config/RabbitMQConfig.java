package acc.br.estoque_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "fila_pedidos";
    public static final String EXCHANGE_NAME = "exchange_pedidos";
    public static final String ROUTING_KEY = "routing_key_pedido";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);  // 'true' significa que a fila será durável, sobrevivendo a reinicializações do broker
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        // A fila será vinculada ao exchange com a chave de roteamento definida
        return new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, EXCHANGE_NAME, ROUTING_KEY, null);
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                                  MessageListener messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);  // A fila onde as mensagens serão consumidas
        container.setMessageListener(messageListener);  // O listener que processará as mensagens
        return container;
    }
}

package acc.br.pagamento_service.config;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import acc.br.pagamento_service.listener.PagamentoRegisterListener;

@Configuration
public class RabbitMQConfig {

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("Equipe4.pagamento");

        if (listenerAdapter != null) {
            container.setMessageListener(listenerAdapter);
        }

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(PagamentoRegisterListener listener) {
        return new MessageListenerAdapter(listener, "onMessageReceived");
    }
}

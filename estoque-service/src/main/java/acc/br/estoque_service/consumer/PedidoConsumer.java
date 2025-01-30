package acc.br.estoque_service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import acc.br.estoque_service.config.RabbitMQConfig;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumirMensagem(String mensagem) {
        // Processamento da mensagem recebida
        System.out.println("Mensagem recebida: " + mensagem);
        // Aqui você pode adicionar lógica para processar a mensagem
    }
}

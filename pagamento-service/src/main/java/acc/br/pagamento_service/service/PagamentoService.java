package acc.br.pagamento_service.service;

import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.pagamento_service.model.Email;
import acc.br.pagamento_service.model.Pagamento;
import acc.br.pagamento_service.repository.PagamentoRepository;

@Service
public class PagamentoService {
	
	static String QUEUE_NAME = "Equipe4.email";

    private final RabbitTemplate rabbitTemplate;

    public PagamentoService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public void confirmarPagamento(Pagamento pagamento) throws JsonProcessingException {
		
		Email email = new Email();
		Random random = new Random();
        int confirmationCode = random.nextInt(900000) + 100000;
        pagamentoRepository.atualizarDataPagamento(pagamento.getIdCliente(), pagamento.getIdPedido());
        
        email.setPara(pagamentoRepository.pegarEmail(pagamento.getIdCliente()).get());
        email.setTitulo("Pagamento do pedido " + pagamento.getIdPedido());
        email.setConteudo("Pagamento referente ao pedido " + pagamento.getIdPedido() + " foi devidamente cadastrado;");
        email.setConfirmationCode(confirmationCode);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(email);

        rabbitTemplate.convertAndSend(QUEUE_NAME, queuePayloadString);
        
        
	}
}

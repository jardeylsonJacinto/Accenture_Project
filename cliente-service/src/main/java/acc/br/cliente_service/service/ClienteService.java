package acc.br.cliente_service.service;

import java.util.List;
import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.cliente_service.dto.RastreioDTO;
import acc.br.cliente_service.model.Cliente;
import acc.br.cliente_service.model.Pagamento;
import acc.br.cliente_service.repository.ClienteRepository;

@Service
public class ClienteService {
	
    static String QUEUE_NAME = "Equipe4.pagamento";

    private final RabbitTemplate rabbitTemplate;

    public ClienteService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> obterTodos(){
		return clienteRepository.findAll();
	}
	
	public Cliente obterUm(Integer id) {
		return clienteRepository.findById(id).get();
	}
	
	public void deletar(Integer id) {
		clienteRepository.deleteById(id);
	}
	
	public Cliente criar(Cliente cliente) {
		cliente.setIdCliente(null);
		return clienteRepository.save(cliente);
	}
	
	public Cliente alterar(Integer id, Cliente cliente) {
		cliente.setIdCliente(id);
		return clienteRepository.save(cliente);
	}
	
	public List<RastreioDTO> obterTodosRastreios(Integer idCliente) {
		return clienteRepository.obterTodosRastreios(idCliente);
	}
	
	public RastreioDTO obterRastreio(Integer idCliente,Integer idPedido) {
		return clienteRepository.obterRastreio(idCliente,idPedido).get();
	}
	
	public void realizarPagamento(Integer idCliente, Integer idPedido) throws JsonProcessingException {
		
        Random random = new Random();
        int confirmationCode = random.nextInt(900000) + 100000;
        Pagamento novoPagamento = new Pagamento();
        
        novoPagamento.setIdCliente(idCliente);
        novoPagamento.setIdPedido(idPedido);
        novoPagamento.setConfirmationCode(confirmationCode);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(novoPagamento);

        rabbitTemplate.convertAndSend(QUEUE_NAME, queuePayloadString);
	}
	
	
}

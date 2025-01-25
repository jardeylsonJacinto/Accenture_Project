package acc.br.cliente_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acc.br.cliente_service.model.Cliente;
import acc.br.cliente_service.repository.ClienteRepository;

@Service
public class ClienteService {

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
	
	
}

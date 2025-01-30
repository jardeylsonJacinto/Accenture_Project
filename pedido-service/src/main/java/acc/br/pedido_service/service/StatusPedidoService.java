package acc.br.pedido_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acc.br.pedido_service.model.StatusPedido;
import acc.br.pedido_service.repository.StatusPedidoRepository;

@Service
public class StatusPedidoService {
	
	@Autowired
	private StatusPedidoRepository statusPedidoRepository;
	
	public List<StatusPedido> obterTodos(){
		return statusPedidoRepository.findAll();
	}
	
	public StatusPedido obterUm(Integer id) {
		return statusPedidoRepository.findById(id).get();
	}
	
	public void deletar(Integer id) {
		statusPedidoRepository.deleteById(id);
	}
	
	public StatusPedido criarAlterar(StatusPedido status) {
		return statusPedidoRepository.save(status);
	}

}

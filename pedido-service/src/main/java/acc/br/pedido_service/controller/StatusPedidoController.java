package acc.br.pedido_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import acc.br.pedido_service.model.StatusPedido;
import acc.br.pedido_service.service.StatusPedidoService;

@RestController
@RequestMapping("/api/statuspedido")
public class StatusPedidoController {
	
	@Autowired
	private StatusPedidoService statusPedidoService;
	
	@GetMapping
	public List<StatusPedido> obterTodos(){
		return statusPedidoService.obterTodos();
	}
	
	@GetMapping("/{id}")
	public StatusPedido obterUm(@PathVariable Integer id) {
		return statusPedidoService.obterUm(id);
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Integer id) {
		statusPedidoService.deletar(id);
	}
	
	@PostMapping
	public StatusPedido criar(@RequestBody StatusPedido status) {
		return statusPedidoService.criarAlterar(status);
	}

}

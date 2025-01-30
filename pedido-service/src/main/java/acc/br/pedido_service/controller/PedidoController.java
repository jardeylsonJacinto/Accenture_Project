package acc.br.pedido_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import acc.br.pedido_service.dto.PedidoDTO;
import acc.br.pedido_service.model.Pedido;
import acc.br.pedido_service.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping
	public List<Pedido> obterTodos(){
		return pedidoService.obterTodos();
	}
	
	@GetMapping("/{id}")
	public PedidoDTO obterUm(@PathVariable Integer id) {
		return new PedidoDTO();//pedidoService.obterUm(id);
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Integer id) {
		pedidoService.deletar(id);
	}
	
	@PostMapping
	public Pedido criar(@RequestBody PedidoDTO pedido) {
		System.out.println(pedido.toString());
		return  pedidoService.criar(pedido);
	}
	
	@PutMapping("/{id}")
	public Pedido alterar(@RequestBody Pedido pedido, @PathVariable Integer id) {
		return pedidoService.alterar(pedido, id);
	}

}

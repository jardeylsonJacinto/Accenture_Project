package acc.br.cliente_service.controller;

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

import acc.br.cliente_service.model.Cliente;
import acc.br.cliente_service.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Operation(summary = "Retorna todos os clientes")
	@GetMapping
	public List<Cliente> obterTodos(){
		return clienteService.obterTodos();
	}
	
	@Operation(summary = "Retorna um cliente")
	@GetMapping("/{id}")
	public Cliente obterUm(@PathVariable Integer id) {
		return clienteService.obterUm(id);
	}
	
	@Operation(summary = "Deleta um cliente")
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Integer id) {
		clienteService.deletar(id);
	}
	
	@Operation(summary = "Cria um cliente novo")
	@PostMapping
	public Cliente criar(@RequestBody Cliente cliente) {
		return clienteService.criar(cliente);
	}
	
	@Operation(summary = "Altera um cliente")
	@PutMapping("/{id}")
	public Cliente alterar(@RequestBody Cliente cliente, @PathVariable Integer id) {
		return clienteService.alterar(id, cliente);
	}
}

package br.com.vendedor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vendedor.model.Vendedor;
import br.com.vendedor.service.VendedorService;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {
	
	@Autowired
	private VendedorService vendedorService;
	
	@GetMapping
	public List<Vendedor> obterTodos(){
		return vendedorService.obterTodos();
	}
	
	@GetMapping("/{id}")
	public Optional<Vendedor> obterUm(@PathVariable Integer id){
		return vendedorService.obterUm(id);
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Integer id) {
		vendedorService.deleta(id);
	}
	
	@PostMapping
	public Vendedor criar(@RequestBody Vendedor vendedor) {
		System.out.println(vendedor.getVendedorNome());
		System.out.println(vendedor.getVendedorSetor());
		return vendedorService.criar(vendedor);
	}
	
	@PutMapping("/{id}")
	public Vendedor alterar(@RequestBody Vendedor vendedor, @PathVariable Integer id) {
		return vendedorService.alterar(vendedor, id);
	}

}

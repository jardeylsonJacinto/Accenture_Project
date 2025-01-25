package acc.br.estoque_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import acc.br.estoque_service.model.Produto;
import acc.br.estoque_service.service.ProdutoService;

@RestController
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public List<Produto> obterTodos(){
		return produtoService.obterTodos();
	}
	
	@GetMapping("/{id}")
	public Produto obterUm(@PathVariable Integer id) {
		return produtoService.obterUm(id);
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Integer id) {
		produtoService.deletar(id);
	}
	
	@PostMapping
	public Produto criar(@RequestBody Produto produto) {
		return produtoService.criar(produto);
	}
	
	@PutMapping("/{id}")
	public Produto alterar(@RequestBody Produto produto, @PathVariable Integer id) {
		return produtoService.alterar(produto, id);
	}

}

package acc.br.estoque_service.controller;


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

import acc.br.estoque_service.model.Produto;
import acc.br.estoque_service.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Operation(summary = "Retorna todos os produtos")
	@GetMapping
	public List<Produto> obterTodos(){
		return produtoService.obterTodos();
	}
	
	@Operation(summary = "Retorna um produto")
	@GetMapping("/{id}")
	public Produto obterUm(@PathVariable Integer id) {
		return produtoService.obterUm(id);
	}
	
	@Operation(summary = "Deleta um produto")
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Integer id) {
		produtoService.deletar(id);
	}
	
	@Operation(summary = "Cria um produto")
	@PostMapping
	public Produto criar(@RequestBody Produto produto) {
		return produtoService.criar(produto);
	}
	
	@Operation(summary = "Altera um produto")
	@PutMapping("/{id}")
	public Produto alterar(@RequestBody Produto produto, @PathVariable Integer id) {
		return produtoService.alterar(produto, id);
	}

}

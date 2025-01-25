package acc.br.estoque_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acc.br.estoque_service.model.Produto;
import acc.br.estoque_service.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> obterTodos(){
		return produtoRepository.findAll();
	}
	
	public Produto obterUm(Integer id) {
		return produtoRepository.findById(id).get();
	}
	
	public void deletar(Integer id) {
		produtoRepository.deleteById(id);
	}
	
	public Produto criar(Produto produto) {
		produto.setIdProduto(null);
		return produtoRepository.save(produto);
	}
	
	public Produto alterar(Produto produto, Integer id) {
		produto.setIdProduto(id);
		return produtoRepository.save(produto);
	}
}

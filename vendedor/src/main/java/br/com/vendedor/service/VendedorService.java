package br.com.vendedor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vendedor.model.Vendedor;
import br.com.vendedor.repository.VendedorRepository;

@Service
public class VendedorService {

	@Autowired
	private VendedorRepository vendedorRepository;
	
	/**
	 * Metodo que retorna todos os registros da tabela de vendedores
	 * @return lista de vendedores
	 */
	public List<Vendedor> obterTodos(){
		return vendedorRepository.findAll();
	}
	
	/**
	 * Metodo responsavel por trazer um vendedor especifico
	 * @param id dp vendedor
	 * @return Vendedor
	 */
	public Optional<Vendedor> obterUm(Integer id){
		return vendedorRepository.findById(id);
	}
	
	/**
	 * Metodo que exclui um vendedor especifico
	 * @param id do vendedor
	 */
	public void deleta(Integer id) {
		vendedorRepository.deleteById(id);
	}
	
	/**
	 * Metodo que cria um registro de vendedor
	 * @param vendedor que ira ser criado
	 * @return vendedor criado
	 */
	public Vendedor criar(Vendedor vendedor) {
		vendedor.setIdVendedor(null);
		return vendedorRepository.save(vendedor);
	}
	
	/**
	 * Metodo responsavel por alterar um registro de um vendedor especifico
	 * @param vendedor que ira ser alterado
	 * @param id do vendedor 
	 * @return vendedor alterado
	 */
	public Vendedor alterar(Vendedor vendedor, Integer id) {
		vendedor.setIdVendedor(id);
		return vendedorRepository.save(vendedor);
	}
}
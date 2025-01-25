package acc.br.vendedor.controller;

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

import acc.br.vendedor.dtos.VendedorDTO;
import acc.br.vendedor.model.Vendedor;
import acc.br.vendedor.service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {
	
	@Autowired
	private VendedorService vendedorService;
	
	@Operation(summary = "Retorna todos os vendedores")
	@GetMapping
	public List<Vendedor> obterTodos(){
		return vendedorService.obterTodos();
	}
	
	@Operation(summary = "Retorna um vendedor especifico")
	@GetMapping("/{id}")
	public Optional<Vendedor> obterUm(@PathVariable Integer id){
		return vendedorService.obterUm(id);
	}
	
	@Operation(summary = "Deleta um vendedor")
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Integer id) {
		vendedorService.deleta(id);
	}
	
	@Operation(summary = "Cria um registro de vendedor")
	@PostMapping
	public Vendedor criar(@RequestBody VendedorDTO vendedorDTO) {
		
		Vendedor vendedor = new Vendedor();
		vendedor.setVendedorNome(vendedorDTO.getVendedorNome());
		vendedor.setVendedorSetor(vendedorDTO.getVendedorSetor());
		
		return vendedorService.criar(vendedor);
	}
	
	@Operation(summary = "Altera um registro de vendedor")
	@PutMapping("/{id}")
	public Vendedor alterar(@RequestBody VendedorDTO vendedorDTO, @PathVariable Integer id) {
		Vendedor vendedor = new Vendedor();
		vendedor.setVendedorNome(vendedorDTO.getVendedorNome());
		vendedor.setVendedorSetor(vendedorDTO.getVendedorSetor());
		return vendedorService.alterar(vendedor, id);
	}

}

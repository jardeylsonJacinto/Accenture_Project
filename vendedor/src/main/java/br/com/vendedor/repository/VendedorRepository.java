package br.com.vendedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendedor.model.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer>{

}

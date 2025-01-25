package acc.br.vendedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import acc.br.vendedor.model.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer>{

}

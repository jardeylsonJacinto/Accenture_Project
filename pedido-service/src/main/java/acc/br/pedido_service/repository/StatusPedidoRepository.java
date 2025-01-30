package acc.br.pedido_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import acc.br.pedido_service.model.StatusPedido;

@Repository
public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Integer>{

}

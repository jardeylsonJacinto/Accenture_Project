package acc.br.cliente_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acc.br.cliente_service.dto.RastreioDTO;
import acc.br.cliente_service.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	@Query(value = "select p.Pedido_idPedido as idPedido"
			+ ", sp.statusPedidoDescricao as statusDescricao"
			+ ", p.datahorastatuspedido as dataStatus"
			+ ",p.datahorapagamento as dataPagamento "
			+ "from pedido_historico_status p "
			+ "inner join statuspedido sp on p.statusPedido_idstatusPedido = sp.idstatusPedido "
			+ "where p.Pedido_idPedido in "
			+ "(select idPedido from pedido where Cliente_idCliente = :idCliente)" , nativeQuery = true)
	List<RastreioDTO> obterTodosRastreios(@Param("idCliente") Integer idCliente);
	
	@Query(value = "select p.Pedido_idPedido as idPedido"
			+ ", sp.statusPedidoDescricao as statusDescricao"
			+ ", p.datahorastatuspedido as dataStatus"
			+ ",p.datahorapagamento as dataPagamento "
			+ "from pedido_historico_status p "
			+ "inner join statuspedido sp on p.statusPedido_idstatusPedido = sp.idstatusPedido "
			+ "where p.Pedido_idPedido in "
			+ "(select idPedido from pedido where Cliente_idCliente = :idCliente and idPedido = :idPedido)" , nativeQuery = true)
	Optional<RastreioDTO> obterRastreio(@Param("idCliente") Integer idCliente,@Param("idPedido") Integer idPedido);

}

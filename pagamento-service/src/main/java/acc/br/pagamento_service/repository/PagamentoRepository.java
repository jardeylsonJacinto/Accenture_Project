package acc.br.pagamento_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import acc.br.pagamento_service.model.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "update pedido_historico_status set DataHoraPagamento = now() where pedido_idpedido = (select idPedido from pedido where Cliente_idCliente = :idCliente and idPedido = :idPedido)", nativeQuery = true)
	void atualizarDataPagamento(@Param("idCliente") Integer idCliente, @Param("idPedido") Integer idPedido);

}

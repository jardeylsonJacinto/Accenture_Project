package acc.br.pagamento_service.repository;

import java.util.Optional;

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
	@Query(value = "update pedido_historico_status set DataHoraPagamento = now(), statusPedido_idstatusPedido = 2, dataHoraStatusPedido = now() where pedido_idpedido = (select idPedido from pedido where Cliente_idCliente = :idCliente and idPedido = :idPedido)", nativeQuery = true)
	void atualizarDataPagamento(@Param("idCliente") Integer idCliente, @Param("idPedido") Integer idPedido);
	
	@Query(value = "select clienteEmail from cliente where idCliente = :idCliente", nativeQuery = true)
	Optional<String> pegarEmail(@Param("idCliente") Integer idCliente);

}

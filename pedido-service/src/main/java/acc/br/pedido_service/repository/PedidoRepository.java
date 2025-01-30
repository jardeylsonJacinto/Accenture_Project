package acc.br.pedido_service.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acc.br.pedido_service.dto.PedidoItemDTO;
import acc.br.pedido_service.model.Pedido;
import jakarta.transaction.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	@Query(value = "select ProdutoEstoque as quantidade, idProduto from Produto where idProduto = :id", nativeQuery = true)
	PedidoItemDTO checagemEstoque(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query(value = "update Produto set ProdutoEstoque = ProdutoEstoque - :qnt where idProduto = :id", nativeQuery = true)
	void atualizarEstoque(@Param("id") Integer id,@Param("qnt") Integer qnt);
	
	@Query(value = "Select ProdutoValor from produto where idProduto = :id", nativeQuery = true)
	Integer pegarValor(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query(value = "insert into pedido_item_produto values(:idPedido,:ProdutoQuantidade,:idProduto)", nativeQuery = true)
	void insertPedidoItemProduto(@Param("idPedido") Integer idPedido, @Param("ProdutoQuantidade") Integer quantidade, @Param("idProduto") Integer idProduto);

	@Transactional
	@Modifying
	@Query(value = "insert into pedido_historico_status(Pedido_idPedido,statusPedido_idstatusPedido,DataHoraStatusPedido) values(:idPedido,1,now())", nativeQuery = true)
	void insertPedidoHistorico(@Param("idPedido") Integer idPedido);
	
	@Transactional
	@Modifying
	@Query(value = "update pedido_historico_status set statusPedido_idstatusPedido = :status, DataHoraStatusPedido = now() where Pedido_idPedido = :idPedido", nativeQuery = true)
	void atualizarStatus(@Param("idPedido") Integer idPedido, @Param("status") Integer status);
	
	
}

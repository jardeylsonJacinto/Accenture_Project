package acc.accenture.pedido.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import acc.accenture.pedido.dtos.PedidoItemDTO;
import acc.accenture.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
  @Query(value = "select clienteEmail from cliente where idCliente = :idCliente", nativeQuery = true)
  Optional<String> pegarEmail(@Param("idCliente") Long idCliente);

  @Transactional
  @Modifying
  @Query(value = "update pedido_historico_status set statusPedido_idstatusPedido = :statusPedido, dataHoraStatusPedido = now() where pedido_idpedido = (select idPedido from pedido where Cliente_idCliente = :idCliente and idPedido = :idPedido)", nativeQuery = true)
  void atualizarStatusPedido(@Param("idCliente") Integer idCliente, @Param("idPedido") Integer idPedido,
      @Param("statusPedido") Long statusPedido);

  @Transactional
  @Modifying
  @Query(value = "insert into pedido_historico_status(Pedido_idPedido, statusPedido_idstatusPedido, DataHoraStatusPedido) values (:idPedido,1, now());", nativeQuery = true)
  void insertHistorico(@Param("idPedido") Long idPedido);

  @Query(value = "select ProdutoEstoque as quantidade, idProduto from Produto where idProduto = :id", nativeQuery = true)
  PedidoItemDTO checagemEstoque(@Param("id") Long id);

  @Transactional
  @Modifying
  @Query(value = "update Produto set ProdutoEstoque = ProdutoEstoque - :qnt where idProduto = :id", nativeQuery = true)
  void atualizarEstoque(@Param("id") Long id, @Param("qnt") Integer qnt);
}
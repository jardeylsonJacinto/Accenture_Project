package acc.br.relatorio_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acc.br.relatorio_service.dto.RelatorioPagamentos;
import acc.br.relatorio_service.dto.RelatorioVendasPorPeriodo;
import acc.br.relatorio_service.dto.RelatorioVendasPorProduto;
import acc.br.relatorio_service.dto.RelatorioVendasPorVendedor;
import acc.br.relatorio_service.model.EntradaRelatorio;

@Repository
public interface RelatorioRepository extends JpaRepository<EntradaRelatorio, String> {
	
	@Query(value = "SELECT CONCAT(YEAR(p.PedidoDataHora),"
			+ " '-', LPAD(MONTH(p.PedidoDataHora), 2, '0')) AS AnoMes"
			+ ", SUM(pi.ProdutoQuantidade * pr.ProdutoValor) + 0.05 AS TotalVendas "
			+ "FROM  Pedido p "
			+ "JOIN Pedido_item_Produto pi ON p.idPedido = pi.Pedido_idPedido "
			+ "JOIN Produto pr ON pi.Produto_idProduto = pr.idProduto "
			+ "WHERE p.PedidoDataHora BETWEEN :dataInicial AND :dataFinal "
			+ "GROUP BY AnoMes ORDER BY AnoMes DESC", nativeQuery = true)
	List<RelatorioVendasPorPeriodo> montagemRelatorioVendas(@Param("dataInicial") String dataInicial, @Param("dataFinal") String dataFinal);
	
	
	@Query(value = "SELECT pr.ProdutoNome,    "
			+ "SUM(pi.ProdutoQuantidade) AS QuantidadeVendida,"
			+ "    SUM(pi.ProdutoQuantidade * pr.ProdutoValor) AS TotalVendas "
			+ "FROM    Pedido_item_Produto pi "
			+ "JOIN Produto pr ON pi.Produto_idProduto = pr.idProduto "
			+ "JOIN Pedido p ON pi.Pedido_idPedido = p.idPedido "
			+ "WHERE    p.PedidoDataHora BETWEEN :dataInicial AND :dataFinal "
			+ "GROUP BY    pr.ProdutoNome "
			+ "ORDER BY    TotalVendas DESC;", nativeQuery = true)
	List<RelatorioVendasPorProduto> montagemRelatorioVendasProduto(@Param("dataInicial") String dataInicial, @Param("dataFinal") String dataFinal);
	
	
	@Query(value = "SELECT    v.VendedorNome,"
			+ "    SUM(pi.ProdutoQuantidade * pr.ProdutoValor) AS TotalVendas "
			+ "FROM    Pedido p "
			+ "JOIN Vendedor v ON p.Vendedor_idVendedor = v.idVendedor "
			+ "JOIN Pedido_item_Produto pi ON p.idPedido = pi.Pedido_idPedido "
			+ "JOIN Produto pr ON pi.Produto_idProduto = pr.idProduto "
			+ "WHERE p.PedidoDataHora BETWEEN :dataInicial AND :dataFinal "
			+ "GROUP BY v.VendedorNome "
			+ "ORDER BY TotalVendas DESC;", nativeQuery = true)
	List<RelatorioVendasPorVendedor> montagemRelatorioVendasVendedor(@Param("dataInicial") String dataInicial, @Param("dataFinal") String dataFinal);
	
	@Query(value = "SELECT    p.PedidoDescricao,"
			+ "    p.PedidoValor,"
			+ "    sp.statusPedidoDescricao AS Status "
			+ "FROM    Pedido p "
			+ "JOIN Pedido_historico_status phs ON p.idPedido = phs.Pedido_idPedido "
			+ "JOIN statusPedido sp ON phs.statusPedido_idstatusPedido = sp.idstatusPedido "
			+ "WHERE sp.statusPedidoDescricao IN ('Pagamento Realizado', 'Recebido') and "
			+ "p.PedidoDataHora BETWEEN :dataInicial AND :dataFinal "
			+ "ORDER BY p.PedidoDataHora DESC;", nativeQuery = true)
	List<RelatorioPagamentos> montagemRelatorioPagamentos(@Param("dataInicial") String dataInicial, @Param("dataFinal") String dataFinal);

}

package acc.br.relatorio_service.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RelatorioVendasPorProduto {
	
	private String produtoNome;
	
	private BigDecimal quantidadeVendida;
	
	private BigDecimal totalVendas;

	public RelatorioVendasPorProduto(String produtoNome, BigDecimal quantidadeVendida, BigDecimal totalVendas) {
		this.produtoNome = produtoNome;
		this.quantidadeVendida = quantidadeVendida;
		this.totalVendas = totalVendas;
	}
	
	

}

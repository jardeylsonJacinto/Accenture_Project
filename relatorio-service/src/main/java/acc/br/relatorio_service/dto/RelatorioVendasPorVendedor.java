package acc.br.relatorio_service.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RelatorioVendasPorVendedor {
	
	private String vendedorNome;
	
	private BigDecimal totalVendas;

	public RelatorioVendasPorVendedor(String vendedorNome, BigDecimal totalVendas) {
		this.vendedorNome = vendedorNome;
		this.totalVendas = totalVendas;
	}
	
	

}

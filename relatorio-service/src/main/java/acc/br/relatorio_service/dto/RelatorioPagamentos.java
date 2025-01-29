package acc.br.relatorio_service.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RelatorioPagamentos {
	
	private String pedidoDescricao;
	private BigDecimal pedidoValor;
	private String Status;
	
	public RelatorioPagamentos(String pedidoDescricao, BigDecimal pedidoValor, String status) {
		this.pedidoDescricao = pedidoDescricao;
		this.pedidoValor = pedidoValor;
		Status = status;
	}
	
	

}

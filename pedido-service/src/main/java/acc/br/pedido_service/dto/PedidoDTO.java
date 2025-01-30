package acc.br.pedido_service.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoDTO {
	
	private String pedidoDescricao;
	
	private Double pedidoValor;
	
	private Integer idVendedor;
	
	private Integer idCliente;
	
	private List<PedidoItemDTO> itens;

}

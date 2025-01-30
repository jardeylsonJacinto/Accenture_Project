package acc.br.pedido_service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;

public class StatusHistoricoPedidoDTO {
	
	private Integer idPedido;
	
	private String statusDescricao;
	
 	@Column(name = "PedidoDataHora")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private LocalDateTime dataStatus;
	
	@Column(name = "PedidoDataHora")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private LocalDateTime dataPagamento;

}

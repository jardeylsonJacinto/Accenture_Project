package acc.br.pedido_service.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPedido")
	private Integer idPedido;
	
	@Column(name = "PedidoDescricao")
	private String pedidoDescricao;
	
	@Column(name = "PedidoValor")
	private Double pedidoValor;
	
	@Column(name = "PedidoDataHora")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private LocalDateTime pedidoDataHora;
	
	@Column(name = "Vendedor_idVendedor")
	private Integer idVendedor;
	
	@Column(name = "Cliente_idCliente")
	private Integer idCliente;
}

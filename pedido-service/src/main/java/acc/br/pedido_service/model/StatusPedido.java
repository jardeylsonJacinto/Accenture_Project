package acc.br.pedido_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class StatusPedido {
	
	@Id
	@Column(name = "idstatusPedido")
	private Integer idStatusPedido;
	
	@Column(name = "statusPedidoDescricao")
	private String statusPedidoDescricao;

}

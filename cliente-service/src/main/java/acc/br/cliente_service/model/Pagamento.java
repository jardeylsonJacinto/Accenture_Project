package acc.br.cliente_service.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pagamento {
	
	private Integer idCliente;
	
	private Integer IdPedido;
	
	private int confirmationCode;

}

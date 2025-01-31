package acc.accenture.pedido.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoItemDTO {

  private Integer quantidade;

  private Integer idProduto;

}
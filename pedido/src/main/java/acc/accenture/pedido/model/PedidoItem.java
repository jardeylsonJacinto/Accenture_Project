package acc.accenture.pedido.model;

import java.io.Serializable;

import acc.accenture.pedido.dtos.ProdutoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Pedido_item_Produto")
public class PedidoItem implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idPedidoItem")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "Pedido_idPedido", referencedColumnName = "idPedido", nullable = false)
  private Pedido pedido;

  @Column(name = "Produto_idProduto", nullable = false)
  private Long produtoId; // ID do Produto (outra API)

  @Column(name = "ProdutoQuantidade", nullable = false)
  private Integer quantidade;

  @Transient
  private ProdutoDTO produto; // Dados do Produto via RabbitMQ

  public PedidoItem() {
  }

  public PedidoItem(Pedido pedido, Long produtoId, Integer quantidade) {
    this.pedido = pedido;
    this.produtoId = produtoId;
    this.quantidade = quantidade;
  }

  // Getters and Setters
  public Pedido getPedido() {
    return pedido;
  }

  public void setPedido(Pedido pedido) {
    this.pedido = pedido;
  }

  public Long getProdutoId() {
    return produtoId;
  }

  public void setProdutoId(Long produtoId) {
    this.produtoId = produtoId;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }
}
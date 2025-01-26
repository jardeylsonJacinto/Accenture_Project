package acc.accenture.pedido.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponse {
  private Long id;
  private String descricao;
  private BigDecimal valor;
  private LocalDateTime dataHora;
  private String vendedor;
  private String cliente;
  private List<PedidoItemResponse> itens;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }

  public String getVendedor() {
    return vendedor;
  }

  public void setVendedor(String vendedor) {
    this.vendedor = vendedor;
  }

  public String getCliente() {
    return cliente;
  }

  public void setCliente(String cliente) {
    this.cliente = cliente;
  }

  public List<PedidoItemResponse> getItens() {
    return itens;
  }

  public void setItens(List<PedidoItemResponse> itens) {
    this.itens = itens;
  }
}

class PedidoItemResponse {
  private String produto;
  private Integer quantidade;

  public String getProduto() {
    return produto;
  }

  public void setProduto(String produto) {
    this.produto = produto;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }
}
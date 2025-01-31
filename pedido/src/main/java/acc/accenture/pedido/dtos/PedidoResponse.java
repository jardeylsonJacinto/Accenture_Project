package acc.accenture.pedido.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponse {
  private Long id;
  private String descricao;
  private BigDecimal valor;
  private LocalDateTime dataHora;
  private Long vendedorId;
  private Long clienteId;
  private List<PedidoItemResponse> itens;

  // Getters e Setters
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

  public Long getVendedorId() {
    return vendedorId;
  }

  public void setVendedorId(Long vendedor) {
    this.vendedorId = vendedor;
  }

  public Long getClienteId() {
    return clienteId;
  }

  public void setClienteId(Long cliente) {
    this.clienteId = cliente;
  }

  public List<PedidoItemResponse> getItens() {
    return itens;
  }

  public void setItens(List<PedidoItemResponse> itens) {
    this.itens = itens;
  }
}

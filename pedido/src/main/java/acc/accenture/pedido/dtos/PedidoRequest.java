package acc.accenture.pedido.dtos;

import java.math.BigDecimal;
import java.util.List;

public class PedidoRequest {
    private String descricao;
    private BigDecimal valor;
    private Long vendedorId;
    private Long clienteId;
    private List<PedidoItemRequest> itens;

    public PedidoRequest(String descricao, BigDecimal valor, Long vendedorId, Long clienteId,
            List<PedidoItemRequest> itens) {
        this.descricao = descricao;
        this.valor = valor;
        this.vendedorId = vendedorId;
        this.clienteId = clienteId;
        this.itens = itens;
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

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<PedidoItemRequest> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItemRequest> itens) {
        this.itens = itens;
    }
}
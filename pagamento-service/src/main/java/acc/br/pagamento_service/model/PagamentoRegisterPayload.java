package acc.br.pagamento_service.model;

public record PagamentoRegisterPayload(Integer idCliente,Integer idPedido,int confirmationCode) {

}

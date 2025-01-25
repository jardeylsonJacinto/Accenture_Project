package acc.br.email_service.dtos;

public record EmailPayload(String para, String titulo, String conteudo, int confirmationCode) {

}

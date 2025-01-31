package acc.accenture.pedido.dtos;

import java.io.Serializable;

public class Email implements Serializable {
  private String para;
  private String titulo;
  private String conteudo;
  private int ConfirmationCode;

  public Email(String para, String titulo, String conteudo, int ConfirmationCode) {
    this.para = para;
    this.titulo = titulo;
    this.conteudo = conteudo;
    this.ConfirmationCode = ConfirmationCode;
  }

  public String getPara() {
    return para;
  }

  public void setPara(String para) {
    this.para = para;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getConteudo() {
    return conteudo;
  }

  public void setConteudo(String conteudo) {
    this.conteudo = conteudo;
  }

  public int getConfirmationCode() {
    return ConfirmationCode;
  }

  public void setConfirmationCode(int confirmationCode) {
    this.ConfirmationCode = confirmationCode;
  }
}

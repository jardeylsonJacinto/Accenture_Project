package acc.accenture.pedido.dtos;

public class VendedorDTO {
  private Long id;
  private String nome;
  private String setor;

  // Getters e Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSetor() {
    return setor;
  }

  public void setSetor(String setor) {
    this.setor = setor;
  }
}

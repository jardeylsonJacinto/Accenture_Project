package acc.br.relatorio_service.model;

public class Email {
	
	private String para;
	
	private String titulo;
	
	private String conteudo;
	
	private int ConfirmationCode;

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
		ConfirmationCode = confirmationCode;
	}
	
}


package acc.br.vendedor.dtos;

public class VendedorDTO {
	
	private String vendedorNome;
	
	private String vendedorSetor;
	
	

	public VendedorDTO(String vendedorNome, String vendedorSetor) {
		this.vendedorNome = vendedorNome;
		this.vendedorSetor = vendedorSetor;
	}
	

	public VendedorDTO() {
	}


	public String getVendedorNome() {
		return vendedorNome;
	}

	public void setVendedorNome(String vendedorNome) {
		this.vendedorNome = vendedorNome;
	}

	public String getVendedorSetor() {
		return vendedorSetor;
	}

	public void setVendedorSetor(String vendedorSetor) {
		this.vendedorSetor = vendedorSetor;
	}
	
	

}

package acc.br.vendedor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vendedor {
	
	@Id
	@Column(name ="idVendedor")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idVendedor;
	
	@Column(name ="VendedorNome")
	private String vendedorNome;
	
	@Column(name ="VendedorSetor")
	private String vendedorSetor;
	

	public Integer getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
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

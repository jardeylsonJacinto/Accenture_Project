package acc.br.cliente_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCliente")
	private Integer idCliente;
	
	@Column(name = "ClienteNome")
	private String clienteNome;
	
	@Column(name = "ClienteCPF")
	private String clienteCPF;
	
	@Column(name = "ClienteTelefone")
	private String clienteTelefone;
	
	@Column(name = "ClienteEmail")
	private String clienteEmail;

}

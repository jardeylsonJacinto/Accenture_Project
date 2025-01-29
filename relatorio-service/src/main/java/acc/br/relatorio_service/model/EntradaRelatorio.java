package acc.br.relatorio_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class EntradaRelatorio {
	
	@Id
	private String dataInicial;
	
	private String dataFinal;
	
	private String email;

}

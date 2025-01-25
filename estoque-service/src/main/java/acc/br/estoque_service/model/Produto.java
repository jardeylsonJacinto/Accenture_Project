package acc.br.estoque_service.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProduto")
	private Integer idProduto;
	
	@Column(name = "ProdutoDescricao")
	private String produtoDescricao;
	
	@Column(name = "ProdutoValor")
	private Double produtoValor;
	
	@Column(name = "ProdutoDataHoraSaida")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private LocalDateTime produtoDataHoraSaida;
	
	@Column(name = "ProdutoEstoque")
	private Integer produtoEstoque;
	
	@Column(name = "ProdutoNome")
	private String produtoNome;
}

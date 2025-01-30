package acc.br.relatorio_service.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RelatorioVendasPorPeriodo {
    
    private String anoMes;
    
    private BigDecimal totalVendas;

    // Construtor adequado para a query
    public RelatorioVendasPorPeriodo(String anoMes, BigDecimal totalVendas) {
        this.anoMes = anoMes;
        this.totalVendas = totalVendas;
    }
}

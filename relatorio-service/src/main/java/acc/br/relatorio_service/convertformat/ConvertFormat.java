package acc.br.relatorio_service.convertformat;

import java.util.List;

import acc.br.relatorio_service.dto.RelatorioPagamentos;
import acc.br.relatorio_service.dto.RelatorioVendasPorPeriodo;
import acc.br.relatorio_service.dto.RelatorioVendasPorProduto;
import acc.br.relatorio_service.dto.RelatorioVendasPorVendedor;

public class ConvertFormat {

    public static String formatarRelatorioVendasPorPeriodo(List<RelatorioVendasPorPeriodo> registros) {
        StringBuilder tabela = new StringBuilder();

        // Adicionando o cabeçalho
        tabela.append(String.format("%-10s | %-12s%n", "Ano-Mês", "Total Vendas"));
        tabela.append("-----------------------------\n");

        // Adicionando os registros
        for (RelatorioVendasPorPeriodo registro : registros) {
            tabela.append(String.format("%-10s | %-12.2f%n", registro.getAnoMes(), registro.getTotalVendas()));
        }

        return tabela.toString();
    }
    
    public static String formatarRelatorioVendasPorProdutos(List<RelatorioVendasPorProduto> registros) {
        StringBuilder tabela = new StringBuilder();

        // Adicionando o cabeçalho
        tabela.append(String.format("%-15s | %-18s | %-12s%n", "Produto", "Quantidade Vendida", "Total Vendas"));
        tabela.append("-------------------------------------------------------------\n");

        // Adicionando os registros
        for (RelatorioVendasPorProduto registro : registros) {
            tabela.append(String.format(
                "%-15s | %-18.0f | %-12.2f%n",
                registro.getProdutoNome(),
                registro.getQuantidadeVendida(),
                registro.getTotalVendas()
            ));
        }

        return tabela.toString();
    }
    
    public static String formatarRelatorioVendedores(List<RelatorioVendasPorVendedor> registros) {
        StringBuilder tabela = new StringBuilder();

        // Adicionando o cabeçalho
        tabela.append(String.format("%-15s | %-12s%n", "Vendedor", "Total Vendas"));
        tabela.append("----------------------------------\n");

        // Adicionando os registros
        for (RelatorioVendasPorVendedor registro : registros) {
            tabela.append(String.format(
                "%-15s | %-12.2f%n",
                registro.getVendedorNome(),
                registro.getTotalVendas()
            ));
        }

        return tabela.toString();
    }
    
    public static String formatarRelatorioPagamentos(List<RelatorioPagamentos> registros) {
        StringBuilder tabela = new StringBuilder();

        // Adicionando o cabeçalho
        tabela.append(String.format("%-15s | %-10s | %-20s%n", "Pedido", "Valor", "Status"));
        tabela.append("---------------------------------------------------------\n");

        // Adicionando os registros
        for (RelatorioPagamentos registro : registros) {
            tabela.append(String.format(
                "%-15s | %-10.2f | %-20s%n",
                registro.getPedidoDescricao(),
                registro.getPedidoValor(),
                registro.getStatus()
            ));
        }

        return tabela.toString();
    }
}

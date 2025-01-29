package acc.br.relatorio_service.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acc.br.relatorio_service.dto.RelatorioPagamentos;
import acc.br.relatorio_service.dto.RelatorioVendasPorPeriodo;
import acc.br.relatorio_service.dto.RelatorioVendasPorProduto;
import acc.br.relatorio_service.dto.RelatorioVendasPorVendedor;
import acc.br.relatorio_service.repository.RelatorioRepository;

@Service
public class RelatorioService {
	
	@Autowired
	private RelatorioRepository relatorioRepository;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public List<RelatorioVendasPorPeriodo> obterRelatorioVendasPorPeriodo(String dataInicial,String dataFinal){	
		String dataInicialStr = dataInicial.formatted(formatter);
		String dataFinalStr = dataFinal.formatted(formatter);
		return relatorioRepository.montagemRelatorioVendas(dataInicialStr,dataFinalStr);
	}	
	
	public List<RelatorioVendasPorProduto> obterRelatorioVendasPorProduto(String dataInicial,String dataFinal){
		String dataInicialStr = dataInicial.formatted(formatter);
		String dataFinalStr = dataFinal.formatted(formatter);
		return relatorioRepository.montagemRelatorioVendasProduto(dataInicialStr,dataFinalStr);
	}
	
	public List<RelatorioVendasPorVendedor> obterRelatorioVendasPorVendedor(String dataInicial, String dataFinal){
		String dataInicialStr = dataInicial.formatted(formatter);
		String dataFinalStr = dataFinal.formatted(formatter);
		return relatorioRepository.montagemRelatorioVendasVendedor(dataInicialStr, dataFinalStr);
	}
	
	public List<RelatorioPagamentos> obterRelatorioPagamentos(String dataInicial, String dataFinal){
		String dataInicialStr = dataInicial.formatted(formatter);
		String dataFinalStr = dataFinal.formatted(formatter);
		return relatorioRepository.montagemRelatorioPagamentos(dataInicialStr, dataFinalStr);
	}
	

}

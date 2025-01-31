package acc.br.relatorio_service.controller;

import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.relatorio_service.convertformat.ConvertFormat;
import acc.br.relatorio_service.model.Email;
import acc.br.relatorio_service.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
	
	static String QUEUE_NAME = "Equipe4.email";

    private final RabbitTemplate rabbitTemplate;

    public RelatorioController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
	
	@Autowired
	private RelatorioService relatorioService;
	
	@Operation(summary = "Envia para o email um relatorio de vendas por periodo")
	@GetMapping("/relatorioVendasPorPeriodo")
	public void obterRelatorioVendasPorPeriodo(@RequestParam String dataInicial, @RequestParam String dataFinal, @RequestParam String emailPara) throws JsonProcessingException{
		
		Email email = new Email();
		Random random = new Random();
        int confirmationCode = random.nextInt(900000) + 100000;
        
        email.setPara(emailPara);
        email.setTitulo("Relatório de Vendas por Período - " + dataInicial + " a " + dataFinal);
        email.setConteudo(ConvertFormat.formatarRelatorioVendasPorPeriodo(relatorioService.obterRelatorioVendasPorPeriodo(dataInicial, dataFinal)));
        email.setConfirmationCode(confirmationCode);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(email);

        rabbitTemplate.convertAndSend(QUEUE_NAME, queuePayloadString);
		
	}
	
	@Operation(summary = "Envia para o email um relatorio de vendas por produto")
	@GetMapping("/relatorioVendasPorProduto")
	public void obterRelatorioVendasPorProduto(@RequestParam String dataInicial, @RequestParam String dataFinal, @RequestParam String emailPara) throws JsonProcessingException{
		
		Email email = new Email();
		Random random = new Random();
        int confirmationCode = random.nextInt(900000) + 100000;
        
        email.setPara(emailPara);
        email.setTitulo("Relatório de Vendas por Produto - "  + dataInicial + " a " + dataFinal);
        email.setConteudo(ConvertFormat.formatarRelatorioVendasPorProdutos(relatorioService.obterRelatorioVendasPorProduto(dataInicial, dataFinal)));
        email.setConfirmationCode(confirmationCode);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(email);

        rabbitTemplate.convertAndSend(QUEUE_NAME, queuePayloadString);
		
	}
	
	@Operation(summary = "Envia para o email um relatorio de vendas por vendedor")
	@GetMapping("/relatorioVendasPorVendedor")
	public void obterRelatorioVendasPorVendedor(@RequestParam String dataInicial, @RequestParam String dataFinal, @RequestParam String emailPara) throws JsonProcessingException{
		Email email = new Email();
		Random random = new Random();
        int confirmationCode = random.nextInt(900000) + 100000;
        
        email.setPara(emailPara);
        email.setTitulo("Relatório de Vendas por Vendedor - "  + dataInicial + " a " + dataFinal);
        email.setConteudo(ConvertFormat.formatarRelatorioVendedores(relatorioService.obterRelatorioVendasPorVendedor(dataInicial, dataFinal)));
        email.setConfirmationCode(confirmationCode);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(email);

        rabbitTemplate.convertAndSend(QUEUE_NAME, queuePayloadString);
	 }
	
	@Operation(summary = "Envia para o email um relatorio de pagamentos")
	@GetMapping("/relatorioPagamentos")
	public void obterRelatorioPagamentos(@RequestParam String dataInicial, @RequestParam String dataFinal, @RequestParam String emailPara) throws JsonProcessingException{
		Email email = new Email();
		Random random = new Random();
        int confirmationCode = random.nextInt(900000) + 100000;
        
        email.setPara(emailPara);
        email.setTitulo("Relatório de Pagamentos - "  + dataInicial + " a " + dataFinal);
        email.setConteudo(ConvertFormat.formatarRelatorioPagamentos(relatorioService.obterRelatorioPagamentos(dataInicial, dataFinal)));
        email.setConfirmationCode(confirmationCode);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(email);

        rabbitTemplate.convertAndSend(QUEUE_NAME, queuePayloadString);
	}

}

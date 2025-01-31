package acc.br.pagamento_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import acc.br.pagamento_service.model.Pagamento;
import acc.br.pagamento_service.service.PagamentoService;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {
	

	
	@Autowired
	private PagamentoService pagamentoService;
	
	@PostMapping
	public void confirmarPagamento(@RequestBody Pagamento pagamento) throws JsonProcessingException {        
        pagamentoService.confirmarPagamento(pagamento);    
	}
	

}

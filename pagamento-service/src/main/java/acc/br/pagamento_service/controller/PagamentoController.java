package acc.br.pagamento_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import acc.br.pagamento_service.model.Pagamento;
import acc.br.pagamento_service.service.PagamentoService;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {
	
	
    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<?> confirmarPagamento(@RequestBody Pagamento pagamento) {
        try {
            pagamentoService.confirmarPagamento(pagamento);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar pagamento: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
    }

}

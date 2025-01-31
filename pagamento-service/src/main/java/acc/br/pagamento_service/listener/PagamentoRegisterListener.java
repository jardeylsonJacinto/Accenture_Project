package acc.br.pagamento_service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.pagamento_service.model.Pagamento;
import acc.br.pagamento_service.model.PagamentoRegisterPayload;
import acc.br.pagamento_service.service.PagamentoService;

@Component
public class PagamentoRegisterListener {
	
	@Autowired
	private PagamentoService pagamentoService;
	
    public void onMessageReceived(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<PagamentoRegisterPayload> mapType = new TypeReference<>() {};
        PagamentoRegisterPayload payload = objectMapper.readValue(message, mapType);

        Pagamento pagamentoNovo = new Pagamento();
        pagamentoNovo.setIdCliente(payload.idCliente());
        pagamentoNovo.setIdPedido(payload.idPedido());
        
        pagamentoService.confirmarPagamento(pagamentoNovo);
    }

}

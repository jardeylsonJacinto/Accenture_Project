package acc.br.pagamento_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acc.br.pagamento_service.model.Pagamento;
import acc.br.pagamento_service.repository.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public void confirmarPagamento(Pagamento pagamento) {
		pagamentoRepository.atualizarDataPagamento(pagamento.getIdCliente(), pagamento.getIdPedido());
	}
}

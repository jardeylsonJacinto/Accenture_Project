package acc.br.pedido_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acc.br.pedido_service.dto.PedidoDTO;
import acc.br.pedido_service.dto.PedidoItemDTO;
import acc.br.pedido_service.model.Pedido;
import acc.br.pedido_service.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public List<Pedido> obterTodos(){
		return pedidoRepository.findAll();
	}
	
	public Pedido obterUm(Integer id) {
		return pedidoRepository.findById(id).get();
	}
	
	public void deletar(Integer id) {
		pedidoRepository.deleteById(id);
	}
	
	public Pedido criar(PedidoDTO pedido) {
		List<PedidoItemDTO> lista = pedido.getItens();
		Double total = 0.0;
		for (int i = 0; i < lista.size(); i++) {
			if(lista.get(i).getQuantidade() <= pedidoRepository.checagemEstoque(lista.get(i).getIdProduto()).getQuantidade()) {
				pedidoRepository.atualizarEstoque(lista.get(i).getIdProduto(), lista.get(i).getQuantidade());
				total += lista.get(i).getQuantidade() * pedidoRepository.pegarValor(lista.get(i).getIdProduto());
			} else {System.out.println("Quantidade errada");}
		}
		Pedido registro = new Pedido();
		
		registro.setIdCliente(pedido.getIdCliente());
		registro.setIdVendedor(pedido.getIdVendedor());
		registro.setPedidoDescricao(pedido.getPedidoDescricao());
		registro.setPedidoDataHora(LocalDateTime.now());
		registro.setPedidoValor(total);
		Integer idNovoPedido = pedidoRepository.save(registro).getIdPedido();
		
		for (int i = 0; i < lista.size(); i++) {
			if(lista.get(i).getQuantidade() <= pedidoRepository.checagemEstoque(lista.get(i).getIdProduto()).getQuantidade()) {
				pedidoRepository.insertPedidoItemProduto(idNovoPedido, lista.get(i).getQuantidade(), lista.get(i).getIdProduto());
			} else {System.out.println("Quantidade errada");}
		}
		
		pedidoRepository.insertPedidoHistorico(idNovoPedido);
		
		return pedidoRepository.findById(idNovoPedido).get();
	}
	
	public Pedido alterar(Pedido pedido, Integer id) {
		pedido.setIdPedido(id);
		return pedidoRepository.save(pedido);
	}

}

package acc.accenture.pedido;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import acc.accenture.pedido.dtos.PedidoItemRequest;
import acc.accenture.pedido.dtos.PedidoRequest;
import acc.accenture.pedido.dtos.PedidoResponse;
import acc.accenture.pedido.model.Pedido;
import acc.accenture.pedido.repository.PedidoRepository;
import acc.accenture.pedido.service.PedidoService;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    public void testCriarPedido() {
        List<PedidoItemRequest> itensPedido = new ArrayList<>();
        itensPedido.add(new PedidoItemRequest(1L, 2));
        PedidoRequest request = new PedidoRequest(
                "Pedido de teste",
                BigDecimal.valueOf(100),
                1L, // VendedorId
                1L, // ClienteId
                itensPedido);

        Pedido savedPedido = new Pedido();
        savedPedido.setId(1L);
        savedPedido.setDescricao(request.getDescricao());
        savedPedido.setValor(request.getValor());

        Mockito.when(pedidoRepository.save(any(Pedido.class))).thenReturn(savedPedido);

        PedidoResponse response = pedidoService.criarPedido(request);

        Assertions.assertThat(response.getId()).isEqualTo(savedPedido.getId());
        Assertions.assertThat(response.getDescricao()).isEqualTo(savedPedido.getDescricao());
        Assertions.assertThat(response.getValor()).isEqualTo(savedPedido.getValor());

        verify(rabbitTemplate).convertAndSend(eq("pedido.exchange"), eq("pedido.created"), any(Pedido.class));
    }

    @Test
    public void testListarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido());
        pedidos.add(new Pedido());

        Mockito.when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<PedidoResponse> responses = pedidoService.listarPedidos();

        Assertions.assertThat(responses.size()).isEqualTo(pedidos.size());
    }

    @Test
    public void testBuscarPedidoPorId_pedidoExistente() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        PedidoResponse response = pedidoService.buscarPedidoPorId(id).getBody();

        Assertions.assertThat(response.getId()).isEqualTo(id);
    }

    @Test
    public void testBuscarPedidoPorId_pedidoInexistente() {
        Long id = 1L;
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pedidoService.buscarPedidoPorId(id));
    }
}

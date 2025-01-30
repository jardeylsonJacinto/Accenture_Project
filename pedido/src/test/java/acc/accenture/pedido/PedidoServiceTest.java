package acc.accenture.pedido;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Arrays;
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
        // Dados de exemplo para o pedido
        PedidoRequest request = new PedidoRequest();
        request.setDescricao("Pedido de teste");
        request.setValor(BigDecimal.valueOf(100));

        // Mock do comportamento do repositório
        Pedido savedPedido = new Pedido();
        savedPedido.setId(1L);
        savedPedido.setDescricao(request.getDescricao());
        savedPedido.setValor(request.getValor());

        Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(savedPedido);

        // Executa o método
        PedidoResponse response = pedidoService.criarPedido(request);

        // Verifica se o pedido foi salvo corretamente
        Assertions.assertThat(response.getId()).isEqualTo(savedPedido.getId());
        Assertions.assertThat(response.getDescricao()).isEqualTo(savedPedido.getDescricao());
        Assertions.assertThat(response.getValor()).isEqualTo(savedPedido.getValor());

        // Verifica se a mensagem foi enviada para o RabbitMQ
        verify(rabbitTemplate).convertAndSend(eq("pedido.exchange"), eq("pedido.created"), any(Pedido.class));
    }

    @Test
    public void testListarPedidos() {
        // Mock de lista de pedidos
        List<Pedido> pedidos = Arrays.asList(
                new Pedido(),
                new Pedido());
        Mockito.when(pedidoRepository.findAll()).thenReturn(pedidos);

        // Executa o método
        List<PedidoResponse> responses = pedidoService.listarPedidos();

        // Verifica se a quantidade de pedidos retornados é igual a quantidade mockada
        Assertions.assertThat(responses.size()).isEqualTo(pedidos.size());
    }

    @Test
    public void testBuscarPedidoPorId_pedidoExistente() {
        // ID do pedido mockado
        Long id = 1L;

        // Mock do pedido
        Pedido pedido = new Pedido();
        pedido.setId(id);

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        // Executa o método
        PedidoResponse response = pedidoService.buscarPedidoPorId(id);

        // Verifica se o ID do pedido retornado é igual ao ID mockado
        Assertions.assertThat(response.getId()).isEqualTo(id);
    }

    @Test
    public void testBuscarPedidoPorId_pedidoInexistente() {
        // ID do pedido mockado (inexistente)
        Long id = 1L;

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        // Executa o método e verifica se a exceção esperada é lançada
        assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPedidoPorId(id);
        });
    }
}
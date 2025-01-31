package acc.br.cliente_service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import acc.br.cliente_service.controller.ClienteController;
import acc.br.cliente_service.dto.RastreioDTO;
import acc.br.cliente_service.model.Cliente;
import acc.br.cliente_service.service.ClienteService;

public class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;
    private RastreioDTO rastreioDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();

        // Criando um cliente de exemplo
        cliente = new Cliente(1, "Carlos Silva", "12345678901", "1234567890", "carlos@exemplo.com");

        // Criando um RastreioDTO de exemplo
        rastreioDTO = new RastreioDTO();
        rastreioDTO.setIdPedido(1);
        rastreioDTO.setStatusDescricao("Aguardando Pagamento");
        rastreioDTO.setDataStatus(Timestamp.valueOf("2025-01-30 12:00:00"));
        rastreioDTO.setDataPagamento(Timestamp.valueOf("2025-01-30 14:00:00"));
    }

    @Test
    public void testObterTodosClientes() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.obterTodos()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idCliente").value(1))
            .andExpect(jsonPath("$[0].clienteNome").value("Carlos Silva"))
            .andExpect(jsonPath("$[0].clienteCPF").value("12345678901"))
            .andExpect(jsonPath("$[0].clienteEmail").value("carlos@exemplo.com"));
    }

    @Test
    public void testObterUmCliente() throws Exception {
        when(clienteService.obterUm(1)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idCliente").value(1))
            .andExpect(jsonPath("$.clienteNome").value("Carlos Silva"))
            .andExpect(jsonPath("$.clienteCPF").value("12345678901"))
            .andExpect(jsonPath("$.clienteEmail").value("carlos@exemplo.com"));
    }

    @Test
    public void testCriarCliente() throws Exception {
        when(clienteService.criar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"clienteNome\": \"Carlos Silva\", \"clienteCPF\": \"12345678901\", \"clienteTelefone\": \"1234567890\", \"clienteEmail\": \"carlos@exemplo.com\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.clienteNome").value("Carlos Silva"))
            .andExpect(jsonPath("$.clienteCPF").value("12345678901"));
    }

    @Test
    public void testAlterarCliente() throws Exception {
        Cliente clienteAlterado = new Cliente(1, "Carlos Silva Alterado", "12345678901", "1234567890", "alterado@exemplo.com");
        when(clienteService.alterar(eq(1), any(Cliente.class))).thenReturn(clienteAlterado);

        // Realizar a requisição PUT e verificar a resposta
        mockMvc.perform(put("/api/clientes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteNome\": \"Carlos Silva Alterado\", \"clienteCPF\": \"12345678901\", \"clienteTelefone\": \"1234567890\", \"clienteEmail\": \"alterado@exemplo.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteNome").value("Carlos Silva Alterado"))
                .andExpect(jsonPath("$.clienteEmail").value("alterado@exemplo.com"));
    }

    @Test
    public void testDeletarCliente() throws Exception {
        doNothing().when(clienteService).deletar(1);

        mockMvc.perform(delete("/api/clientes/{id}", 1))
            .andExpect(status().isOk());
    }

    @Test
    public void testObterTodosRastreios() throws Exception {
        List<RastreioDTO> rastreios = Arrays.asList(rastreioDTO);
        when(clienteService.obterTodosRastreios(1)).thenReturn(rastreios);

        mockMvc.perform(get("/api/clientes/rastreio/{idCliente}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idPedido").value(1))
            .andExpect(jsonPath("$[0].statusDescricao").value("Aguardando Pagamento"))
            .andExpect(jsonPath("$[0].dataStatus").value("30/01/2025 12:00:00"))
            .andExpect(jsonPath("$[0].dataPagamento").value("30/01/2025 14:00:00"));
    }

    @Test
    public void testObterRastreio() throws Exception {
        when(clienteService.obterRastreio(1, 1)).thenReturn(rastreioDTO);

        mockMvc.perform(get("/api/clientes/rastreio/{idCliente}/{idPedido}", 1, 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idPedido").value(1))
            .andExpect(jsonPath("$.statusDescricao").value("Aguardando Pagamento"))
            .andExpect(jsonPath("$.dataStatus").value("30/01/2025 12:00:00"))
            .andExpect(jsonPath("$.dataPagamento").value("30/01/2025 14:00:00"));
    }

    @Test
    public void testPagamento() throws Exception {
        doNothing().when(clienteService).realizarPagamento(1, 1);

        mockMvc.perform(post("/api/clientes/pagamento/{idCliente}/{idPedido}", 1, 1))
            .andExpect(status().isOk());
    }
}

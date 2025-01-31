package acc.br.pagamento_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.pagamento_service.controller.PagamentoController;
import acc.br.pagamento_service.model.Pagamento;
import acc.br.pagamento_service.service.PagamentoService;

@ExtendWith(MockitoExtension.class)
class PagamentoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PagamentoController pagamentoController;

    @Mock
    private PagamentoService pagamentoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConfirmarPagamento_Success() throws Exception {
        // Arrange
        Pagamento pagamento = new Pagamento(1, 123);
        String jsonRequest = objectMapper.writeValueAsString(pagamento);

        doNothing().when(pagamentoService).confirmarPagamento(any(Pagamento.class));

        // Act & Assert
        mockMvc.perform(post("/api/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());

        verify(pagamentoService, times(1)).confirmarPagamento(any(Pagamento.class));
    }

    @Test
    void testConfirmarPagamento_Failure() throws Exception {
        // Arrange
        Pagamento pagamento = new Pagamento(1, 123);
        String jsonRequest = objectMapper.writeValueAsString(pagamento);

        doThrow(new RuntimeException("Erro ao processar pagamento"))
                .when(pagamentoService).confirmarPagamento(any(Pagamento.class));

        // Act & Assert
        mockMvc.perform(post("/api/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isInternalServerError());

        verify(pagamentoService, times(1)).confirmarPagamento(any(Pagamento.class));
    }
}


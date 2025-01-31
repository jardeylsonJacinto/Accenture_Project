package acc.br.estoque_service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.estoque_service.controller.ProdutoController;
import acc.br.estoque_service.model.Produto;
import acc.br.estoque_service.service.ProdutoService;

class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testObterTodos() throws Exception {
        // Arrange
        Produto produto1 = new Produto(1, "Produto 1", 10.0, LocalDateTime.now(), 100, "Produto A");
        Produto produto2 = new Produto(2, "Produto 2", 20.0, LocalDateTime.now(), 50, "Produto B");
        List<Produto> produtos = Arrays.asList(produto1, produto2);

        when(produtoService.obterTodos()).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/api/produtos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].produtoNome").value("Produto A"))
            .andExpect(jsonPath("$[1].produtoNome").value("Produto B"));
    }

    @Test
    void testObterUm() throws Exception {
        // Arrange
        Produto produto = new Produto(1, "Produto 1", 10.0, LocalDateTime.now(), 100, "Produto A");
        when(produtoService.obterUm(1)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(get("/api/produtos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.produtoNome").value("Produto A"));
    }

    @Test
    void testCriarProduto() throws Exception {
        // Arrange
        Produto produto = new Produto(null, "Novo Produto", 30.0, LocalDateTime.now(), 200, "Produto X");
        Produto produtoCriado = new Produto(1, "Novo Produto", 30.0, LocalDateTime.now(), 200, "Produto X");

        when(produtoService.criar(any(Produto.class))).thenReturn(produtoCriado);

        // Act & Assert
        mockMvc.perform(post("/api/produtos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(produto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idProduto").value(1))
            .andExpect(jsonPath("$.produtoNome").value("Produto X"));
    }

    @Test
    void testAlterarProduto() throws Exception {
        // Arrange
        Produto produto = new Produto(1, "Produto Alterado", 35.0, LocalDateTime.now(), 150, "Produto Y");
        when(produtoService.alterar(any(Produto.class), eq(1))).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(put("/api/produtos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(produto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.produtoNome").value("Produto Y"))
            .andExpect(jsonPath("$.produtoValor").value(35.0));
    }

    @Test
    void testDeletarProduto() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/produtos/1"))
            .andExpect(status().isOk());

        verify(produtoService, times(1)).deletar(1);
    }
}


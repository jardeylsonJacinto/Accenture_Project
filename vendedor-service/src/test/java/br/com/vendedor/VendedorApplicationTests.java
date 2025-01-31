package br.com.vendedor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.vendedor.controller.VendedorController;
import acc.br.vendedor.dtos.VendedorDTO;
import acc.br.vendedor.model.Vendedor;
import acc.br.vendedor.service.VendedorService;

class VendedorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VendedorService vendedorService;

    @InjectMocks
    private VendedorController vendedorController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendedorController).build();
    }

    @Test
    void testObterTodos() throws Exception {
        Vendedor vendedor1 = new Vendedor();
        vendedor1.setIdVendedor(1);
        vendedor1.setVendedorNome("Carlos Silva");
        vendedor1.setVendedorSetor("Eletrônicos");

        Vendedor vendedor2 = new Vendedor();
        vendedor2.setIdVendedor(2);
        vendedor2.setVendedorNome("Ana Souza");
        vendedor2.setVendedorSetor("Roupas");

        List<Vendedor> vendedores = Arrays.asList(vendedor1, vendedor2);

        when(vendedorService.obterTodos()).thenReturn(vendedores);

        mockMvc.perform(get("/api/vendedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].vendedorNome").value("Carlos Silva"))
                .andExpect(jsonPath("$[1].vendedorNome").value("Ana Souza"));
    }

    @Test
    void testObterUm() throws Exception {
        Vendedor vendedor = new Vendedor();
        vendedor.setIdVendedor(1);
        vendedor.setVendedorNome("Carlos Silva");
        vendedor.setVendedorSetor("Eletrônicos");

        when(vendedorService.obterUm(1)).thenReturn(Optional.of(vendedor));

        mockMvc.perform(get("/api/vendedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendedorNome").value("Carlos Silva"))
                .andExpect(jsonPath("$.vendedorSetor").value("Eletrônicos"));
    }


    @Test
    void testDeletar() throws Exception {
        doNothing().when(vendedorService).deleta(1);

        mockMvc.perform(delete("/api/vendedores/1"))
                .andExpect(status().isOk());

        verify(vendedorService, times(1)).deleta(1);
    }

    @Test
    void testCriar() throws Exception {
        VendedorDTO vendedorDTO = new VendedorDTO();
        vendedorDTO.setVendedorNome("Carlos Silva");
        vendedorDTO.setVendedorSetor("Eletrônicos");

        Vendedor vendedor = new Vendedor();
        vendedor.setIdVendedor(1);
        vendedor.setVendedorNome("Carlos Silva");
        vendedor.setVendedorSetor("Eletrônicos");

        when(vendedorService.criar(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/api/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendedorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVendedor").value(1))
                .andExpect(jsonPath("$.vendedorNome").value("Carlos Silva"))
                .andExpect(jsonPath("$.vendedorSetor").value("Eletrônicos"));
    }

    @Test
    void testAlterar() throws Exception {
        VendedorDTO vendedorDTO = new VendedorDTO();
        vendedorDTO.setVendedorNome("Carlos Atualizado");
        vendedorDTO.setVendedorSetor("Automóveis");

        Vendedor vendedor = new Vendedor();
        vendedor.setIdVendedor(1);
        vendedor.setVendedorNome("Carlos Atualizado");
        vendedor.setVendedorSetor("Automóveis");

        when(vendedorService.alterar(any(Vendedor.class), eq(1))).thenReturn(vendedor);

        mockMvc.perform(put("/api/vendedores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendedorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendedorNome").value("Carlos Atualizado"))
                .andExpect(jsonPath("$.vendedorSetor").value("Automóveis"));
    }
}


package br.com.vendedor;

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

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.vendedor.controller.VendedorController;
import acc.br.vendedor.dtos.VendedorDTO;
import acc.br.vendedor.model.Vendedor;
import acc.br.vendedor.service.VendedorService;

@ExtendWith(MockitoExtension.class)
class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VendedorService vendedorService;

    @InjectMocks
    private VendedorController vendedorController;

    private Vendedor vendedor;
    private VendedorDTO vendedorDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendedorController).build();
        
        vendedor = new Vendedor();
        vendedor.setVendedorNome("Vendedor Teste");
        vendedor.setVendedorSetor("Setor Teste");

        vendedorDTO = new VendedorDTO();
        vendedorDTO.setVendedorNome("Vendedor Teste");
        vendedorDTO.setVendedorSetor("Setor Teste");
    }

    @Test
    void obterTodos_DeveRetornarListaDeVendedores() throws Exception {
        when(vendedorService.obterTodos()).thenReturn(Arrays.asList(vendedor));

        mockMvc.perform(get("/api/vendedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vendedorNome").value("Vendedor Teste"));
    }

    @Test
    void obterUm_DeveRetornarVendedorEspecifico() throws Exception {
        when(vendedorService.obterUm(1)).thenReturn(Optional.of(vendedor));

        mockMvc.perform(get("/api/vendedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendedorNome").value("Vendedor Teste"));
    }

    @Test
    void obterUm_DeveRetornarNotFoundQuandoVendedorNaoExistir() throws Exception {
        when(vendedorService.obterUm(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/vendedores/1"))
        .andExpect(status().isOk())  // Espera um status 200 pois o vendedor foi encontrado (mas vazio)
        .andExpect(jsonPath("$.id").doesNotExist())  // Verifica que o ID do vendedor vazio não está presente
        .andExpect(jsonPath("$.vendedorNome").doesNotExist())  // Verifica que o nome do vendedor não está presente
        .andExpect(jsonPath("$.vendedorSetor").doesNotExist());
    }

    @Test
    void criar_DeveCriarVendedor() throws Exception {
        when(vendedorService.criar(any(Vendedor.class))).thenReturn(vendedor);

        mockMvc.perform(post("/api/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(vendedorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendedorNome").value("Vendedor Teste"));
    }

    @Test
    void alterar_DeveAlterarVendedor() throws Exception {
        when(vendedorService.alterar(any(Vendedor.class), eq(1))).thenReturn(vendedor);

        mockMvc.perform(put("/api/vendedores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(vendedorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendedorNome").value("Vendedor Teste"));
    }

    @Test
    void deletar_DeveDeletarVendedor() throws Exception {
        doNothing().when(vendedorService).deleta(1);

        mockMvc.perform(delete("/api/vendedores/1"))
                .andExpect(status().isOk());
    }
}

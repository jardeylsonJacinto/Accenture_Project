package acc.accenture.pedido.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import acc.accenture.pedido.dtos.PedidoRequest;
import acc.accenture.pedido.dtos.PedidoResponse;
import acc.accenture.pedido.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Define o código de status como 201 (Criado)
    public ResponseEntity<PedidoResponse> criarPedido(@RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.criarPedido(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {
        List<PedidoResponse> pedidos = pedidoService.listarPedidos();
        return pedidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedidoPorId(@PathVariable Long id) {
        return pedidoService.buscarPedidoPorId(id);
    }

    // Novo endpoint para atualizar o status do pedido
    @PatchMapping("/{id}/status/{novoStatus}")
    public ResponseEntity<Void> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestParam Long clienteId,
            @PathVariable Long novoStatus) {

        pedidoService.atualizarStatusPedido(id, clienteId, novoStatus);
        return ResponseEntity.noContent().build(); // Retorna 204 (Sem conteúdo)
    }
}

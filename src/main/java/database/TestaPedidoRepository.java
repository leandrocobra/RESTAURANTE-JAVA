package database;

import model.Pedido;
import model.enums.StatusPagamentoPedido;
import model.enums.StatusPreparoPedido;
import repository.PedidoRepository;

import java.util.List;

public class TestaPedidoRepository {
    public static void main(String[] args) {
        PedidoRepository pedidoRepository = new PedidoRepository();

        System.out.println("=== LISTAR TODOS ANTES ===");
        List<Pedido> pedidosAntes = pedidoRepository.listarTodos();
        for (Pedido pedido : pedidosAntes) {
            imprimirPedido(pedido);
        }

        System.out.println("\n=== INSERIR NOVO PEDIDO ===");
        Pedido novoPedido = new Pedido(0, 1);
        pedidoRepository.salvar(novoPedido);
        System.out.println("Pedido salvo com ID: " + novoPedido.getId());

        System.out.println("\n=== BUSCAR POR ID ===");
        Pedido pedidoPorId = pedidoRepository.buscarPorId(novoPedido.getId());
        if (pedidoPorId != null) {
            imprimirPedido(pedidoPorId);
        } else {
            System.out.println("Pedido não encontrado.");
        }

        System.out.println("\n=== BUSCAR PEDIDO ATIVO POR MESA ===");
        Pedido pedidoAtivo = pedidoRepository.buscarPedidoAtivoPorMesa(1);
        if (pedidoAtivo != null) {
            imprimirPedido(pedidoAtivo);
        } else {
            System.out.println("Nenhum pedido ativo encontrado para a mesa.");
        }

        System.out.println("\n=== ATUALIZAR STATUS PREPARO ===");
        pedidoRepository.atualizarStatusPreparo(novoPedido.getId(), StatusPreparoPedido.EM_PREPARO);

        Pedido pedidoAtualizadoPreparo = pedidoRepository.buscarPorId(novoPedido.getId());
        if (pedidoAtualizadoPreparo != null) {
            imprimirPedido(pedidoAtualizadoPreparo);
        }

        System.out.println("\n=== ATUALIZAR STATUS PAGAMENTO ===");
        pedidoRepository.atualizarStatusPagamento(novoPedido.getId(), StatusPagamentoPedido.FECHADO);

        Pedido pedidoAtualizadoPagamento = pedidoRepository.buscarPorId(novoPedido.getId());
        if (pedidoAtualizadoPagamento != null) {
            imprimirPedido(pedidoAtualizadoPagamento);
        }

        System.out.println("\n=== LISTAR TODOS DEPOIS ===");
        List<Pedido> pedidosDepois = pedidoRepository.listarTodos();
        for (Pedido pedido : pedidosDepois) {
            imprimirPedido(pedido);
        }
    }

    private static void imprimirPedido(Pedido pedido) {
        System.out.println(
                "ID: " + pedido.getId() +
                        " | Mesa ID: " + pedido.getMesaId() +
                        " | Status preparo: " + pedido.getStatusPreparo() +
                        " | Status pagamento: " + pedido.getStatusPagamento()
        );
    }
}
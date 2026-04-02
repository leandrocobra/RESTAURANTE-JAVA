package repository;

import model.Pedido;
import model.enums.StatusPagamentoPedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    private List<Pedido> pedidos;

    public PedidoRepository() {
        this.pedidos = new ArrayList<>();
    }

    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }

    public Pedido buscarPorId(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    public Pedido buscarPedidoAtivoPorMesa(int mesaId) {
        for (Pedido pedido : pedidos) {
            if (pedido.getStatusPagamento() == StatusPagamentoPedido.ABERTO
                    && pedido.getMesaId() == mesaId) {
                return pedido;
            }
        }
        return null;
    }
}
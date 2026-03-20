package repository;

import model.Pedido;
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
}

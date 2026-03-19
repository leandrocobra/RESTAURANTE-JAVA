package repository;

import model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    private List<Pedido> pedidos;

    public PedidoRepository(){
        this.pedidos = new ArrayList<>();
    }
}

package repository;

import model.ItemPedido;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoRepository {
    private List<ItemPedido> itensPedidos;

    public ItemPedidoRepository(){
        this.itensPedidos = new ArrayList<>();
    }
}

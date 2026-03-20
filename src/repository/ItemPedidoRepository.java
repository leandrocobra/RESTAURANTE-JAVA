package repository;

import model.ItemPedido;
import model.Pedido;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoRepository {

    private List<ItemPedido> itensPedidos;

    public ItemPedidoRepository() {
        this.itensPedidos = new ArrayList<>();
    }

    public void salvar(ItemPedido itemPedido) {
        this.itensPedidos.add(itemPedido);
    }

    public List<ItemPedido> listarTodos() {
        return itensPedidos;
    }

    public ItemPedido buscarPorId(int id) {
        for (ItemPedido itemPedido : itensPedidos) {
            if (itemPedido.getId() == id) {
                return itemPedido;
            }
        }
        return null;
    }

    public List<ItemPedido> listarPorPedidoId(int pedidoId) {

        List<ItemPedido> itemPedidoEncontrado = new ArrayList<>();

        for (ItemPedido itemPedido : itensPedidos) {
            if (itemPedido.getPedidoId() == pedidoId) {
                itemPedidoEncontrado.add(itemPedido);
            }
        }
        return itemPedidoEncontrado;
    }
}
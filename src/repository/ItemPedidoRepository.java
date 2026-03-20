package repository;

import model.ItemPedido;
import model.Mesa;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoRepository {
    private List<ItemPedido> itensPedidos;

    public ItemPedidoRepository(){
        this.itensPedidos = new ArrayList<>();
    }

    public void salvar(ItemPedido itemPedido){

        this.itensPedidos.add(itemPedido);
    }

    public List<ItemPedido> listarTodos(){

        return itensPedidos;
    }

}

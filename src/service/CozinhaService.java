package service;

import model.Pedido;
import model.enums.StatusPreparoPedido;
import repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;

public class CozinhaService {
    private PedidoRepository pedidoRepository;

    public CozinhaService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarPedidosRecebidos(){

        List<Pedido> todosPedidos = pedidoRepository.listarTodos();
        List<Pedido> pedidosStatusRecebido = new ArrayList<>();

        if (todosPedidos == null || todosPedidos.isEmpty()) {
            return pedidosStatusRecebido;
        }

        for (Pedido pedido : todosPedidos) {
            if (pedido.getStatusPreparo() == StatusPreparoPedido.RECEBIDO) {
                pedidosStatusRecebido.add(pedido);
            }
        }
        return pedidosStatusRecebido;
    }
}

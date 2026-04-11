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

    public List<Pedido> listarPedidosRecebidos() {
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

    public Pedido iniciarPreparo(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null || pedido.getStatusPreparo() != StatusPreparoPedido.RECEBIDO) {
            return null;
        }

        pedidoRepository.atualizarStatusPreparo(pedidoId, StatusPreparoPedido.EM_PREPARO);
        pedido.setStatusPreparo(StatusPreparoPedido.EM_PREPARO);

        return pedido;
    }

    public Pedido marcarPronto(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null || pedido.getStatusPreparo() != StatusPreparoPedido.EM_PREPARO) {
            return null;
        }

        pedidoRepository.atualizarStatusPreparo(pedidoId, StatusPreparoPedido.PRONTO);
        pedido.setStatusPreparo(StatusPreparoPedido.PRONTO);

        return pedido;
    }

    public Pedido marcarEntregue(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null || pedido.getStatusPreparo() != StatusPreparoPedido.PRONTO) {
            return null;
        }

        pedidoRepository.atualizarStatusPreparo(pedidoId, StatusPreparoPedido.ENTREGUE);
        pedido.setStatusPreparo(StatusPreparoPedido.ENTREGUE);

        return pedido;
    }

    public List<Pedido> listarPedidosEmPreparo() {
        List<Pedido> todosPedidos = pedidoRepository.listarTodos();
        List<Pedido> pedidosStatusEmPreparo = new ArrayList<>();

        if (todosPedidos == null || todosPedidos.isEmpty()) {
            return pedidosStatusEmPreparo;
        }

        for (Pedido pedido : todosPedidos) {
            if (pedido.getStatusPreparo() == StatusPreparoPedido.EM_PREPARO) {
                pedidosStatusEmPreparo.add(pedido);
            }
        }

        return pedidosStatusEmPreparo;
    }

    public List<Pedido> listarPedidosProntos() {
        List<Pedido> todosPedidos = pedidoRepository.listarTodos();
        List<Pedido> pedidosStatusPronto = new ArrayList<>();

        if (todosPedidos == null || todosPedidos.isEmpty()) {
            return pedidosStatusPronto;
        }

        for (Pedido pedido : todosPedidos) {
            if (pedido.getStatusPreparo() == StatusPreparoPedido.PRONTO) {
                pedidosStatusPronto.add(pedido);
            }
        }

        return pedidosStatusPronto;
    }
}
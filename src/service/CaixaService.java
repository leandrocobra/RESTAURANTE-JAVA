package service;

import model.ItemPedido;
import model.Mesa;
import model.Pedido;
import model.enums.StatusMesa;
import model.enums.StatusPagamentoPedido;
import repository.ItemPedidoRepository;
import repository.MesaRepository;
import repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;

public class CaixaService {
    private PedidoRepository pedidoRepository;
    private MesaRepository mesaRepository;
    private ItemPedidoRepository itemPedidoRepository;

    public CaixaService(PedidoRepository pedidoRepository, MesaRepository mesaRepository,
                        ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<Pedido> listarPedidosAguardandoPagamento() {
        List<Pedido> todosPedidos = pedidoRepository.listarTodos();
        List<Pedido> pedidosAguardandoPagamento = new ArrayList<>();

        if (todosPedidos == null || todosPedidos.isEmpty()) {
            return pedidosAguardandoPagamento;
        }

        for (Pedido pedido : todosPedidos) {
            if (pedido.getStatusPagamento() == StatusPagamentoPedido.FECHADO) {
                pedidosAguardandoPagamento.add(pedido);
            }
        }

        return pedidosAguardandoPagamento;
    }

    public double calcularTotalPedido(int pedidoId) {

        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            return 0;
        }

        double total = 0;

        List<ItemPedido> itensPedido = itemPedidoRepository.listarPorPedidoId(pedidoId);
        if (itensPedido == null || itensPedido.isEmpty()) {
            return 0;
        }

        for (ItemPedido itemPedido : itensPedido) {
            total = total + (itemPedido.getQuantidade() * itemPedido.getPrecoUnitario());
        }

        return total;
    }

    public Pedido pagarPedido (int pedidoId){
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null || pedido.getStatusPagamento() != StatusPagamentoPedido.FECHADO){
            return null;
        }

        Mesa mesa = mesaRepository.buscarPorId(pedido.getMesaId());
        if (mesa == null || mesa.getStatus() != StatusMesa.AGUARDANDO_PAGAMENTO){
            return null;
        }

        mesa.setStatus(StatusMesa.LIVRE);
        pedido.setStatusPagamento(StatusPagamentoPedido.PAGO);

        return pedido;
    }
}

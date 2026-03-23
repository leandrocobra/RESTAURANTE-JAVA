package service;

import model.Pedido;
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

    public List<Pedido> listarPedidosAguardandoPagamento(){
        List<Pedido> todosPedidos = pedidoRepository.listarTodos();
        List<Pedido> pedidosAguardandoPagamento = new ArrayList<>();

        if (todosPedidos == null  || todosPedidos.isEmpty()){
            return pedidosAguardandoPagamento;
        }

        for (Pedido pedido : todosPedidos){
            if (pedido.getStatusPagamento() == StatusPagamentoPedido.FECHADO){
                pedidosAguardandoPagamento.add(pedido);
            }
        }

        return pedidosAguardandoPagamento;
    }




}

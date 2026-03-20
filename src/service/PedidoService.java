package service;

import model.Mesa;
import model.Pedido;
import model.enums.StatusMesa;
import repository.MesaRepository;
import repository.PedidoRepository;

public class PedidoService {

    private MesaRepository mesaRepository;
    private PedidoRepository pedidoRepository;

    public PedidoService(MesaRepository mesaRepository, PedidoRepository pedidoRepository) {
        this.mesaRepository = mesaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido abrirPedido(int numeroMesa) {
        Mesa mesa = mesaRepository.buscarPorNumero(numeroMesa);

        if (mesa == null) {
            return null;
        }

        if (mesa.getStatus() != StatusMesa.LIVRE) {
            return null;
        }

        Pedido pedidoAtivo = pedidoRepository.buscarPedidoAtivoPorMesa(mesa.getId());

        if (pedidoAtivo != null) {
            return null;
        }

        int novoId = pedidoRepository.listarTodos().size() + 1;
        Pedido novoPedido = new Pedido(novoId, mesa.getId());
        mesa.setStatus(StatusMesa.OCUPADA);
        pedidoRepository.salvar(novoPedido);

        return novoPedido;
    }
}


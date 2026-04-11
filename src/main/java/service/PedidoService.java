package service;

import model.ItemPedido;
import model.Mesa;
import model.Pedido;
import model.Produto;
import model.enums.StatusMesa;
import model.enums.StatusPagamentoPedido;
import model.enums.StatusPreparoPedido;
import repository.ItemPedidoRepository;
import repository.MesaRepository;
import repository.PedidoRepository;
import repository.ProdutoRepository;

import java.util.List;

public class PedidoService {

    private MesaRepository mesaRepository;
    private PedidoRepository pedidoRepository;
    private ProdutoRepository produtoRepository;
    private ItemPedidoRepository itemPedidoRepository;

    public PedidoService(MesaRepository mesaRepository, PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.mesaRepository = mesaRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
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

        Pedido novoPedido = new Pedido(0, mesa.getId());
        pedidoRepository.salvar(novoPedido);

        mesaRepository.atualizarStatus(mesa.getId(), StatusMesa.OCUPADA);
        mesa.setStatus(StatusMesa.OCUPADA);

        return novoPedido;
    }

    public ItemPedido adicionarItemAoPedido(int numeroMesa, int produtoId, int quantidade) {
        Mesa mesa = mesaRepository.buscarPorNumero(numeroMesa);

        if (mesa == null) {
            return null;
        }

        Pedido pedido = pedidoRepository.buscarPedidoAtivoPorMesa(mesa.getId());

        if (pedido == null) {
            return null;
        }

        if (pedido.getStatusPagamento() != StatusPagamentoPedido.ABERTO) {
            return null;
        }

        Produto produto = produtoRepository.buscarPorId(produtoId);

        if (produto == null) {
            return null;
        }

        if (!produto.isAtivo()) {
            return null;
        }

        if (quantidade <= 0) {
            return null;
        }

        ItemPedido novoItem = new ItemPedido(
                0,
                pedido.getId(),
                produto.getId(),
                quantidade,
                produto.getPreco()
        );

        itemPedidoRepository.salvar(novoItem);

        return novoItem;
    }

    public List<ItemPedido> visualizarComanda(int numeroMesa) {
        Mesa mesa = mesaRepository.buscarPorNumero(numeroMesa);

        if (mesa == null) {
            return null;
        }

        Pedido pedidoAtivo = pedidoRepository.buscarPedidoAtivoPorMesa(mesa.getId());

        if (pedidoAtivo == null) {
            return null;
        }

        return itemPedidoRepository.listarPorPedidoId(pedidoAtivo.getId());
    }

    public Pedido fecharPedido(int numeroMesa) {
        Mesa mesa = mesaRepository.buscarPorNumero(numeroMesa);

        if (mesa == null || mesa.getStatus() != StatusMesa.OCUPADA) {
            return null;
        }

        Pedido pedidoAtivo = pedidoRepository.buscarPedidoAtivoPorMesa(mesa.getId());

        if (pedidoAtivo == null || pedidoAtivo.getStatusPreparo() != StatusPreparoPedido.ENTREGUE) {
            return null;
        }

        List<ItemPedido> itensPedido = itemPedidoRepository.listarPorPedidoId(pedidoAtivo.getId());

        if (itensPedido == null || itensPedido.isEmpty()) {
            return null;
        }

        pedidoRepository.atualizarStatusPagamento(pedidoAtivo.getId(), StatusPagamentoPedido.FECHADO);
        mesaRepository.atualizarStatus(mesa.getId(), StatusMesa.AGUARDANDO_PAGAMENTO);

        pedidoAtivo.setStatusPagamento(StatusPagamentoPedido.FECHADO);
        mesa.setStatus(StatusMesa.AGUARDANDO_PAGAMENTO);

        return pedidoAtivo;
    }
}
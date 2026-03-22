package service;

import model.ItemPedido;
import model.Mesa;
import model.Pedido;
import model.Produto;
import model.enums.StatusMesa;
import model.enums.StatusPagamentoPedido;
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

        int novoId = pedidoRepository.listarTodos().size() + 1;
        Pedido novoPedido = new Pedido(novoId, mesa.getId());
        mesa.setStatus(StatusMesa.OCUPADA);
        pedidoRepository.salvar(novoPedido);

        return novoPedido;
    }

    public ItemPedido adicionarItemAoPedido(int numeroMesa, int produtoId, int quantidade) {

        Mesa mesa = mesaRepository.buscarPorNumero(numeroMesa);

        if (mesa == null) {
            return null;
        }

        Pedido pedido = pedidoRepository.buscarPedidoAtivoPorMesa(
                mesaRepository.buscarPorNumero(numeroMesa).getId());

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

        if ((!produto.isAtivo())) {
            return null;
        }

        if (quantidade <= 0) {
            return null;
        }

        int novoId = itemPedidoRepository.listarTodos().size() + 1;

        ItemPedido novoItem = new ItemPedido(novoId, pedido.getId(), produto.getId(),
                quantidade, produto.getPreco());

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

        if (mesa == null) {
            return null;
        }

        Pedido pedidoAtivo = pedidoRepository.buscarPedidoAtivoPorMesa(mesa.getId());

        if (pedidoAtivo == null) {
            return null;
        }

        List<ItemPedido> itemPedidoEncontrado = itemPedidoRepository.listarPorPedidoId(pedidoAtivo.getId());

        if (itemPedidoEncontrado == null || itemPedidoEncontrado.isEmpty()){
            return null;
        }

        pedidoAtivo.setStatusPagamento(StatusPagamentoPedido.FECHADO);
        mesa.setStatus(StatusMesa.AGUARDANDO_PAGAMENTO);

        return pedidoAtivo;
    }


}


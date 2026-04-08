package database;

import model.ItemPedido;
import model.Mesa;
import model.Produto;
import repository.ItemPedidoRepository;
import repository.MesaRepository;
import repository.ProdutoRepository;

import java.sql.*;
import java.util.List;

public class TestaItemPedidoRepository {
    public static void main(String[] args) {
        ItemPedidoRepository itemPedidoRepository = new ItemPedidoRepository();
        MesaRepository mesaRepository = new MesaRepository();
        ProdutoRepository produtoRepository = new ProdutoRepository();

        Integer pedidoIdApoio = null;
        Integer itemIdCriado = null;

        try {
            Mesa mesa = buscarPrimeiraMesa(mesaRepository);
            Produto produto = buscarPrimeiroProduto(produtoRepository);

            if (mesa == null) {
                System.out.println("Nenhuma mesa encontrada no banco.");
                return;
            }

            if (produto == null) {
                System.out.println("Nenhum produto encontrado no banco.");
                return;
            }

            pedidoIdApoio = criarPedidoDeApoio(mesa.getId());

            System.out.println("=== INSERIR ITEM ===");
            ItemPedido novoItem = new ItemPedido(
                    0,
                    pedidoIdApoio,
                    produto.getId(),
                    2,
                    produto.getPreco()
            );

            itemPedidoRepository.salvar(novoItem);
            itemIdCriado = novoItem.getId();

            System.out.println(
                    "Item salvo -> ID: " + novoItem.getId() +
                            " | Pedido ID: " + novoItem.getPedidoId() +
                            " | Produto ID: " + novoItem.getProdutoId() +
                            " | Quantidade: " + novoItem.getQuantidade() +
                            " | Preço unitário: " + novoItem.getPrecoUnitario()
            );

            System.out.println("\n=== BUSCAR ITEM POR ID ===");
            ItemPedido itemBuscado = itemPedidoRepository.buscarPorId(novoItem.getId());
            if (itemBuscado != null) {
                imprimirItem(itemBuscado);
            } else {
                System.out.println("Item não encontrado.");
            }

            System.out.println("\n=== LISTAR ITENS POR PEDIDO ID ===");
            List<ItemPedido> itensDoPedido = itemPedidoRepository.listarPorPedidoId(pedidoIdApoio);
            for (ItemPedido item : itensDoPedido) {
                imprimirItem(item);
            }

            System.out.println("\n=== LISTAR TODOS OS ITENS ===");
            List<ItemPedido> todosItens = itemPedidoRepository.listarTodos();
            for (ItemPedido item : todosItens) {
                imprimirItem(item);
            }

        } finally {
            if (itemIdCriado != null) {
                deletarItem(itemIdCriado);
            }

            if (pedidoIdApoio != null) {
                deletarPedido(pedidoIdApoio);
            }
        }
    }

    private static Mesa buscarPrimeiraMesa(MesaRepository mesaRepository) {
        List<Mesa> mesas = mesaRepository.listarTodos();
        if (mesas.isEmpty()) {
            return null;
        }
        return mesas.get(0);
    }

    private static Produto buscarPrimeiroProduto(ProdutoRepository produtoRepository) {
        List<Produto> produtos = produtoRepository.listarTodos();
        if (produtos.isEmpty()) {
            return null;
        }
        return produtos.get(0);
    }

    private static int criarPedidoDeApoio(int mesaId) {
        String sql = "INSERT INTO pedidos (mesa_id, status_preparo, status_pagamento) VALUES (?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setInt(1, mesaId);
            stmt.setString(2, "RECEBIDO");
            stmt.setString(3, "ABERTO");

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new RuntimeException("Não foi possível obter o ID do pedido de apoio.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar pedido de apoio para o teste.", e);
        }
    }

    private static void deletarItem(int itemId) {
        String sql = "DELETE FROM itens_pedido WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, itemId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover item de teste.", e);
        }
    }

    private static void deletarPedido(int pedidoId) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, pedidoId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover pedido de apoio do teste.", e);
        }
    }

    private static void imprimirItem(ItemPedido item) {
        System.out.println(
                "ID: " + item.getId() +
                        " | Pedido ID: " + item.getPedidoId() +
                        " | Produto ID: " + item.getProdutoId() +
                        " | Quantidade: " + item.getQuantidade() +
                        " | Preço unitário: " + item.getPrecoUnitario()
        );
    }
}
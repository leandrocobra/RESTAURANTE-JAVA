package repository;

import database.ConnectionFactory;
import model.ItemPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoRepository {

    public void salvar(ItemPedido itemPedido) {
        String sql = "INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setInt(1, itemPedido.getPedidoId());
            stmt.setInt(2, itemPedido.getProdutoId());
            stmt.setInt(3, itemPedido.getQuantidade());
            stmt.setDouble(4, itemPedido.getPrecoUnitario());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    itemPedido.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar item do pedido.", e);
        }
    }

    public List<ItemPedido> listarTodos() {
        String sql = "SELECT id, pedido_id, produto_id, quantidade, preco_unitario FROM itens_pedido ORDER BY id";
        List<ItemPedido> itensPedido = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                itensPedido.add(mapearItemPedido(rs));
            }

            return itensPedido;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens do pedido.", e);
        }
    }

    public ItemPedido buscarPorId(int id) {
        String sql = "SELECT id, pedido_id, produto_id, quantidade, preco_unitario FROM itens_pedido WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearItemPedido(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item do pedido por id.", e);
        }
    }

    public List<ItemPedido> listarPorPedidoId(int pedidoId) {
        String sql = "SELECT id, pedido_id, produto_id, quantidade, preco_unitario FROM itens_pedido WHERE pedido_id = ? ORDER BY id";
        List<ItemPedido> itensPedidoEncontrados = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, pedidoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    itensPedidoEncontrados.add(mapearItemPedido(rs));
                }
            }

            return itensPedidoEncontrados;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens por pedido.", e);
        }
    }

    private ItemPedido mapearItemPedido(ResultSet rs) throws SQLException {
        return new ItemPedido(
                rs.getInt("id"),
                rs.getInt("pedido_id"),
                rs.getInt("produto_id"),
                rs.getInt("quantidade"),
                rs.getDouble("preco_unitario")
        );
    }
}
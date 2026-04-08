package repository;

import database.ConnectionFactory;
import model.Pedido;
import model.enums.StatusPagamentoPedido;
import model.enums.StatusPreparoPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    public void salvar(Pedido pedido) {
        String sql = "INSERT INTO pedidos (mesa_id, status_preparo, status_pagamento) VALUES (?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setInt(1, pedido.getMesaId());
            stmt.setString(2, pedido.getStatusPreparo().name());
            stmt.setString(3, pedido.getStatusPagamento().name());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pedido.", e);
        }
    }

    public List<Pedido> listarTodos() {
        String sql = "SELECT id, mesa_id, status_preparo, status_pagamento FROM pedidos ORDER BY id";
        List<Pedido> pedidos = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                pedidos.add(mapearPedido(rs));
            }

            return pedidos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pedidos.", e);
        }
    }

    public Pedido buscarPorId(int id) {
        String sql = "SELECT id, mesa_id, status_preparo, status_pagamento FROM pedidos WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido por id.", e);
        }
    }

    public Pedido buscarPedidoAtivoPorMesa(int mesaId) {
        String sql = """
                SELECT id, mesa_id, status_preparo, status_pagamento
                FROM pedidos
                WHERE mesa_id = ? AND status_pagamento = ?
                ORDER BY id DESC
                LIMIT 1
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, mesaId);
            stmt.setString(2, StatusPagamentoPedido.ABERTO.name());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido ativo por mesa.", e);
        }
    }

    public void atualizarStatusPreparo(int pedidoId, StatusPreparoPedido statusPreparo) {
        String sql = "UPDATE pedidos SET status_preparo = ? WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, statusPreparo.name());
            stmt.setInt(2, pedidoId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status de preparo do pedido.", e);
        }
    }

    public void atualizarStatusPagamento(int pedidoId, StatusPagamentoPedido statusPagamento) {
        String sql = "UPDATE pedidos SET status_pagamento = ? WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, statusPagamento.name());
            stmt.setInt(2, pedidoId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status de pagamento do pedido.", e);
        }
    }

    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido(
                rs.getInt("id"),
                rs.getInt("mesa_id")
        );

        pedido.setStatusPreparo(StatusPreparoPedido.valueOf(rs.getString("status_preparo")));
        pedido.setStatusPagamento(StatusPagamentoPedido.valueOf(rs.getString("status_pagamento")));

        return pedido;
    }
}
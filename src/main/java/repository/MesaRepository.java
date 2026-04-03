package repository;

import database.ConnectionFactory;
import model.Mesa;
import model.enums.StatusMesa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaRepository {

    public void salvar(Mesa mesa) {
        String sql = "INSERT INTO mesas (numero, status) VALUES (?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setInt(1, mesa.getNumero());
            stmt.setString(2, mesa.getStatus().name());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    mesa.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar mesa.", e);
        }
    }

    public List<Mesa> listarTodos() {
        String sql = "SELECT id, numero, status FROM mesas ORDER BY numero";
        List<Mesa> mesas = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Mesa mesa = mapearMesa(rs);
                mesas.add(mesa);
            }

            return mesas;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar mesas.", e);
        }
    }

    public Mesa buscarPorId(int id) {
        String sql = "SELECT id, numero, status FROM mesas WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMesa(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar mesa por id.", e);
        }
    }

    public Mesa buscarPorNumero(int numero) {
        String sql = "SELECT id, numero, status FROM mesas WHERE numero = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, numero);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMesa(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar mesa por número.", e);
        }
    }

    public void atualizarStatus(int idMesa, StatusMesa status) {
        String sql = "UPDATE mesas SET status = ? WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, status.name());
            stmt.setInt(2, idMesa);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status da mesa.", e);
        }
    }

    private Mesa mapearMesa(ResultSet rs) throws SQLException {
        Mesa mesa = new Mesa(
                rs.getInt("id"),
                rs.getInt("numero")
        );

        mesa.setStatus(StatusMesa.valueOf(rs.getString("status")));
        return mesa;
    }
}
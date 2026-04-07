package repository;

import database.ConnectionFactory;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    public void salvar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco, ativo) VALUES (?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isAtivo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto.", e);
        }
    }

    public List<Produto> listarTodos() {
        String sql = "SELECT id, nome, preco, ativo FROM produtos ORDER BY id";
        List<Produto> produtos = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }

            return produtos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos.", e);
        }
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT id, nome, preco, ativo FROM produtos WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProduto(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por id.", e);
        }
    }

    private Produto mapearProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getDouble("preco")
        );

        produto.setAtivo(rs.getBoolean("ativo"));
        return produto;
    }
}
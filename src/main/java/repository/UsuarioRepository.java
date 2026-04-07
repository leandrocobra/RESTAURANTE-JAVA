package repository;

import database.ConnectionFactory;
import model.Usuario;
import model.enums.PerfilUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (usuario, senha, perfil, ativo) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getPerfil().name());
            stmt.setBoolean(4, usuario.isAtivo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário.", e);
        }
    }

    public List<Usuario> listarTodos() {
        String sql = "SELECT id, usuario, senha, perfil, ativo FROM usuarios ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários.", e);
        }
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT id, usuario, senha, perfil, ativo FROM usuarios WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por id.", e);
        }
    }

    public Usuario buscarPorUsuario(String usuario) {
        String sql = "SELECT id, usuario, senha, perfil, ativo FROM usuarios WHERE usuario = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por username.", e);
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getInt("id"),
                rs.getString("usuario"),
                rs.getString("senha"),
                PerfilUsuario.valueOf(rs.getString("perfil"))
        );

        usuario.setAtivo(rs.getBoolean("ativo"));
        return usuario;
    }
}
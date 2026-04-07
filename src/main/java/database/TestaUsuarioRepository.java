package database;

import model.Usuario;
import model.enums.PerfilUsuario;
import repository.UsuarioRepository;

import java.util.List;

public class TestaUsuarioRepository {
    public static void main(String[] args) {
        UsuarioRepository usuarioRepository = new UsuarioRepository();

        System.out.println("=== LISTAR TODOS ===");
        List<Usuario> usuarios = usuarioRepository.listarTodos();
        for (Usuario usuario : usuarios) {
            System.out.println(
                    "ID: " + usuario.getId() +
                            " | Usuário: " + usuario.getUsuario() +
                            " | Senha: " + usuario.getSenha() +
                            " | Perfil: " + usuario.getPerfil() +
                            " | Ativo: " + usuario.isAtivo()
            );
        }

        System.out.println("\n=== BUSCAR POR ID ===");
        Usuario usuarioPorId = usuarioRepository.buscarPorId(1);
        if (usuarioPorId != null) {
            System.out.println(
                    "Usuário encontrado -> ID: " + usuarioPorId.getId() +
                            " | Usuário: " + usuarioPorId.getUsuario() +
                            " | Perfil: " + usuarioPorId.getPerfil() +
                            " | Ativo: " + usuarioPorId.isAtivo()
            );
        } else {
            System.out.println("Usuário não encontrado.");
        }

        System.out.println("\n=== BUSCAR POR USUÁRIO ===");
        Usuario usuarioPorUsername = usuarioRepository.buscarPorUsuario("admin");
        if (usuarioPorUsername != null) {
            System.out.println(
                    "Usuário encontrado -> ID: " + usuarioPorUsername.getId() +
                            " | Usuário: " + usuarioPorUsername.getUsuario() +
                            " | Perfil: " + usuarioPorUsername.getPerfil() +
                            " | Ativo: " + usuarioPorUsername.isAtivo()
            );
        } else {
            System.out.println("Usuário não encontrado.");
        }

        System.out.println("\n=== INSERIR NOVO USUÁRIO ===");
        Usuario novoUsuario = new Usuario(0, "teste_jdbc_usuario", "123", PerfilUsuario.GARCOM);
        usuarioRepository.salvar(novoUsuario);

        System.out.println(
                "Usuário salvo -> ID: " + novoUsuario.getId() +
                        " | Usuário: " + novoUsuario.getUsuario() +
                        " | Perfil: " + novoUsuario.getPerfil() +
                        " | Ativo: " + novoUsuario.isAtivo()
        );

        System.out.println("\n=== BUSCAR NOVO USUÁRIO POR USUÁRIO ===");
        Usuario usuarioInserido = usuarioRepository.buscarPorUsuario("teste_jdbc_usuario");
        if (usuarioInserido != null) {
            System.out.println(
                    "Usuário encontrado -> ID: " + usuarioInserido.getId() +
                            " | Usuário: " + usuarioInserido.getUsuario() +
                            " | Perfil: " + usuarioInserido.getPerfil() +
                            " | Ativo: " + usuarioInserido.isAtivo()
            );
        } else {
            System.out.println("Usuário não encontrado após insert.");
        }
    }
}
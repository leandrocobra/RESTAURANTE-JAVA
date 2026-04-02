package service;

import model.Usuario;
import repository.UsuarioRepository;

public class AutService {

    private UsuarioRepository usuarioRepository;

    public AutService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String usuario, String senha) {
        Usuario usuarioEncontrado = usuarioRepository.buscarPorUsuario(usuario);
        if (usuarioEncontrado == null) {
            return null;
        }

        if (!usuarioEncontrado.getSenha().equals(senha)) {
            return null;
        }

        if (!usuarioEncontrado.isAtivo()) {
            return null;
        }

        return usuarioEncontrado;
    }
}

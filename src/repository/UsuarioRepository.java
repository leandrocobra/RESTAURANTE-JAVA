package repository;

import model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private List<Usuario> usuarios;

    public UsuarioRepository(){
        this.usuarios = new ArrayList<>();
    }
}


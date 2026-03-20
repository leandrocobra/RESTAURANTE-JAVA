package repository;

import model.Mesa;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MesaRepository {

    private List<Mesa> mesas;

    public MesaRepository() {
        this.mesas = new ArrayList<>();
    }

    public void salvar(Mesa mesa) {
        mesas.add(mesa);
    }

    public List<Mesa> listarTodos() {
        return mesas;
    }

    public Mesa buscarPorId(int id) {
        for (Mesa mesa : mesas) {
            if (mesa.getId() == id) {
                return mesa;
            }
        }
        return null;
    }

    public Mesa buscarPorNumero(int numero) {
        for (Mesa mesa : mesas) {
            if (mesa.getNumero() == numero) {
                return mesa;
            }
        }
        return null;
    }
}
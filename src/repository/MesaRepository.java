package repository;

import model.Mesa;
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

    public List<Mesa> listarTodas() {
        return mesas;
    }
}

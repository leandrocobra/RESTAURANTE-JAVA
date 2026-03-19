package model;

import model.enums.StatusMesa;

public class Mesa {
    private int id;
    private int numero;
    private StatusMesa status = StatusMesa.LIVRE;

    public Mesa(int id, int numero) {
        this.id = id;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public StatusMesa getStatus() {
        return status;
    }

    public void setStatus(StatusMesa status) {
        this.status = status;
    }
}

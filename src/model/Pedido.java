package model;

import model.enums.StatusPagamentoPedido;
import model.enums.StatusPreparoPedido;

public class Pedido {
    private int id;
    private int mesaId;
    private StatusPreparoPedido statusPreparo = StatusPreparoPedido.RECEBIDO;
    private StatusPagamentoPedido statusPagamento = StatusPagamentoPedido.ABERTO;

    public Pedido(int id, int mesaId) {
        this.id = id;
        this.mesaId = mesaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMesaId() {
        return mesaId;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    public StatusPreparoPedido getStatusPreparo() {
        return statusPreparo;
    }

    public void setStatusPreparo(StatusPreparoPedido statusPreparo) {
        this.statusPreparo = statusPreparo;
    }

    public StatusPagamentoPedido getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamentoPedido statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}

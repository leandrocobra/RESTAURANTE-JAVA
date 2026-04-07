package database;

import model.Mesa;
import model.enums.StatusMesa;
import repository.MesaRepository;

import java.util.List;

public class TestaMesaRepository {
    public static void main(String[] args) {
        MesaRepository mesaRepository = new MesaRepository();

        System.out.println("=== LISTAR TODAS ===");
        List<Mesa> mesas = mesaRepository.listarTodos();
        for (Mesa mesa : mesas) {
            System.out.println(
                    "ID: " + mesa.getId() +
                            " | Número: " + mesa.getNumero() +
                            " | Status: " + mesa.getStatus()
            );
        }

        System.out.println("\n=== BUSCAR POR NÚMERO ===");
        Mesa mesa1 = mesaRepository.buscarPorNumero(2);
        if (mesa1 != null) {
            System.out.println("Mesa encontrada: " + mesa1.getNumero() + " - " + mesa1.getStatus());
        }

        System.out.println("\n=== ATUALIZAR STATUS ===");
        if (mesa1 != null) {
            mesaRepository.atualizarStatus(mesa1.getId(), StatusMesa.OCUPADA);

            Mesa mesaAtualizada = mesaRepository.buscarPorNumero(1);
            System.out.println("Novo status da mesa 1: " + mesaAtualizada.getStatus());
        }
    }
}
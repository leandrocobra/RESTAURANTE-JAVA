package ui;

import model.Usuario;
import service.AutService;
import java.util.Scanner;

public class LoginUI {
    private Scanner scanner;
    private AutService autService;

    public LoginUI(Scanner scanner, AutService autService) {
        this.scanner = scanner;
        this.autService = autService;
    }

    public Usuario fazerLogin(){
        System.out.print("Usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        return autService.autenticar(usuario, senha);
    }
}

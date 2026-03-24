package ui;

import model.Usuario;
import model.enums.PerfilUsuario;
import repository.MesaRepository;
import repository.ProdutoRepository;
import repository.UsuarioRepository;
import service.CaixaService;
import service.CozinhaService;
import service.PedidoService;

import java.util.Scanner;

public class MenuConsole {
    private Scanner scanner;
    private LoginUI loginUI;
    private CaixaService caixaService;
    private CozinhaService cozinhaService;
    private PedidoService pedidoService;
    private MesaRepository mesaRepository;
    private UsuarioRepository usuarioRepository;
    private ProdutoRepository produtoRepository;

    public MenuConsole(Scanner scanner, LoginUI loginUI, CaixaService caixaService,
                       CozinhaService cozinhaService, PedidoService pedidoService,
                       MesaRepository mesaRepository, UsuarioRepository usuarioRepository,
                       ProdutoRepository produtoRepository) {
        this.scanner = scanner;
        this.loginUI = loginUI;
        this.caixaService = caixaService;
        this.cozinhaService = cozinhaService;
        this.pedidoService = pedidoService;
        this.mesaRepository = mesaRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    private void menuAdm(){
        System.out.println("Menu ADM.");
    }
    private void menuGarcom(){
        System.out.println("Menu GARCOM.");
    }
    private void menuCozinha(){
        System.out.println("Menu COZINHA.");
    }
    private void menuCaixa(){
        System.out.println("Menu CAIXA.");
    }

    public void iniciar() {
        Usuario usuarioLogado = loginUI.fazerLogin();
        if (usuarioLogado == null) {
            System.out.println("Login inválido!");
        } else {
            System.out.println("Login realizado com sucesso.");
            System.out.println("Bem vindo(a) " + usuarioLogado.getUsuario());
        }

        PerfilUsuario perfilUsuario = usuarioLogado.getPerfil();

        switch (perfilUsuario){
            case PerfilUsuario.ADM -> menuAdm();
            case PerfilUsuario.GARCOM -> menuGarcom();
            case PerfilUsuario.COZINHA -> menuCozinha();
            case PerfilUsuario.CAIXA -> menuCaixa();
        }
    }

}

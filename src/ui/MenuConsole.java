package ui;

import model.Mesa;
import model.Pedido;
import model.Usuario;
import model.enums.PerfilUsuario;
import model.enums.StatusMesa;
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

    public void iniciar() {
        Usuario usuarioLogado = loginUI.fazerLogin();
        if (usuarioLogado == null) {
            System.out.println("Login inválido!");
            return;
        } else {
            System.out.println("Login realizado com sucesso.");
            System.out.println("Bem vindo(a) " + usuarioLogado.getUsuario());
        }

        PerfilUsuario perfilUsuario = usuarioLogado.getPerfil();

        switch (perfilUsuario){
            case ADM -> menuAdm();
            case GARCOM -> menuGarcom();
            case COZINHA -> menuCozinha();
            case CAIXA -> menuCaixa();
        }
    }

    private void menuAdm(){
        System.out.println("Menu ADM.");
    }
    private void menuGarcom(){
        int opcao;
        do {
            System.out.println("===Menu GARÇOM.===");
            System.out.println("1 - Abrir pedido.");
            System.out.println("2 - Adicionar item ao pedido.");
            System.out.println("3 - Visualizar comanda.");
            System.out.println("4 - Fechar pedido.");
            System.out.println("0 - Sair");
            System.out.println("Digite sua opção:");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1 -> abrirPedidoUI();
                case 2 -> System.out.println("2 - Adicionar item ao pedido.");
                case 3 -> System.out.println("3 - Visualizar comanda.");
                case 4 -> System.out.println("4 - Fechar pedido.");
                case 0 -> System.out.println("Saindo do menu GARÇOM ...");
                default -> System.out.println("Opção inválida!\n");
            }

        }while (opcao != 0);

    }
    private void menuCozinha(){
        System.out.println("Menu COZINHA.");
    }

    private void menuCaixa(){
        System.out.println("Menu CAIXA.");
    }

    private void abrirPedidoUI(){
        listarMesasLivres();
        System.out.print("Informe o número da mesa: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = pedidoService.abrirPedido(numeroMesa);
        if (pedido == null){
            System.out.println("Não foi possivel abrir o pedido");
        }else {
            System.out.println("Pedido aberto com sucesso:");
            System.out.println("Pedido ID:" + pedido.getId() + "Mesa: " + numeroMesa);
        }
    }

    private void listarMesasLivres(){
        for (Mesa mesa : mesaRepository.listarTodos()){
            if (mesa.getStatus() == StatusMesa.LIVRE){
                System.out.println("Mesa " + mesa.getNumero());
            }
        }
    }
}

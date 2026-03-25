package ui;

import model.*;
import model.enums.PerfilUsuario;
import model.enums.StatusMesa;
import repository.MesaRepository;
import repository.ProdutoRepository;
import repository.UsuarioRepository;
import service.CaixaService;
import service.CozinhaService;
import service.PedidoService;

import java.util.List;
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
                case 2 -> adicionarItemAoPedidoUI();
                case 3 -> visualizarComandaUI();
                case 4 -> fecharPedidoUI();
                case 0 -> System.out.println("Saindo do menu GARÇOM ...");
                default -> System.out.println("Opção inválida!\n");
            }

        }while (opcao != 0);

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

    private void visualizarComandaUI() {
        System.out.print("Informe o número da mesa: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        List<ItemPedido> itensComanda = pedidoService.visualizarComanda(numeroMesa);

        if (itensComanda == null || itensComanda.isEmpty()) {
            System.out.println("Nenhuma comanda ativa encontrada para essa mesa.");
            return;
        }

        double totalComanda = 0;

        System.out.println("=== COMANDA DA MESA " + numeroMesa + " ===");

        for (ItemPedido item : itensComanda) {
            Produto produto = produtoRepository.buscarPorId(item.getProdutoId());
            double subtotal = item.getQuantidade() * item.getPrecoUnitario();
            totalComanda += subtotal;

            System.out.println("Produto: " + produto.getNome());
            System.out.println("Quantidade: " + item.getQuantidade());
            System.out.println("Preço unitário: R$ " + item.getPrecoUnitario());
            System.out.println("Subtotal: R$ " + subtotal);
            System.out.println();
        }
        System.out.println("Total da comanda: R$ " + totalComanda);
    }

    private void fecharPedidoUI() {
        System.out.print("Informe o número da mesa: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        Pedido pedidoFechado = pedidoService.fecharPedido(numeroMesa);

        if (pedidoFechado == null) {
            System.out.println("Não foi possível fechar o pedido.");
            System.out.println("Verifique se a mesa possui pedido ativo, itens e se o pedido já foi ENTREGUE.");
        } else {
            System.out.println("Pedido fechado com sucesso.");
            System.out.println("Pedido ID: " + pedidoFechado.getId() + " | Mesa: " + numeroMesa);
            System.out.println("Status pagamento: " + pedidoFechado.getStatusPagamento());
        }
    }

    private void listarProdutos() {
        System.out.println("=== PRODUTOS ===");
        for (Produto produto : produtoRepository.listarTodos()) {
            System.out.println(produto.getId() + " - " + produto.getNome() + " | R$ " + produto.getPreco());
        }
    }

    private void adicionarItemAoPedidoUI() {
        System.out.print("Informe o número da mesa: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        listarProdutos();

        System.out.print("Informe o ID do produto: ");
        int produtoId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        ItemPedido itemPedido = pedidoService.adicionarItemAoPedido(numeroMesa, produtoId, quantidade);

        if (itemPedido == null) {
            System.out.println("Não foi possível adicionar o item ao pedido.");
        } else {
            System.out.println("Item adicionado com sucesso.");
            System.out.println("Item ID: " + itemPedido.getId());
            System.out.println("Produto ID: " + itemPedido.getProdutoId());
            System.out.println("Quantidade: " + itemPedido.getQuantidade());
        }
    }

    private void menuCozinha(){
        System.out.println("Menu COZINHA.");
    }

    private void menuCaixa(){
        System.out.println("Menu CAIXA.");
    }


}

package ui;

import model.*;
import model.enums.PerfilUsuario;
import model.enums.StatusMesa;
import repository.ItemPedidoRepository;
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
    private ItemPedidoRepository itemPedidoRepository;

    public MenuConsole(Scanner scanner, LoginUI loginUI, CaixaService caixaService,
                       CozinhaService cozinhaService, PedidoService pedidoService,
                       MesaRepository mesaRepository, UsuarioRepository usuarioRepository,
                       ProdutoRepository produtoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.scanner = scanner;
        this.loginUI = loginUI;
        this.caixaService = caixaService;
        this.cozinhaService = cozinhaService;
        this.pedidoService = pedidoService;
        this.mesaRepository = mesaRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
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

        switch (perfilUsuario) {
            case ADM -> menuAdm();
            case GARCOM -> menuGarcom();
            case COZINHA -> menuCozinha();
            case CAIXA -> menuCaixa();
        }
    }

    private void menuAdm() {
        int opcao;

        do {
            System.out.println("=== MENU ADM ===");
            System.out.println("1 - Listar mesas");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Listar usuários");
            System.out.println("4 - Acessar menu GARÇOM");
            System.out.println("5 - Acessar menu COZINHA");
            System.out.println("6 - Acessar menu CAIXA");
            System.out.println("0 - Sair");
            System.out.print("Digite sua opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> listarMesasUI();
                case 2 -> listarProdutosUI();
                case 3 -> listarUsuariosUI();
                case 4 -> menuGarcom();
                case 5 -> menuCozinha();
                case 6 -> menuCaixa();
                case 0 -> System.out.println("Saindo do menu ADM...");
                default -> System.out.println("Opção inválida!\n");
            }

        } while (opcao != 0);
    }

    private void listarMesasUI() {
        System.out.println("=== MESAS ===");

        List<Mesa> mesas = mesaRepository.listarTodos();

        if (mesas == null || mesas.isEmpty()) {
            System.out.println("Nenhuma mesa cadastrada.");
            return;
        }

        for (Mesa mesa : mesas) {
            System.out.println(
                    "ID: " + mesa.getId() +
                            " | Número: " + mesa.getNumero() +
                            " | Status: " + mesa.getStatus()
            );
        }
    }

    private void listarProdutosUI() {
        System.out.println("=== PRODUTOS ===");

        List<Produto> produtos = produtoRepository.listarTodos();

        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            System.out.println(
                    "ID: " + produto.getId() +
                            " | Nome: " + produto.getNome() +
                            " | Preço: R$ " + produto.getPreco()
            );
        }
    }

    private void listarUsuariosUI() {
        System.out.println("=== USUÁRIOS ===");

        List<Usuario> usuarios = usuarioRepository.listarTodos();

        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Usuario usuario : usuarios) {
            System.out.println(
                    "ID: " + usuario.getId() +
                            " | Login: " + usuario.getUsuario() +
                            " | Perfil: " + usuario.getPerfil()
            );
        }
    }

    private void menuGarcom() {
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

            switch (opcao) {
                case 1 -> abrirPedidoUI();
                case 2 -> adicionarItemAoPedidoUI();
                case 3 -> visualizarComandaUI();
                case 4 -> fecharPedidoUI();
                case 0 -> System.out.println("Saindo do menu GARÇOM ...");
                default -> System.out.println("Opção inválida!\n");
            }

        } while (opcao != 0);

    }

    private void abrirPedidoUI() {
        listarMesasLivres();
        System.out.print("Informe o número da mesa: ");
        int numeroMesa = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = pedidoService.abrirPedido(numeroMesa);
        if (pedido == null) {
            System.out.println("Não foi possivel abrir o pedido");
        } else {
            System.out.println("Pedido aberto com sucesso:");
            System.out.println("Pedido ID:" + pedido.getId() + " | Mesa: " + numeroMesa);
        }
    }

    private void listarMesasLivres() {
        for (Mesa mesa : mesaRepository.listarTodos()) {
            if (mesa.getStatus() == StatusMesa.LIVRE) {
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

    private void menuCozinha() {
        int opcao;

        do {
            System.out.println("=== MENU COZINHA ===");
            System.out.println("1 - Listar pedidos recebidos");
            System.out.println("2 - Iniciar preparo");
            System.out.println("3 - Listar pedidos em preparo");
            System.out.println("4 - Marcar pedido como pronto");
            System.out.println("5 - Listar pedidos prontos");
            System.out.println("6 - Marcar pedido como entregue");
            System.out.println("0 - Sair");
            System.out.print("Digite sua opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> listarPedidosRecebidosUI();
                case 2 -> iniciarPreparoUI();
                case 3 -> listarPedidosEmPreparoUI();
                case 4 -> marcarProntoUI();
                case 5 -> listarPedidosProntosUI();
                case 6 -> marcarEntregueUI();
                case 0 -> System.out.println("Saindo do menu COZINHA...");
                default -> System.out.println("Opção inválida!\n");
            }

        } while (opcao != 0);
    }

    private void exibirPedidoComItens(Pedido pedido) {
        System.out.println("Pedido ID: " + pedido.getId());
        System.out.println("Mesa ID: " + pedido.getMesaId());
        System.out.println("Status preparo: " + pedido.getStatusPreparo());
        System.out.println("Status pagamento: " + pedido.getStatusPagamento());
        System.out.println("Itens:");

        List<ItemPedido> itensPedido = itemPedidoRepository.listarPorPedidoId(pedido.getId());

        if (itensPedido == null || itensPedido.isEmpty()) {
            System.out.println("Nenhum item encontrado para este pedido.");
        } else {
            for (ItemPedido item : itensPedido) {
                Produto produto = produtoRepository.buscarPorId(item.getProdutoId());

                System.out.println("- " + produto.getNome() + " | Quantidade: " + item.getQuantidade());
            }
        }
        System.out.println();
    }

    private void listarPedidosRecebidosUI() {
        List<Pedido> pedidosRecebidos = cozinhaService.listarPedidosRecebidos();

        if (pedidosRecebidos == null || pedidosRecebidos.isEmpty()) {
            System.out.println("Nenhum pedido recebido no momento.");
            return;
        }

        System.out.println("=== PEDIDOS RECEBIDOS ===");
        for (Pedido pedido : pedidosRecebidos) {
            exibirPedidoComItens(pedido);
        }
    }

    private void iniciarPreparoUI() {
        System.out.print("Informe o ID do pedido: ");
        int pedidoId = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = cozinhaService.iniciarPreparo(pedidoId);

        if (pedido == null) {
            System.out.println("Não foi possível iniciar o preparo do pedido.");
        } else {
            System.out.println("Preparo iniciado com sucesso.");
            System.out.println(
                    "Pedido ID: " + pedido.getId() +
                            " | Status preparo: " + pedido.getStatusPreparo()
            );
        }
    }

    private void listarPedidosEmPreparoUI() {
        List<Pedido> pedidosEmPreparo = cozinhaService.listarPedidosEmPreparo();

        if (pedidosEmPreparo == null || pedidosEmPreparo.isEmpty()) {
            System.out.println("Nenhum pedido em preparo no momento.");
            return;
        }

        System.out.println("=== PEDIDOS EM PREPARO ===");
        for (Pedido pedido : pedidosEmPreparo) {
            exibirPedidoComItens(pedido);
        }
    }

    private void marcarProntoUI() {
        System.out.print("Informe o ID do pedido: ");
        int pedidoId = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = cozinhaService.marcarPronto(pedidoId);

        if (pedido == null) {
            System.out.println("Não foi possível marcar o pedido como pronto.");
        } else {
            System.out.println("Pedido marcado como pronto com sucesso.");
            System.out.println(
                    "Pedido ID: " + pedido.getId() +
                            " | Status preparo: " + pedido.getStatusPreparo()
            );
        }
    }

    private void listarPedidosProntosUI() {
        List<Pedido> pedidosProntos = cozinhaService.listarPedidosProntos();

        if (pedidosProntos == null || pedidosProntos.isEmpty()) {
            System.out.println("Nenhum pedido pronto no momento.");
            return;
        }

        System.out.println("=== PEDIDOS PRONTOS ===");
        for (Pedido pedido : pedidosProntos) {
            exibirPedidoComItens(pedido);
        }
    }

    private void marcarEntregueUI() {
        System.out.print("Informe o ID do pedido: ");
        int pedidoId = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = cozinhaService.marcarEntregue(pedidoId);

        if (pedido == null) {
            System.out.println("Não foi possível marcar o pedido como entregue.");
        } else {
            System.out.println("Pedido marcado como entregue com sucesso.");
            System.out.println(
                    "Pedido ID: " + pedido.getId() +
                            " | Status preparo: " + pedido.getStatusPreparo()
            );
        }
    }

    private void menuCaixa() {
        int opcao;

        do {
            System.out.println("=== MENU CAIXA ===");
            System.out.println("1 - Listar pedidos aguardando pagamento");
            System.out.println("2 - Pagar pedido");
            System.out.println("0 - Sair");
            System.out.print("Digite sua opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> listarPedidosAguardandoPagamentoUI();
                case 2 -> pagarPedidoUI();
                case 0 -> System.out.println("Saindo do menu CAIXA...");
                default -> System.out.println("Opção inválida!\n");
            }

        } while (opcao != 0);
    }

    private void listarPedidosAguardandoPagamentoUI() {
        List<Pedido> pedidosAguardandoPagamento = caixaService.listarPedidosAguardandoPagamento();

        if (pedidosAguardandoPagamento == null || pedidosAguardandoPagamento.isEmpty()) {
            System.out.println("Nenhum pedido aguardando pagamento no momento.");
            return;
        }

        System.out.println("=== PEDIDOS AGUARDANDO PAGAMENTO ===");
        for (Pedido pedido : pedidosAguardandoPagamento) {
            Mesa mesa = mesaRepository.buscarPorId(pedido.getMesaId());
            int numeroMesa = (mesa != null) ? mesa.getNumero() : -1;

            double totalPedido = caixaService.calcularTotalPedido(pedido.getId());

            System.out.println(
                    "Pedido ID: " + pedido.getId() +
                            " | Mesa: " + (numeroMesa == -1 ? "Não encontrada" : numeroMesa) +
                            " | Status pagamento: " + pedido.getStatusPagamento() +
                            " | Total: R$ " + totalPedido
            );
        }
    }

    private void pagarPedidoUI() {
        System.out.print("Informe o ID do pedido: ");
        int pedidoId = scanner.nextInt();
        scanner.nextLine();

        Pedido pedidoPago = caixaService.pagarPedido(pedidoId);

        if (pedidoPago == null) {
            System.out.println("Não foi possível realizar o pagamento do pedido.");
        } else {
            Mesa mesa = mesaRepository.buscarPorId(pedidoPago.getMesaId());
            String mesaInfo = (mesa != null) ? String.valueOf(mesa.getNumero()) : "Não encontrada";

            System.out.println("Pagamento realizado com sucesso.");
            System.out.println("Pedido ID: " + pedidoPago.getId());
            System.out.println("Mesa: " + mesaInfo);
            System.out.println("Status pagamento: " + pedidoPago.getStatusPagamento());
        }
    }

}

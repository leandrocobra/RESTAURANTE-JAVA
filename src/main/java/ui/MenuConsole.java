package ui;

import model.ItemPedido;
import model.Mesa;
import model.Pedido;
import model.Produto;
import model.Usuario;
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
import java.util.Locale;
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
        exibirTituloSistema();

        Usuario usuarioLogado = loginUI.fazerLogin();

        if (usuarioLogado == null) {
            pularLinha();
            System.out.println("Login inválido.");
            System.out.println("Verifique usuário, senha e status do cadastro.");
            return;
        }

        pularLinha();
        System.out.println("Login realizado com sucesso.");
        System.out.println("Bem-vindo(a), " + usuarioLogado.getUsuario() + ".");

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
            exibirCabecalho("MENU ADM");
            System.out.println("1 - Listar mesas");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Listar usuários");
            System.out.println("4 - Acessar menu GARÇOM");
            System.out.println("5 - Acessar menu COZINHA");
            System.out.println("6 - Acessar menu CAIXA");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Digite sua opção: ");

            pularLinha();

            switch (opcao) {
                case 1 -> listarMesasUI();
                case 2 -> listarProdutosUI();
                case 3 -> listarUsuariosUI();
                case 4 -> menuGarcom();
                case 5 -> menuCozinha();
                case 6 -> menuCaixa();
                case 0 -> System.out.println("Saindo do menu ADM...");
                default -> System.out.println("Opção inválida.");
            }

            pularLinha();
        } while (opcao != 0);
    }

    private void menuGarcom() {
        int opcao;

        do {
            exibirCabecalho("MENU GARÇOM");
            System.out.println("1 - Abrir pedido");
            System.out.println("2 - Adicionar item ao pedido");
            System.out.println("3 - Visualizar comanda");
            System.out.println("4 - Fechar pedido");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Digite sua opção: ");

            pularLinha();

            switch (opcao) {
                case 1 -> abrirPedidoUI();
                case 2 -> adicionarItemAoPedidoUI();
                case 3 -> visualizarComandaUI();
                case 4 -> fecharPedidoUI();
                case 0 -> System.out.println("Saindo do menu GARÇOM...");
                default -> System.out.println("Opção inválida.");
            }

            pularLinha();
        } while (opcao != 0);
    }

    private void menuCozinha() {
        int opcao;

        do {
            exibirCabecalho("MENU COZINHA");
            System.out.println("1 - Listar pedidos recebidos");
            System.out.println("2 - Iniciar preparo");
            System.out.println("3 - Listar pedidos em preparo");
            System.out.println("4 - Marcar pedido como pronto");
            System.out.println("5 - Listar pedidos prontos");
            System.out.println("6 - Marcar pedido como entregue");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Digite sua opção: ");

            pularLinha();

            switch (opcao) {
                case 1 -> listarPedidosRecebidosUI();
                case 2 -> iniciarPreparoUI();
                case 3 -> listarPedidosEmPreparoUI();
                case 4 -> marcarProntoUI();
                case 5 -> listarPedidosProntosUI();
                case 6 -> marcarEntregueUI();
                case 0 -> System.out.println("Saindo do menu COZINHA...");
                default -> System.out.println("Opção inválida.");
            }

            pularLinha();
        } while (opcao != 0);
    }

    private void menuCaixa() {
        int opcao;

        do {
            exibirCabecalho("MENU CAIXA");
            System.out.println("1 - Listar pedidos aguardando pagamento");
            System.out.println("2 - Pagar pedido");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Digite sua opção: ");

            pularLinha();

            switch (opcao) {
                case 1 -> listarPedidosAguardandoPagamentoUI();
                case 2 -> pagarPedidoUI();
                case 0 -> System.out.println("Saindo do menu CAIXA...");
                default -> System.out.println("Opção inválida.");
            }

            pularLinha();
        } while (opcao != 0);
    }

    private void listarMesasUI() {
        exibirCabecalho("MESAS");

        List<Mesa> mesas = mesaRepository.listarTodos();

        if (mesas == null || mesas.isEmpty()) {
            System.out.println("Nenhuma mesa cadastrada.");
            return;
        }

        for (Mesa mesa : mesas) {
            System.out.println(
                    "ID: " + mesa.getId()
                            + " | Número: " + mesa.getNumero()
                            + " | Status: " + mesa.getStatus()
            );
        }
    }

    private void listarProdutosUI() {
        exibirCabecalho("PRODUTOS");

        List<Produto> produtos = produtoRepository.listarTodos();

        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            System.out.println(
                    "ID: " + produto.getId()
                            + " | Nome: " + produto.getNome()
                            + " | Preço: " + formatarMoeda(produto.getPreco())
                            + " | Ativo: " + (produto.isAtivo() ? "SIM" : "NÃO")
            );
        }
    }

    private void listarUsuariosUI() {
        exibirCabecalho("USUÁRIOS");

        List<Usuario> usuarios = usuarioRepository.listarTodos();

        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Usuario usuario : usuarios) {
            System.out.println(
                    "ID: " + usuario.getId()
                            + " | Login: " + usuario.getUsuario()
                            + " | Perfil: " + usuario.getPerfil()
                            + " | Ativo: " + (usuario.isAtivo() ? "SIM" : "NÃO")
            );
        }
    }

    private void abrirPedidoUI() {
        exibirCabecalho("ABRIR PEDIDO");
        listarMesasLivres();

        int numeroMesa = lerInteiro("Informe o número da mesa: ");
        Pedido pedido = pedidoService.abrirPedido(numeroMesa);

        pularLinha();

        if (pedido == null) {
            System.out.println("Não foi possível abrir o pedido.");
            System.out.println("Verifique se a mesa existe e se está LIVRE.");
            return;
        }

        System.out.println("Pedido aberto com sucesso.");
        System.out.println("Pedido ID: " + pedido.getId() + " | Mesa: " + numeroMesa);
    }

    private void adicionarItemAoPedidoUI() {
        exibirCabecalho("ADICIONAR ITEM AO PEDIDO");

        int numeroMesa = lerInteiro("Informe o número da mesa: ");
        pularLinha();

        listarProdutosAtivos();

        int produtoId = lerInteiro("Informe o ID do produto: ");
        int quantidade = lerInteiro("Informe a quantidade: ");

        ItemPedido itemPedido = pedidoService.adicionarItemAoPedido(numeroMesa, produtoId, quantidade);

        pularLinha();

        if (itemPedido == null) {
            System.out.println("Não foi possível adicionar o item ao pedido.");
            System.out.println("Verifique mesa, pedido ativo, produto e quantidade.");
            return;
        }

        Produto produto = produtoRepository.buscarPorId(itemPedido.getProdutoId());

        System.out.println("Item adicionado com sucesso.");
        System.out.println("Item ID: " + itemPedido.getId());
        System.out.println("Produto: " + (produto != null ? produto.getNome() : "Não encontrado"));
        System.out.println("Quantidade: " + itemPedido.getQuantidade());
        System.out.println("Preço unitário: " + formatarMoeda(itemPedido.getPrecoUnitario()));
    }

    private void visualizarComandaUI() {
        exibirCabecalho("VISUALIZAR COMANDA");

        int numeroMesa = lerInteiro("Informe o número da mesa: ");
        List<ItemPedido> itensComanda = pedidoService.visualizarComanda(numeroMesa);

        pularLinha();

        if (itensComanda == null || itensComanda.isEmpty()) {
            System.out.println("Nenhuma comanda ativa encontrada para essa mesa.");
            return;
        }

        double totalComanda = 0;

        System.out.println("Mesa: " + numeroMesa);
        pularLinha();

        for (ItemPedido item : itensComanda) {
            Produto produto = produtoRepository.buscarPorId(item.getProdutoId());
            double subtotal = item.getQuantidade() * item.getPrecoUnitario();
            totalComanda += subtotal;

            System.out.println("Produto: " + (produto != null ? produto.getNome() : "Não encontrado"));
            System.out.println("Quantidade: " + item.getQuantidade());
            System.out.println("Preço unitário: " + formatarMoeda(item.getPrecoUnitario()));
            System.out.println("Subtotal: " + formatarMoeda(subtotal));
            System.out.println("----------------------------------------");
        }

        System.out.println("Total da comanda: " + formatarMoeda(totalComanda));
    }

    private void fecharPedidoUI() {
        exibirCabecalho("FECHAR PEDIDO");

        int numeroMesa = lerInteiro("Informe o número da mesa: ");
        Pedido pedidoFechado = pedidoService.fecharPedido(numeroMesa);

        pularLinha();

        if (pedidoFechado == null) {
            System.out.println("Não foi possível fechar o pedido.");
            System.out.println("Verifique se a mesa possui pedido ativo, itens e se o pedido já foi ENTREGUE.");
            return;
        }

        System.out.println("Pedido fechado com sucesso.");
        System.out.println("Pedido ID: " + pedidoFechado.getId() + " | Mesa: " + numeroMesa);
        System.out.println("Status pagamento: " + pedidoFechado.getStatusPagamento());
    }

    private void listarPedidosRecebidosUI() {
        exibirCabecalho("PEDIDOS RECEBIDOS");

        List<Pedido> pedidosRecebidos = cozinhaService.listarPedidosRecebidos();

        if (pedidosRecebidos == null || pedidosRecebidos.isEmpty()) {
            System.out.println("Nenhum pedido recebido no momento.");
            return;
        }

        for (Pedido pedido : pedidosRecebidos) {
            exibirPedidoComItens(pedido);
        }
    }

    private void iniciarPreparoUI() {
        exibirCabecalho("INICIAR PREPARO");

        int pedidoId = lerInteiro("Informe o ID do pedido: ");
        Pedido pedido = cozinhaService.iniciarPreparo(pedidoId);

        pularLinha();

        if (pedido == null) {
            System.out.println("Não foi possível iniciar o preparo do pedido.");
            return;
        }

        System.out.println("Preparo iniciado com sucesso.");
        System.out.println("Pedido ID: " + pedido.getId() + " | Status preparo: " + pedido.getStatusPreparo());
    }

    private void listarPedidosEmPreparoUI() {
        exibirCabecalho("PEDIDOS EM PREPARO");

        List<Pedido> pedidosEmPreparo = cozinhaService.listarPedidosEmPreparo();

        if (pedidosEmPreparo == null || pedidosEmPreparo.isEmpty()) {
            System.out.println("Nenhum pedido em preparo no momento.");
            return;
        }

        for (Pedido pedido : pedidosEmPreparo) {
            exibirPedidoComItens(pedido);
        }
    }

    private void marcarProntoUI() {
        exibirCabecalho("MARCAR PEDIDO COMO PRONTO");

        int pedidoId = lerInteiro("Informe o ID do pedido: ");
        Pedido pedido = cozinhaService.marcarPronto(pedidoId);

        pularLinha();

        if (pedido == null) {
            System.out.println("Não foi possível marcar o pedido como pronto.");
            return;
        }

        System.out.println("Pedido marcado como pronto com sucesso.");
        System.out.println("Pedido ID: " + pedido.getId() + " | Status preparo: " + pedido.getStatusPreparo());
    }

    private void listarPedidosProntosUI() {
        exibirCabecalho("PEDIDOS PRONTOS");

        List<Pedido> pedidosProntos = cozinhaService.listarPedidosProntos();

        if (pedidosProntos == null || pedidosProntos.isEmpty()) {
            System.out.println("Nenhum pedido pronto no momento.");
            return;
        }

        for (Pedido pedido : pedidosProntos) {
            exibirPedidoComItens(pedido);
        }
    }

    private void marcarEntregueUI() {
        exibirCabecalho("MARCAR PEDIDO COMO ENTREGUE");

        int pedidoId = lerInteiro("Informe o ID do pedido: ");
        Pedido pedido = cozinhaService.marcarEntregue(pedidoId);

        pularLinha();

        if (pedido == null) {
            System.out.println("Não foi possível marcar o pedido como entregue.");
            return;
        }

        System.out.println("Pedido marcado como entregue com sucesso.");
        System.out.println("Pedido ID: " + pedido.getId() + " | Status preparo: " + pedido.getStatusPreparo());
    }

    private void listarPedidosAguardandoPagamentoUI() {
        exibirCabecalho("PEDIDOS AGUARDANDO PAGAMENTO");

        List<Pedido> pedidosAguardandoPagamento = caixaService.listarPedidosAguardandoPagamento();

        if (pedidosAguardandoPagamento == null || pedidosAguardandoPagamento.isEmpty()) {
            System.out.println("Nenhum pedido aguardando pagamento no momento.");
            return;
        }

        for (Pedido pedido : pedidosAguardandoPagamento) {
            Mesa mesa = mesaRepository.buscarPorId(pedido.getMesaId());
            int numeroMesa = mesa != null ? mesa.getNumero() : -1;
            double totalPedido = caixaService.calcularTotalPedido(pedido.getId());

            System.out.println(
                    "Pedido ID: " + pedido.getId()
                            + " | Mesa: " + (numeroMesa == -1 ? "Não encontrada" : numeroMesa)
                            + " | Status pagamento: " + pedido.getStatusPagamento()
                            + " | Total: " + formatarMoeda(totalPedido)
            );
        }
    }

    private void pagarPedidoUI() {
        exibirCabecalho("PAGAR PEDIDO");

        int pedidoId = lerInteiro("Informe o ID do pedido: ");
        Pedido pedidoPago = caixaService.pagarPedido(pedidoId);

        pularLinha();

        if (pedidoPago == null) {
            System.out.println("Não foi possível realizar o pagamento do pedido.");
            return;
        }

        Mesa mesa = mesaRepository.buscarPorId(pedidoPago.getMesaId());
        String mesaInfo = mesa != null ? String.valueOf(mesa.getNumero()) : "Não encontrada";

        System.out.println("Pagamento realizado com sucesso.");
        System.out.println("Pedido ID: " + pedidoPago.getId());
        System.out.println("Mesa: " + mesaInfo);
        System.out.println("Status pagamento: " + pedidoPago.getStatusPagamento());
    }

    private void listarMesasLivres() {
        List<Mesa> mesas = mesaRepository.listarTodos();
        boolean encontrouMesaLivre = false;

        System.out.println("Mesas livres:");

        for (Mesa mesa : mesas) {
            if (mesa.getStatus() == StatusMesa.LIVRE) {
                System.out.println("- Mesa " + mesa.getNumero());
                encontrouMesaLivre = true;
            }
        }

        if (!encontrouMesaLivre) {
            System.out.println("Nenhuma mesa livre no momento.");
        }

        pularLinha();
    }

    private void listarProdutosAtivos() {
        List<Produto> produtos = produtoRepository.listarTodos();

        System.out.println("Produtos disponíveis:");

        for (Produto produto : produtos) {
            if (produto.isAtivo()) {
                System.out.println(
                        produto.getId()
                                + " - " + produto.getNome()
                                + " | " + formatarMoeda(produto.getPreco())
                );
            }
        }

        pularLinha();
    }

    private void exibirPedidoComItens(Pedido pedido) {
        Mesa mesa = mesaRepository.buscarPorId(pedido.getMesaId());

        System.out.println("Pedido ID: " + pedido.getId());
        System.out.println("Mesa: " + (mesa != null ? mesa.getNumero() : "Não encontrada"));
        System.out.println("Status preparo: " + pedido.getStatusPreparo());
        System.out.println("Status pagamento: " + pedido.getStatusPagamento());
        System.out.println("Itens:");

        List<ItemPedido> itensPedido = itemPedidoRepository.listarPorPedidoId(pedido.getId());

        if (itensPedido == null || itensPedido.isEmpty()) {
            System.out.println("Nenhum item encontrado para este pedido.");
        } else {
            for (ItemPedido item : itensPedido) {
                Produto produto = produtoRepository.buscarPorId(item.getProdutoId());
                String nomeProduto = produto != null ? produto.getNome() : "Produto não encontrado";

                System.out.println(
                        "- " + nomeProduto
                                + " | Quantidade: " + item.getQuantidade()
                                + " | Unitário: " + formatarMoeda(item.getPrecoUnitario())
                );
            }
        }

        System.out.println("----------------------------------------");
        pularLinha();
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            if (scanner.hasNextInt()) {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            }

            System.out.println("Entrada inválida. Digite um número inteiro.");
            scanner.nextLine();
        }
    }

    private void exibirTituloSistema() {
        System.out.println();
        System.out.println("========================================");
        System.out.println(" SISTEMA DE GESTÃO DE RESTAURANTE ");
        System.out.println("========================================");
    }

    private void exibirCabecalho(String titulo) {
        System.out.println();
        System.out.println("=== " + titulo + " ===");
    }

    private void pularLinha() {
        System.out.println();
    }

    private String formatarMoeda(double valor) {
        return String.format(Locale.US, "R$ %.2f", valor);
    }
}
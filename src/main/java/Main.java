import repository.ItemPedidoRepository;
import repository.MesaRepository;
import repository.PedidoRepository;
import repository.ProdutoRepository;
import repository.UsuarioRepository;
import service.AutService;
import service.CaixaService;
import service.CozinhaService;
import service.PedidoService;
import ui.LoginUI;
import ui.MenuConsole;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ItemPedidoRepository itemPedidoRepository = new ItemPedidoRepository();
        MesaRepository mesaRepository = new MesaRepository();
        PedidoRepository pedidoRepository = new PedidoRepository();
        ProdutoRepository produtoRepository = new ProdutoRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();

        AutService autService = new AutService(usuarioRepository);
        CaixaService caixaService = new CaixaService(pedidoRepository, mesaRepository, itemPedidoRepository);
        CozinhaService cozinhaService = new CozinhaService(pedidoRepository);
        PedidoService pedidoService = new PedidoService(
                mesaRepository,
                pedidoRepository,
                produtoRepository,
                itemPedidoRepository
        );

        try (Scanner scanner = new Scanner(System.in)) {
            LoginUI loginUI = new LoginUI(scanner, autService);

            MenuConsole menuConsole = new MenuConsole(
                    scanner,
                    loginUI,
                    caixaService,
                    cozinhaService,
                    pedidoService,
                    mesaRepository,
                    usuarioRepository,
                    produtoRepository,
                    itemPedidoRepository
            );

            menuConsole.iniciar();
        }
    }
}
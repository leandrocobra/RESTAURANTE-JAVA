
import model.Mesa;
import model.Produto;
import model.Usuario;
import model.enums.PerfilUsuario;
import repository.*;
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
        PedidoService pedidoService = new PedidoService(mesaRepository, pedidoRepository,
                                                        produtoRepository, itemPedidoRepository);

        mesaRepository.salvar(new Mesa(1, 1));
        mesaRepository.salvar(new Mesa(2, 2));
        mesaRepository.salvar(new Mesa(3, 3));
        mesaRepository.salvar(new Mesa(4, 4));
        mesaRepository.salvar(new Mesa(5, 5));

        usuarioRepository.salvar(new Usuario(1, "adm", "adm", PerfilUsuario.ADM));
        usuarioRepository.salvar(new Usuario(2, "garcom", "garcom", PerfilUsuario.GARCOM));
        usuarioRepository.salvar(new Usuario(3, "cozinha", "cozinha", PerfilUsuario.COZINHA));
        usuarioRepository.salvar(new Usuario(4, "caixa", "caixa", PerfilUsuario.CAIXA));

        produtoRepository.salvar(new Produto(1, "Baguncinha", 17.50));
        produtoRepository.salvar(new Produto(2, "Coca-Cola 1L", 7.50));
        produtoRepository.salvar(new Produto(3, "Agua 500ml", 4.00));
        produtoRepository.salvar(new Produto(4, "Porção de batata", 10.00));

        Scanner scanner = new Scanner(System.in);
        LoginUI loginUI = new LoginUI(scanner, autService);

        MenuConsole menuConsole = new MenuConsole(scanner, loginUI, caixaService, cozinhaService, pedidoService, mesaRepository, usuarioRepository, produtoRepository);

        menuConsole.iniciar();
    }
}
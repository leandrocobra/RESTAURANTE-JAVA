
import repository.*;
import service.AutService;
import service.CaixaService;
import service.CozinhaService;
import service.PedidoService;

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
    }
}
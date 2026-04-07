package database;

import model.Produto;
import repository.ProdutoRepository;

import java.util.List;

public class TesteProdutoRepository {
    public static void main(String[] args) {
        ProdutoRepository produtoRepository = new ProdutoRepository();

        System.out.println("=== LISTAR TODOS ===");
        List<Produto> produtos = produtoRepository.listarTodos();
        for (Produto produto : produtos) {
            System.out.println(
                    "ID: " + produto.getId() +
                            " | Nome: " + produto.getNome() +
                            " | Preço: " + produto.getPreco() +
                            " | Ativo: " + produto.isAtivo()
            );
        }

        System.out.println("\n=== BUSCAR POR ID ===");
        Produto produto = produtoRepository.buscarPorId(1);
        if (produto != null) {
            System.out.println(
                    "Produto encontrado -> ID: " + produto.getId() +
                            " | Nome: " + produto.getNome() +
                            " | Preço: " + produto.getPreco() +
                            " | Ativo: " + produto.isAtivo()
            );
        } else {
            System.out.println("Produto não encontrado.");
        }

        System.out.println("\n=== INSERIR NOVO PRODUTO ===");
        Produto novoProduto = new Produto(0, "Milkshake de Chocolate", 14.00);
        produtoRepository.salvar(novoProduto);

        System.out.println(
                "Produto salvo -> ID: " + novoProduto.getId() +
                        " | Nome: " + novoProduto.getNome() +
                        " | Preço: " + novoProduto.getPreco() +
                        " | Ativo: " + novoProduto.isAtivo()
        );

        System.out.println("\n=== LISTAR TODOS APÓS INSERT ===");
        List<Produto> produtosAtualizados = produtoRepository.listarTodos();
        for (Produto p : produtosAtualizados) {
            System.out.println(
                    "ID: " + p.getId() +
                            " | Nome: " + p.getNome() +
                            " | Preço: " + p.getPreco() +
                            " | Ativo: " + p.isAtivo()
            );
        }
    }
}
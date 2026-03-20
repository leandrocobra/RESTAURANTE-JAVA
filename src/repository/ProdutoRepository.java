package repository;

import model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    private List<Produto> produtos;

    public ProdutoRepository(){
        this.produtos = new ArrayList<>();
    }

    public void salvar(Produto produto){
        produtos.add(produto);
    }

    public List<Produto> listarTodos(){
        return produtos;
    }
}

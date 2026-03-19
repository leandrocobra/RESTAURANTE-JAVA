package repository;

import model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    private List<Produto> produtos;

    public ProdutoRepository(){
        this.produtos = new ArrayList<>();
    }
}

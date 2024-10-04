public class Produto {
    private String nome;


    private Integer preco;

    public Produto(String nome, Integer preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Integer getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Produto produto = (Produto) obj;
        return nome.equals(produto.nome);
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Estoque estoque = new Estoque();

        String[] produtos = new String[3];
        produtos[0] = "batata";
        produtos[1] = "cenoura";
        produtos[2] = "beterraba";

        estoque.iniciarAbastecimento(produtos);
        estoque.iniciarReabastecimento(produtos);
    }
}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService clientes = Executors.newFixedThreadPool(12);
        Estoque estoque = new Estoque();
        Caminhao sidinho = new Caminhao(estoque);

        sidinho.reabastecer();
        estoque.mostrarEstoque();
    }
}
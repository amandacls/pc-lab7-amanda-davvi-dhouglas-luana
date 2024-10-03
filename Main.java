import java.net.CookieManager;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Pedido> listaDePedidos = new LinkedBlockingDeque<Pedido>(3);
        ExecutorService clientes = Executors.newFixedThreadPool(9);
        ExecutorService workers = Executors.newFixedThreadPool(3);

        Estoque estoque = new Estoque();
        Caminhao sidinho = new Caminhao(estoque);

        sidinho.reabastecer();
        estoque.mostrarEstoque();

        // Temporário

        Produto produto1 = new Produto("Macarrao");
        Produto produto2 = new Produto("Sorvete");
        Produto produto3 = new Produto("Queijo");

        Item item1 = new Item(produto1, 5);
        Item item2 = new Item(produto2, 5);
        Item item3 = new Item(produto3, 5);

        Item[] itens = new Item[] {item1, item2, item3};

        Cliente cliente1 = new Cliente("Mannel", clientes);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            cliente1.criarPedido(itens, listaDePedidos);
        }, 0, 5, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Lista de Pedidos: " + listaDePedidos);
        }, 0, 5, TimeUnit.SECONDS);
    }
}
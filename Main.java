import java.net.CookieManager;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Pedido> listaDePedidos = new LinkedBlockingDeque<Pedido>(3);
        ExecutorService clientes = Executors.newFixedThreadPool(9);
        Relatorio relatorio = new Relatorio();

        ExecutorService workers = Executors.newFixedThreadPool(3);

        Estoque estoque = new Estoque(relatorio);
        Caminhao sidinho = new Caminhao(estoque);

        sidinho.reabastecer();
        estoque.mostrarEstoque();

        Produto produto1 = new Produto("Macarrao", 5);
        Produto produto2 = new Produto("Sorvete", 10);
        Produto produto3 = new Produto("Queijo", 15);

        Item item1 = new Item(produto1, 120);
        Item item2 = new Item(produto2, 120);
        Item item3 = new Item(produto3, 120);

        Item[] itens = new Item[] {item1, item2, item3};

        Cliente cliente1 = new Cliente("Mannel", clientes);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        scheduler.scheduleAtFixedRate(() -> {
            cliente1.criarPedido(itens, listaDePedidos);
        }, 0, 10, TimeUnit.SECONDS);

        Thread.sleep(5000);
        Worker w = new Worker(estoque, listaDePedidos, relatorio);
        w.work();
        relatorio.start();
    }
}
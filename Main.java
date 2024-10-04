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

        Produto produto1 = new Produto("Macarrao", 5);
        Produto produto2 = new Produto("Sorvete", 30);
        Produto produto3 = new Produto("Queijo", 20);

        Item item1 = new Item(produto1, 5);
        Item item2 = new Item(produto2, 5);
        Item item3 = new Item(produto3, 5);

        Item[] itens = new Item[] {item1, item2, item3};

        Cliente cliente1 = new Cliente("Mannel", clientes);
        Cliente cliente2 = new Cliente("Giovanni", clientes);
        Cliente cliente3 = new Cliente("Massoni", clientes);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        scheduler.scheduleAtFixedRate(() -> {
            cliente1.criarPedido(itens, listaDePedidos);
            cliente2.criarPedido(itens, listaDePedidos);
            cliente3.criarPedido(itens, listaDePedidos);
        }, 0, 10, TimeUnit.SECONDS);

        Worker worker1 = new Worker(estoque, listaDePedidos, relatorio);
        Worker worker2 = new Worker(estoque, listaDePedidos, relatorio);
        Worker worker3 = new Worker(estoque, listaDePedidos, relatorio);

        workers.submit(() -> {
            worker1.work();
        });

        workers.submit(() -> {
            worker2.work();
        });

        workers.submit(() -> {
            worker3.work();
        });

        relatorio.start();
    }
}
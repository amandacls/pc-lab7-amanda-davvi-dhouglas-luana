import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Relatorio {

    private AtomicInteger totalvendas;
    private AtomicInteger pedidosProcessados;
    private AtomicInteger pedidosRejeitados;

    public Relatorio () {
        this.totalvendas = new AtomicInteger(0);
        this.pedidosProcessados = new AtomicInteger(0);
        this.pedidosRejeitados = new AtomicInteger(0);
    }

    public void addVenda(Integer venda) {
        this.totalvendas.getAndAdd(venda);
    }

    public void addPedidoProcessado() {
        this.pedidosProcessados.incrementAndGet();
    }

    public void addPedidoRejeitado() {
        this.pedidosRejeitados.incrementAndGet();
    }

    public void start() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("RELATÒRIO PERIÓDICO");
            System.out.println("Total vendas: " + totalvendas.get());
            System.out.println("Pedidos: " + pedidosProcessados.get());
            System.out.println("Pedidos rejeitado: " + pedidosRejeitados.get());
        }, 30, 30, TimeUnit.SECONDS);
    }
}

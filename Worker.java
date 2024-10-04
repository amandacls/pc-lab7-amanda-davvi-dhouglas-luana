import java.util.concurrent.*;

public class Worker {

    private Estoque estoque;

    private BlockingQueue<Pedido> filaPedido;

    private Relatorio relatorio;

    public Worker(Estoque estoque, BlockingQueue<Pedido> filaPedido, Relatorio relatorio) {
        this.estoque = estoque;
        this.filaPedido = filaPedido;
        this.relatorio = relatorio;
    }

    public void work() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            Pedido pedido = null;
            try {
                pedido = filaPedido.take();
                estoque.processarPedido(pedido);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}

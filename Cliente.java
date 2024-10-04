import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class Cliente {
    private String nome;
    private ExecutorService pedidos;

    public Cliente(String nome, ExecutorService pedidos) {
        this.nome = nome;
        this.pedidos = pedidos;
    }

    public String getNome() {
        return nome;
    }

    public void criarPedido(Item[] itens, BlockingQueue<Pedido> fila) {
        Pedido pedido = new Pedido(itens, this);
        pedidos.submit(() -> {
            try {
                fila.put(pedido);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

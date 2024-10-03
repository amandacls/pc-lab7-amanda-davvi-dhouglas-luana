import java.util.concurrent.ScheduledExecutorService;

public class Cliente {
    private String nome;
    private ScheduledExecutorService pedidos;

    public Cliente(String nome, ScheduledExecutorService pedidos) {
        this.nome = nome;
        this.pedidos = pedidos;
    }

    public void criarPedido(Item[] itens) {
        Pedido pedido = new Pedido(itens, this);
        pedidos.
    }
}

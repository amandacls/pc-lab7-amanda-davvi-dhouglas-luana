import java.util.Random;

public class Pedido {
    private final int id;
    private Item[] listaItens;
    private Cliente cliente;
    private static final Random random = new Random();

    public Pedido(Item[] itens, Cliente cliente) {
        this.listaItens = itens;
        this.cliente = cliente;
        this.id = random.nextInt();
    }

    public int getId() {
        return id;
    }

    public Item[] getListaItens() {
        return listaItens;
    }

    public void setListaItens(Item[] listaItens) {
        this.listaItens = listaItens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

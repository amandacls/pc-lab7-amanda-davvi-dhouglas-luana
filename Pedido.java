public class Pedido {
    private Item[] listaItens;
    private Cliente cliente;

    public Pedido(Item[] itens, Cliente cliente) {
        listaItens = itens;
        this.cliente = cliente;
    }
}

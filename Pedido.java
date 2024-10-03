public class Pedido {
    private Item[] listaItens;
    private Cliente cliente;

    public Pedido(Item[] itens, Cliente cliente) {
        listaItens = itens;
        this.cliente = cliente;
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

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

    // Verificar simultânea se tem todos os itens, se não tiver Rejeita

    // Verificar todos os itens antes de atualizar, por isso tem o sychronized evitar que um worker altere o estoque ao mesmo tempo
    public synchronized void work() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
             Pedido pedido = filaPedido.poll();

             // Verficiar se da pra pegar no estoqu
             assert pedido != null;
             Item[] itens = pedido.getListaItens();
             Integer totalVenda = 0;

             ExecutorService executor = Executors.newFixedThreadPool(itens.length);

             for (Item item: itens) {
                 if (this.estoque.verificaQuantidadeProduto(item.getProduto(), item.getQuantidade())) {
                     executor.submit(new Estoque.TakeItem(item.getProduto(), item.getQuantidade(), this.estoque));
                     totalVenda += item.getQuantidade() * item.getProduto().getPreco();
                 } else {
                      this.relatorio.addPedidoRejeitado();
                      return;
                 }
             }
             this.relatorio.addVenda(totalVenda);
             this.relatorio.addPedidoProcessado();


        }, 0, 5, TimeUnit.SECONDS);
    }

//    public synchronized void processarPedido(Pedido pedido) {
//        Item[] itens = pedido.getListaItens();
//
//        for (int i = 0; i < itens.length; i++) {
//            Produto produto = itens[i].getProduto();
//            Integer quantideEmEstoque = this.produtos.get(produto);
//            Integer quantidade = itens[i].getQuantidade();
//
//            if (quantideEmEstoque >= quantidade) {
//                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//                scheduler.scheduleAtFixedRate(() -> {
//                    Integer currentAmount = this.produtos.get(produto);
//                    if (currentAmount != null && currentAmount > quantidade) {
//                        this.produtos.put(produto, (currentAmount - quantidade));
//                        scheduler.shutdownNow();
//                    }
//                }, 0, 5, TimeUnit.SECONDS);
//            } else {
//                System.out.println("tirei " + quantidade + " sem esperar");
//            }
//        }
//    }
}

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Estoque {
    private ConcurrentHashMap<Produto, Integer> produtos;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Relatorio relatorio;

    public Estoque(Relatorio relatorio) {
        this.produtos = new ConcurrentHashMap<>();
        this.relatorio = relatorio;
    }

    public boolean verificaQuantidadeProduto(Produto produto, Integer quantidade) {
        boolean resultado;
        lock.readLock().lock();
        try {
            resultado = (this.produtos.get(produto) >= quantidade);
        } finally {
            lock.readLock().unlock();
        }
        return resultado;
    }

    public void processarPedido(Pedido pedido) {
        lock.writeLock().lock();
        try{
            Item[] itens = pedido.getListaItens();
            boolean pedidoAceito = true;

            for (Item item: itens) {
                if (!(this.produtos.get(item.getProduto()) >= item.getQuantidade())) {
                    pedidoAceito = false;
                    break;
                }
            }

            if (pedidoAceito) {
                ExecutorService executor = Executors.newFixedThreadPool(itens.length);

                for (Item item: itens) {
                    executor.submit(() -> {
                        takeItem(item.getProduto(), item.getQuantidade());
                    });
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                relatorio.addPedidoProcessado();
                System.out.printf("Pedido %d do Cliente %s foi processado.\n", pedido.getId(), pedido.getCliente().getNome());
            } else {
                relatorio.addPedidoRejeitado();
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void takeItem(Produto produto, Integer quantidade) {
        Integer quantNoEstoque = this.produtos.get(produto);
        this.produtos.put(produto, (quantNoEstoque - quantidade));

        Integer totalVenda = produto.getPreco() * quantidade;
        relatorio.addVenda(totalVenda);
    }

    public void adicionarItem(Produto produto, Integer quantidade) {
        produtos.merge(produto, quantidade, Integer::sum);
        System.out.printf("Estoque abastecido com %d item de %s.\n", quantidade, produto.getNome());
    }

    public void mostrarEstoque() {
        ScheduledExecutorService agendador = Executors.newScheduledThreadPool(1);

        agendador.scheduleAtFixedRate(() -> {
            produtos.forEach((p, q) -> System.out.println("O produto " + p.getNome() + " ainda tem " + q + " no estoque."));
        }, 0, 15, TimeUnit.SECONDS);
    }
}

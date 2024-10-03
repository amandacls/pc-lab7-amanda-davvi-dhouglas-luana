import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Estoque {
    private ConcurrentHashMap<Produto, Integer> produtos;

    public Estoque() {
        this.produtos = new ConcurrentHashMap<>();
    }

    public void adicionarItem(Produto produto, Integer quantidade) {
        produtos.merge(produto, quantidade, Integer::sum);
        System.out.printf("Estoque abastecido com %d itens de %s.\n", quantidade, produto.getNome());
    }

    public void mostrarEstoque() {
        ScheduledExecutorService agendador = Executors.newScheduledThreadPool(1);

        agendador.scheduleAtFixedRate(() -> {
            produtos.forEach((p, q) -> System.out.println("O produto " + p.getNome() + " ainda tem " + q + " no estoque."));
        }, 0, 15, TimeUnit.SECONDS);
    }
}

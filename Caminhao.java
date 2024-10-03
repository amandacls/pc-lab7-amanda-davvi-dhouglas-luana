import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Caminhao {
    private Estoque estoque;
    private ScheduledExecutorService agendador;
    private static final int INTERVALO = 15;

    public Caminhao(Estoque estoque) {
        this.estoque = estoque;
        agendador = Executors.newScheduledThreadPool(1);
    }

    public void reabastecer() {
        agendador.scheduleAtFixedRate(() -> {
            Produto produto1 = new Produto("Macarrao");
            Produto produto2 = new Produto("Sorvete");
            Produto produto3 = new Produto("Queijo");

            estoque.adicionarItem(produto1, 12);
            estoque.adicionarItem(produto2, 13);
            estoque.adicionarItem(produto3, 14);
        }, 0, INTERVALO, TimeUnit.SECONDS);
    }
}

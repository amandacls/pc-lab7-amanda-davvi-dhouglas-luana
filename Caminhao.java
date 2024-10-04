import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Caminhao {
    private Estoque estoque;
    private ScheduledExecutorService agendador;
    private static final int INTERVALO = 15;

    public Caminhao(Estoque estoque) {
        this.estoque = estoque;
        this.agendador = Executors.newScheduledThreadPool(1);
    }

    public void reabastecer() {
        agendador.scheduleAtFixedRate(() -> {
            Produto produto1 = new Produto("Macarrao", 720);
            Produto produto2 = new Produto("Sorvete", 170);
            Produto produto3 = new Produto("Queijo", 200);

            estoque.adicionarItem(produto1, 20);
            estoque.adicionarItem(produto2, 20);
            estoque.adicionarItem(produto3, 20);
        }, 0, INTERVALO, TimeUnit.SECONDS);
    }
}

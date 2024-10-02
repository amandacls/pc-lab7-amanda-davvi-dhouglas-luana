import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Estoque estoque = new Estoque();
        Item batata = new Item("batata", 90.00);
        Item cenoura = new Item("cenoura", 170.00);
        Item beterraba = new Item("beterraba", 250.00);

        String[] produtos = new String[] {"batata", "cenoura", "beterraba"};

        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        Pedido pedido3 = new Pedido();
        ArrayList<Item> set1 = new ArrayList<Item>(Arrays.asList(batata, batata, batata, batata, batata, cenoura,
                beterraba, beterraba));
        ArrayList<Item> set2 = new ArrayList<Item>(Arrays.asList(batata, batata, batata, cenoura, cenoura, cenoura,
                cenoura, cenoura, cenoura));
        ArrayList<Item> set3 = new ArrayList<Item>(Arrays.asList(beterraba, beterraba, beterraba, beterraba, beterraba,
                beterraba, beterraba, beterraba));

        pedido1.setItens(set1);
        pedido2.setItens(set2);
        pedido3.setItens(set3);

        estoque.iniciarAbastecimento(produtos);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Agenda o reabastecimento para rodar a cada 10 segundos
        scheduler.scheduleAtFixedRate(() -> {
            estoque.takeItem("batata", pedido1);
            estoque.takeItem("cenoura", pedido1);
            estoque.takeItem("beterraba", pedido1);
            estoque.takeItem("batata", pedido2);
            estoque.takeItem("cenoura", pedido2);
            estoque.takeItem("beterraba", pedido3);
        }, 0, 3, TimeUnit.SECONDS);



        estoque.iniciarReabastecimento(produtos);
    }
}

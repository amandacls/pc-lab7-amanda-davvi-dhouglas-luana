/* BlockingQueue<>
 * 
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Estoque {

    private ConcurrentHashMap<String, Integer> itens;

    public Estoque() {
        this.itens = new ConcurrentHashMap<String, Integer>();
    }

    public Integer findItemAmount(String nome) {
        return this.itens.get(nome);
    }

    public void addItem(String nome) {
        Integer amountInMap = this.itens.get(nome);
        if (amountInMap != null && amountInMap >= 0) {
            this.itens.put(nome, amountInMap + 1);
        } else {
            this.itens.put(nome, 1);
        }
    }

    public void addItem(String nome, Integer amount) {
        Integer amountInMap = this.itens.get(nome);
        if (amountInMap != null && amountInMap >= 0) {
            this.itens.put(nome, amountInMap + amount);
        } else {
            this.itens.put(nome, amount);
        }
    }

    public void iniciarAbastecimento(String[] produto) {
        for (String p: produto) {
            addItem(p, 10);
        }
        System.out.println("Estoque abastecido com 10 itens de " + produto.length + " produtos");
    }

    public void iniciarReabastecimento(String[] produto) throws InterruptedException {
        Thread.sleep(10000);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(produto.length);

        // Agenda o reabastecimento para rodar a cada 10 segundos
        scheduler.scheduleAtFixedRate(() -> {
            for (String p: produto) {
                addItem(p, 10);
            }
            System.out.println("Sistema reabastecido com 10 itens de " + produto.length + " produtos");
        }, 0, 10, TimeUnit.SECONDS);
    }
}
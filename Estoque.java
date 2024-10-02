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

    public void takeItem(String nome, Integer amount) {
        Integer amountInMap = this.itens.get(nome);
        if (amountInMap == null || amountInMap < amount) {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                Integer currentAmount = this.itens.get(nome);
                if (currentAmount != null && currentAmount > amount) {
                    this.itens.put(nome, (currentAmount - amount));
                    System.out.println("tirei " + amount + " depois da espera");
                    scheduler.shutdownNow();
                }
            }, 0, 5, TimeUnit.SECONDS);
        } else {
            this.itens.put(nome, (amountInMap - amount));
            System.out.println("tirei " + amount + " sem esperar");
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
            System.out.println(this.itens.values());
        }, 0, 10, TimeUnit.SECONDS);
    }
}
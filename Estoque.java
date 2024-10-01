/* BlockingQueue<>
 * 
 */

import java.util.concurrent.ConcurrentHashMap;

public class Estoque {

    private ConcurrentHashMap<String, Integer> itens;

    public Estoque() {
        this.itens = new ConcurrentHashMap<String, Integer>();
    }

    public Integer findItemAmount(String nome) {
        return this.itens.get(nome);
    }
}
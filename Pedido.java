/* BlockingQueue<>
 * - devem ser colocados em uma fila concorrente, 
 * onde as threads irão retirar os pedidos para processá-los.
 * - a fila deve suportar um número limitado de pedidos. 
 * Se a fila estiver cheia, novos pedidos devem ser bloqueados 
 * até que haja espaço.
 * - cada pedido deve conter uma lista de produtos e quantidades.
 * O sistema verifica a disponibilidade de todos os itens solicitados 
 * no estoque.
 * - Se os itens estiverem disponíveis serão descontados do estoque 
 * e o pedido concluído
 * - Se os itens não estiverem disponíveis o pedido deve ser rejeitado ou 
 * ficar pendente, indo para a lista de espera para abastecimento do estoque (Estoque.java)
 * 
 * Temos três estados de pedidos:
 * 1. Processado | 2. Rejeitado | 3. Em espera
 */

import java.util.ArrayList;
import java.util.Random;

public class Pedido {
    private final int id;

    private String state;

    private ArrayList<Item> itens;

    public Pedido() {
        this.state = "em-espera";
        this.id = new Random().nextInt(999999);
        this.itens = new ArrayList<Item>();
    }

    public String getState() {
        return state;
    }

    public void nextState(boolean accepted) {
        if(this.state.equals("em-espera") && accepted) {
            this.state = "processado";
        }
        if(accepted && this.state.equals("em-espera")) {
            this.state = "rejeitado";
        }
    }

    public int getId() {
        return id;
    }

    public ArrayList<Item> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Item> itens) {
        this.itens = itens;
    }

    public void addItem(Item item) {
        this.itens.add(item);
    }

    public void removeItem(Item item) {
        this.itens.remove(item);
    }

    public int count(Item item) {
        int count = 0;
        for (Item i: this.itens) {
            if (item.equals(i)) {
                count++;
            }
        }

        return count;
    }
}
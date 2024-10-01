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
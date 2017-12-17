/*
 *  Classe de item de um pedido
 *  @author Adson Macêdo
 */
package Classes;

import Interfaces.ListItem;

public class Item implements ListItem{
    Produto produto;
    int quant;
    int idPedido;

    public Item(Produto produto, int quant, int idPedido){
        this.produto = produto;
        this.quant = quant;
        this.idPedido = idPedido;
    }
    
    @Override
    public double getTotal() {
        return quant * produto.getPreco();
    }

    @Override
    public double getLucro(){
        return produto.getLucro() * quant;
    }
    
    @Override
    public int getId() {
        return idPedido;
    }

    //  Retorna uma linha com os dados do item
    @Override
    public String getItemLine(int [] colSizes) {
        String format = "%0" +  (colSizes[0] - 1) + "d";
        format += " %-" +       (colSizes[1] - 1) + "s";
        format += " %" +        (colSizes[2] - 1) + "d";
        format += " R$ %," +    (colSizes[3] - 4) + ".2f";
        format += " R$ %," +    (colSizes[4] - 3) + ".2f";
        
        return String.format(format, produto.getId(), produto.getDescricao(), quant, produto.getPreco(), getTotal());
    }
    
    
}

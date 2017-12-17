/*
 * @author Adson Macêdo
 */
package Classes;

import Interfaces.ListItem;
import Interfaces.Totais;

public class Pedido implements ListItem, Totais{
    private static int autoinc = 1;
    private static Tabela itens;
    
    private int id;
    
    public Pedido(){
        this.id = autoinc++;
    }

    @Override
    public double getTotal() {
        return itens.getTotal(id);
    }

    //  Seta a tabela de itens dos pedidos
    public static void setItens(Tabela itens) {
        Pedido.itens = itens;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public double getLucro(){
        double lucro = 0;
        
        for (ListItem li: itens.getLista()) {
            if (li.getId() == id){
                lucro += li.getLucro();
            }
        }
    
        return lucro;
    }
    
    //  Retorna uma linha com os dados do pedido
    @Override
    public String getItemLine(int [] colSizes) {
        String format = "%0" + (colSizes[0] - 1) + "d";
        format += " R$ %," +    (colSizes[1] - 3) + ".2f";
        format += " R$ %," +    (colSizes[1] - 4) + ".2f";
        
        return String.format(format, id, getLucro(), getTotal());
    }

    
}

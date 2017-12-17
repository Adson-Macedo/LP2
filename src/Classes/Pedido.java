/*
 * @author Adson Macêdo
 */
package Classes;

import Interfaces.ListItem;
import Interfaces.Totais;
import enums.Lines;
import java.util.ArrayList;
import utils.Msg;

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

    public static Tabela getItens() {
        return itens;
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
    
    //  Imprime um pedido já finalizado
    public void show(){
        ArrayList<ListItem> itensPedido = new ArrayList<>();
        
        for (ListItem it: itens.getLista()) {
            if (it.getId() == this.id) {
                itensPedido.add(it);
            }
        }
        
        show(itensPedido);
    }

    //  Imprime um pedido e seu total no final
    public void show(ArrayList<ListItem> itensPedido){
        //  armazeno a largura da tabela (pra não chamar muitas vezes o método)
        int len = itens.getTableWidth();    
        String header = String.format("%-" + len + "s", String.format("PEDIDO: %07d", getId()));
        
        Msg.cls();

        //  títulos da tabela e das colunas
        Msg.printFrame(header, Lines.TOP_CORNER, Lines.BOTTON_CORNER);      
        Msg.printFrame(itens.getColHeaders(), Lines.TOP_CORNER, Lines.MIDDLE_CORNER);   

        //  imprime a lista parcial de itens do pedido p
        if (itensPedido.isEmpty())
            Msg.printItem(Msg.toCenter("<<PEDIDO VAZIO>>", len));
        else 
            Msg.printLista(itensPedido, itens.COL_SIZES);
        
        Msg.printLine(Lines.BOTTON_CORNER, len);
        
        //  calcula o total parcial do pedido
        double total = 0;
        for (ListItem it: itensPedido) {
            total += it.getTotal();
        }
        
        String format = "%-" + (len - Pedido.getItens().getTotalColSize()) + "sR$ %" + (Pedido.getItens().getTotalColSize() - 3) + ".2f";
        Msg.printFrame(String.format(format, "TOTAL:", total), Lines.TOP_CORNER, Lines.BOTTON_CORNER);
    }

    
}

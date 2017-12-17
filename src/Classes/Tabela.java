/*
 * A classe tabela armazena elementos do tipo que implementa
 * a interface ListItem
 */
package Classes;

import Interfaces.ListItem;
import Interfaces.Totais;
import enums.Lines;
import exceptions.NotFoundException;
import java.util.ArrayList;
import utils.Msg;

/**
 *
 * @author Adson Macêdo
 */
public class Tabela implements Totais {
    private ArrayList<ListItem> lista = new ArrayList<>();
    private String header;
    private final String []COL_HEADERS;
    public final int []COL_SIZES;
    
    //  Construtor
    public Tabela(String header, String[] colHeaders, int[] colSizes){
        this.header = header;
        this.COL_HEADERS = colHeaders;
        this.COL_SIZES = colSizes;
    }
    
    //  Retorna a largura da tabela (soma dos tamanhos das colunas)
    public int getTableWidth(){
        int tam = 0;
        for (int i = 0; i < COL_SIZES.length; i++) {
            tam += COL_SIZES[i];
        }
        
        return tam;
    }

    //  Retorna o título da tabela centralizado, de acordo com a soma das larguras das colunas
    public String getHeader() {
        return header;
    }

    //  Retorna um array com os títulos das colunas
    public String getColHeaders() {
        String colHead = new String();
        
        for (int i = 0; i < COL_SIZES.length; i++) {
            //  formata o espaçamento de acordo com o tamanho da coluna i
            String format = "%-" + COL_SIZES[i] + "s";
            colHead = colHead + String.format(format, COL_HEADERS[i]);
        }

        return colHead;
    }

    //  Retona um arraylist com os itens da tabela
    public ArrayList<ListItem> getLista(){
        return lista;
    }
    
    //  Adiciona um item na tabela
    public void insertItem(ListItem item){
        lista.add(item);
    }
    
    //  Retorna um item da tabela através do ID
    public ListItem getItemById(int id) throws NotFoundException{
        for (ListItem li: lista) {
            if (li.getId() == id) return li;
        }
        
        
        throw new NotFoundException("ITEM NÃO ENCONTRADO");
    }
    
    //  Retorna o tamanho da coluna de totais
    public int getTotalColSize(){
        return COL_SIZES[COL_SIZES.length - 1];
    }

    //  Retorna o total da tabela
    @Override
    public double getTotal() {
        double total = 0;
        
        for (ListItem li: lista) {
            total += li.getTotal();
        }
        
        return total;
    }
    
    //  Retorna o total da tabela filtrada
    public double getTotal(int key){
        double total = 0;
        
        for (ListItem li: lista) {
            if (key == li.getId())
                total += li.getTotal();
        }
        
        return total;
    }
    
    //  Imprime a tabela
    public void show(boolean showTotal){
        Msg.cls();

        //  títulos da tabela e das colunas
        Msg.printFrame(Msg.toCenter(getHeader(), getTableWidth()), Lines.TOP_CORNER, Lines.MIDDLE_CORNER);   
        Msg.printFrame(getColHeaders(), null, Lines.MIDDLE_CORNER);                  

        //Lista de itens
        if (!Msg.printLista(getLista(), COL_SIZES))
            Msg.printItem(Msg.toCenter("<<LISTA VAZIA>>", getTableWidth()));

        Msg.printLine(Lines.BOTTON_CORNER, getTableWidth());
        
        //Imprime um frame com total
        if (showTotal){
            Msg.printTotalFrame(this, getTableWidth(), getTotalColSize());
        }

    }
   
}

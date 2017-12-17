/*
 * Classe de impressões gerais
 * @author Adson Macêdo
 */

package utils;

import Classes.*;
import Interfaces.ListItem;
import Interfaces.Totais;
import enums.Lines;
import java.io.IOException;
import java.util.ArrayList;


public abstract class Msg {
    //Retorna a string centralizada
    public static String toCenter(String s, int width){
        int len = (width - s.length())/2;   //espaçamento no início da string
        String format = "%-" + len + "s" + "%-" + (width - len) + "s";

        return String.format(format, "", s);
    }

    //Imprime uma linha com cantoneiras
    private static void printLine(Lines tipo, int tam){
        char c = Lines.LINE.getR();
        
        System.out.print(tipo.getL());      //imprime a cantoneira esquerda da linha
        while (tam-- > 0) 
            System.out.print(c);
        
        System.out.println(tipo.getR());    //imprime a cantoneira direita da linha
    }

    //Imprime um item de uma lista
    private static void printItem(String s){
        System.out.println(Lines.LINE.getL() + s + Lines.LINE.getL());
    }
    
    //Imprime uma lista de itens
    private static boolean printLista(ArrayList<ListItem> lista, int [] colSizes){
        if (lista.isEmpty()) return false;

        for (ListItem li: lista) {
            printItem(li.getItemLine(colSizes));
        }

        return true;
    }

    //Imprime uma lista de itens filtrada
    private static boolean printLista(ArrayList<ListItem> lista, int key, int [] colSizes){
        if (lista.isEmpty()) return false;
        
        for (ListItem li: lista) {
            if (key == li.getId())
                printItem(li.getItemLine(colSizes));
        }
        
        return true;
    }
    
    //  Imprime um frame com um texto
    public static void printFrame(String header, Lines topLine, Lines bottomLine){
        if (topLine != null) 
            printLine(topLine, header.length());

        printItem(header);

        if (bottomLine != null) 
            printLine(bottomLine, header.length());
    }
    
    //  Imprime um bloco com um total de uma tabela ou pedido (que implementa a classe Totais)
    private static void printTotalFrame(Totais t, int len, int totalColLen){
        String s = String.format("%-" + (len - totalColLen) + "sR$ %" + (totalColLen - 3) + ".2f", "TOTAL:", t.getTotal());
        printFrame(s, Lines.TOP_CORNER, Lines.BOTTON_CORNER);
    }
    
    //  Imprime uma tabela com/sem total no final
    public static void printTabela(Tabela t, boolean showTotal){
        //  títulos da tabela e das colunas
        printFrame(toCenter(t.getHeader(), t.getTableWidth()), Lines.TOP_CORNER, Lines.MIDDLE_CORNER);   
        printFrame(t.getColHeaders(), null, Lines.MIDDLE_CORNER);                  

        //Lista de itens
        if (!printLista(t.getLista(), t.COL_SIZES))
            printItem(toCenter("<<LISTA VAZIA>>", t.getTableWidth()));

        printLine(Lines.BOTTON_CORNER, t.getTableWidth());
        
        //Imprime um frame com total
        if (showTotal){
            printTotalFrame(t, t.getTableWidth(), t.getTotalColSize());
        }
    }
    
    //  Imprime um menu
    public static void printMenu(Menu m){
        //  formato para cada item do menu de acordo com a maior string do menu
        String format = "(%d) %-" + (m.getMaxLength()) + "s ";

        //  título do menu
        printFrame(m.getHeader(), Lines.TOP_CORNER, Lines.MIDDLE_CORNER);
        
        //  imprime os itens do menu
        for (int i = 0; i < m.getMenuItens().length; i++) {
            printItem(String.format(format, i + 1, m.getMenuItens()[i]));
        }

        //  opção padrão (0) sair
        printFrame(String.format(format, 0, "SAIR"), Lines.MIDDLE_CORNER, Lines.BOTTON_CORNER);
        System.out.print("DIGITE A OPÇÃO: ");
    }
    
    //  Imprime uma mensagem e pausa a execução
    public static void showMessage(String message){
        System.out.println(message);
        System.out.println("<ENTER> PARA CONTINUAR...");

        try {
            System.in.read();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //  Imprime um pedido e seu total no final
    public static void printPedido(Pedido p, Tabela itens){
        //  armazeno a largura da tabela (pra não chamar muitas vezes o método)
        int len = itens.getTableWidth();    
        String header = String.format("%-" + len + "s", String.format("PEDIDO: %07d", p.getId()));
        
        //  títulos da tabela e das colunas
        printFrame(header, Lines.TOP_CORNER, Lines.BOTTON_CORNER);      
        printFrame(itens.getColHeaders(), Lines.TOP_CORNER, Lines.MIDDLE_CORNER);   

        //  imprime a lista de itens do pedido p
        printLista(itens.getLista(), p.getId(), itens.COL_SIZES);
        
        printLine(Lines.BOTTON_CORNER, len);
        
        printTotalFrame(p, len, itens.getTotalColSize());
    }

    //  Imprime um pedido e seu total no final
    public static void printPedido(Pedido p, ArrayList<ListItem> itens){
        //  armazeno a largura da tabela (pra não chamar muitas vezes o método)
        int len = Pedido.getItens().getTableWidth();    
        String header = String.format("%-" + len + "s", String.format("PEDIDO: %07d", p.getId()));
        
        //  títulos da tabela e das colunas
        printFrame(header, Lines.TOP_CORNER, Lines.BOTTON_CORNER);      
        printFrame(Pedido.getItens().getColHeaders(), Lines.TOP_CORNER, Lines.MIDDLE_CORNER);   

        //  imprime a lista parcial de itens do pedido p
        if (itens.isEmpty())
            printItem(toCenter("<<PEDIDO VAZIO>>", len));
        else 
            printLista(itens, Pedido.getItens().COL_SIZES);
        
        printLine(Lines.BOTTON_CORNER, len);
        
        //  calcula o total parcial do pedido
        double total = 0;
        for (ListItem it: itens) {
            total += it.getTotal();
        }
        
        String format = "%-" + (len - Pedido.getItens().getTotalColSize()) + "sR$ %" + (Pedido.getItens().getTotalColSize() - 3) + ".2f";
        printFrame(String.format(format, "TOTAL PARCIAL:", total), Lines.TOP_CORNER, Lines.BOTTON_CORNER);
        
    }
    
}

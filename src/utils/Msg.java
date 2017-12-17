/*
 * Classe de impressões gerais
 * @author Adson Macêdo
 */

package utils;

import Classes.Menu;
import Interfaces.ListItem;
import Interfaces.Totais;
import enums.Lines;
import java.io.IOException;
import java.util.ArrayList;


public abstract class Msg {
    public static void cls(){
        for (int i = 0; i < 100; i++) {
            System.out.println("");
        }
    }

    //Retorna a string centralizada
    public static String toCenter(String s, int width){
        int len = (width - s.length())/2;   //espaçamento no início da string
        String format = "%-" + len + "s" + "%-" + (width - len) + "s";

        return String.format(format, "", s);
    }

    //Imprime uma linha com cantoneiras
    public static void printLine(Lines tipo, int tam){
        char c = Lines.LINE.getR();
        
        System.out.print(tipo.getL());      //imprime a cantoneira esquerda da linha
        while (tam-- > 0) 
            System.out.print(c);
        
        System.out.println(tipo.getR());    //imprime a cantoneira direita da linha
    }

    //Imprime um item de uma lista
    public static void printItem(String s){
        System.out.println(Lines.LINE.getL() + s + Lines.LINE.getL());
    }
    
    //Imprime uma lista de itens
    public static boolean printLista(ArrayList<ListItem> lista, int [] colSizes){
        if (lista.isEmpty()) return false;

        for (ListItem li: lista) {
            printItem(li.getItemLine(colSizes));
        }

        return true;
    }

    //Imprime uma lista de itens filtrada
    public static boolean printLista(ArrayList<ListItem> lista, int key, int [] colSizes){
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
    public static void printTotalFrame(Totais t, int len, int totalColLen){
        String s = String.format("%-" + (len - totalColLen) + "sR$ %" + (totalColLen - 3) + ".2f", "TOTAL:", t.getTotal());
        printFrame(s, Lines.TOP_CORNER, Lines.BOTTON_CORNER);
    }
    
    //  Imprime um menu
    public static void printMenu(Menu m, String defaultOption){
        //  formato para cada item do menu de acordo com a maior string do menu
        String format = "(%d) %-" + (m.getMaxLength()) + "s ";

        cls();
        //  título do menu
        printFrame(m.getHeader(), Lines.TOP_CORNER, Lines.MIDDLE_CORNER);
        
        //  imprime os itens do menu
        for (int i = 0; i < m.getMenuItens().length; i++) {
            printItem(String.format(format, i + 1, m.getMenuItens()[i]));
        }

        //  opção padrão (0) sair
        printFrame(String.format(format, 0, defaultOption), Lines.MIDDLE_CORNER, Lines.BOTTON_CORNER);
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
        
}

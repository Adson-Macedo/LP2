
package Classes;

import java.util.Scanner;
import utils.Msg;

public class Menu{
    private String header;
    private String[] menuItens;
    private int length;
    
    public Menu(String header, String[] itens){
        this.header = header;
        this.menuItens = itens;
        
        int len = header.length();
        
        for (String iten : itens) {
            if (iten.length() > len) {
                len = iten.length();
            }
        }
        
        this.length = len;
    }

    public String getHeader() {
        int space = (length - header.length())/2 + 3;
        String format = "%-" + space + "s" + "%-" + (length - space + 5) + "s";

        return String.format(format, "", header);
    }

    public int getMaxLength() {
        return length;
    }

    public String[] getMenuItens() {
        return menuItens;
    }
    
    public int getMenuOption(){
        int option;
        Scanner s = new Scanner(System.in);
        
        do{
            Msg.printMenu(this, "SAIR");
            option = s.nextInt();
        } while(option < 0 || option > menuItens.length);
        
        return option;
    }
    
}


package Classes;

import java.util.Scanner;
import utils.Msg;

public class Menu{
    private String header;      //  cabeçalho do menu
    private String[] menuItens; //  subitens do menu
    private int width;          //  largura do menu
    
    //  Construtor
    public Menu(String header, String[] itens){
        this.header = header;
        this.menuItens = itens;
        
        //  calcula a largura do menu
        int len = header.length();
        
        for (String iten : itens) {
            if (iten.length() > len) {
                len = iten.length();
            }
        }
        
        this.width = len;
    }

    //  retorna o cabeçalho centralizado
    public String getHeader() {
        return Msg.toCenter(header, width + 5);
    }

    // retorna a largura do menu
    public int getWidth() {
        return width;
    }

    public String[] getMenuItens() {
        return menuItens;
    }
    
    //  Mostra o menu e retorna a opção escolhida
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

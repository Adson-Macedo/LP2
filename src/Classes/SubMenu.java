/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Scanner;
import utils.Msg;

/**
 *
 * @author Adson Macêdo
 */
public class SubMenu extends Menu {
    
    public SubMenu(String header, String[] itens) {
        super(header, itens);
    }
    @Override
    public int getMenuOption(){
        int option;
        Scanner s = new Scanner(System.in);
        
        do{
            Msg.printMenu(this, "VOLTAR");
            option = s.nextInt();
        } while(option < 0 || option > this.getMenuItens().length);
        
        return option;
    }
    
}

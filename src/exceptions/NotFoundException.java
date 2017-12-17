/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Adson Macêdo
 * 
 * Exceção para quando não for encontrado um item
 */
public class NotFoundException extends Exception{

    public NotFoundException(String m) {
        super(m);
    }

}

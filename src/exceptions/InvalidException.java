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
 * Exceção para recursos insuficientes
 */
public class InvalidException extends Exception{

    public InvalidException(String message) {
        super(message);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author Adson Macêdo
 */
public interface ListItem {
    public abstract String getItemLine(int [] colSizes);
    public abstract double getTotal();
    public abstract int getId();
    public abstract double getLucro();
}

/*
*   Classe de Produto
*   @author Adson Macêdo
*/
package Classes;

import Interfaces.ListItem;
import exceptions.InvalidException;

public class Produto implements ListItem  {
    private static int autoinc = 1;

    private int id;
    private String descricao;
    private double preco;
    private double custo;
    private int estoque;

    public void setEstoque(int estoque) throws InvalidException{
        if (estoque < 0) 
            throw new InvalidException("ESTOQUE INVÁLIDO!");
        
        this.estoque = estoque;
    }

    public Produto(String descricao, double preco, double custo) throws InvalidException{
        this.id = autoinc++;
        if (descricao.length() == 0 || preco == 0 || custo == 0) {
            throw new InvalidException("O PRODUTO TEM QUE TER UMA DESCRIÇÃO E VALORES DE VENDA E CUSTO DIFERENTES DE 0!");
        }
        this.descricao = descricao;
        this.preco = preco;
        this.custo = custo;
        this.estoque = 0;
    }

    public double getCusto() {
        return custo;
    }

    @Override
    public double getTotal(){
        return estoque * preco;
    }

    @Override
    public double getLucro(){
        return preco - custo;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }
    
    //  Acrescenta quant ao estoque do produto
    public void addEstoque(int quant) throws InvalidException{
        if (quant == 0) 
            throw new InvalidException("QUANTIDADE INVÁLIDA!");
        
        this.estoque += quant;    
    }

    //  Retorna uma linha com os dados do produto
    @Override
    public String getItemLine(int [] colSizes) {
        String format = "%0" + (colSizes[0] - 1) + "d";
        format += " %-" +       (colSizes[1] - 1) + "s";
        format += " %" +        (colSizes[2] - 1) + "d";
        format += " R$ %," +    (colSizes[3] - 3) + ".2f";
        
        return String.format(format, id, descricao, estoque, preco);
    }
    
    //  Diminui o estoque do produto
    public void decrEstoque(int quant) throws InvalidException{
        if (quant <= 0 || getEstoque() < quant) 
            throw new InvalidException("QUANTIDADE INVÁLIDA OU ESTOQUE INSUFICIENTE");

        this.estoque -= quant;
    }
  
    
}

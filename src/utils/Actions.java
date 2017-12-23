/*
 * Ações executadas pelos menus
 * @author Adson Macêdo
 *   
 */
package utils;

import Classes.*;
import Interfaces.ListItem;
import enums.Lines;
import exceptions.EmptyException;
import exceptions.InvalidException;
import exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Actions {
    //  Verifica se o login é válido
    public static boolean login(Map<String, String> users){
        Scanner s = new Scanner(System.in);
        
        System.out.print("USUÁRIO: ");
        String user = s.next();
        System.out.print("SENHA:   ");
        String pass = s.next();

        //  retorne true se o usuário existe e sua senha corresponde
        return (users.containsKey(user) && users.get(user).equals(pass));
    }
    
    //  Inicializa uma venda
    public static void newVenda(Tabela produtos, Tabela pedidos, Tabela itens) throws EmptyException{
        Scanner s = new Scanner(System.in);
        Pedido pedido = new Pedido();
        ArrayList<ListItem> itensPedido = new ArrayList<>(); //  lista de itens a ser associada ao pedido criado
        
        Msg.showMessage("INICIANDO VENDA!");
        while(true){
            try{
                //  mostra a lista de produtos sem total
                produtos.show(false);
                
                System.out.println("ID DO PRODUTO (0 PARA FINALIZAR):");
                int id = s.nextInt();
                
                //  Flag para finalizar o pedido
                if (id == 0) {
                   pedido.show(itensPedido);
                    do{
                        System.out.println("(0) FINALIZAR   (1)CONTINUAR COMPRANDO");
                        id = s.nextInt();
                    }while(id < 0 || id > 1);
                    
                    if(id == 0)
                        break;
                    else
                        continue;
                }
                
                //  busca o produto na tabela pelo id
                Produto p = (Produto) produtos.getItemById(id);
                System.out.println("QUANTIDADE:");
                
                //  diminui o estoque do produto e adiciona na lista de itens
                int quant = s.nextInt();
                p.decrEstoque(quant);                       
                itensPedido.add(new Item(p, quant, pedido.getId()));    
                
                //  mostra a mensagem e pausa a execução
                Msg.showMessage("ITEM ADICIONADO COM SUCESSO!");
            } catch (Exception e){
                Msg.showMessage(e.getMessage());
            }
        }
       
        if (itensPedido.isEmpty())
            throw new EmptyException ("IMPOSSÍVEL CRIAR PEDIDO VAZIO!");
        
        //  insere o pedido criado na tabela
        pedidos.insertItem(pedido);
        
        //  insere os itens do pedido na tabela de itens
        for (ListItem it: itensPedido) 
            itens.insertItem(it);
        
        //  mostra o pedido criado com seus itens
//      pedido.show();
        Msg.showMessage("PEDIDO CRIADO COM SUCESSO!");
    }
    
    //  Mostra a lista de peididos com o total vendido e o rendimento
    public static void showRendimento(Tabela pedidos, Tabela compras, Tabela produtos){
        double totalLucro  = 0;
        double totalCompra = 0;

        //  soma os custos das compras
        for (ListItem p: compras.getLista())
            totalCompra += ((Item) p).getCusto();

        //  soma os lucros de todos os pedidos
        for (ListItem p: pedidos.getLista())
            totalLucro += p.getLucro();
        
        //  variáveis auxiliares para impressão do total do rendimento
        int len = 40;
        int colTotal = pedidos.getTotalColSize();
        String format = "%-" + (len - colTotal) + "sR$ %" + (colTotal - 3)+ ".2f";

        Msg.cls();
        //  imprime o total do rendimento e pausa o programa
        Msg.printLine(Lines.TOP_CORNER, len);
        Msg.printItem(Msg.toCenter("RENDIMENTOS DA EMPRESA", len));
        Msg.printLine(Lines.MIDDLE_CORNER, len);
        Msg.printItem(String.format(format, "TOTAL VENDIDO   : ", pedidos.getTotal()));
        Msg.printItem(String.format(format, "TOTAL (CUSTO)   :" , pedidos.getTotal() - totalLucro));
        Msg.printItem(String.format(format, "TOTAL DE LUCRO  :" , totalLucro));
        Msg.printItem(String.format("%-" + (len - colTotal) + "s %" + (colTotal - 2)+ ".2f%%", "% DE LUCRO      :" , pedidos.getTotal() > 0? 100*totalLucro/pedidos.getTotal(): 0));
        Msg.printLine(Lines.MIDDLE_CORNER, len);
        Msg.printItem(String.format(format, "VALOR EM ESTOQUE:" , produtos.getTotal()));
        Msg.printItem(String.format(format, "GASTO EM COMPRAS:" , totalCompra));
        Msg.printItem(String.format(format, "SALDO DO CAIXA  :" , pedidos.getTotal() - totalCompra));
        Msg.printLine(Lines.BOTTON_CORNER, len);

        Msg.showMessage("");
    }

    //  Seta o estoque do produto 
    public static void setEstoqueProduto(Tabela produtos) throws NotFoundException, InvalidException{
        Scanner s = new Scanner(System.in);

        //  imprime a tabela de produtos sem o total
        produtos.show(false);
        System.out.print("DIGITE O ID:  ");
        
        int id = s.nextInt();
        
        //  se mode = true, então adiciona ao estoque do produto
        System.out.print("NOVO ESTOQUE: ");
        ((Produto)produtos.getItemById(id)).setEstoque(s.nextInt());
        
        Msg.showMessage("ESTOQUE ATUALIZADO COM SUCESSO!");
    }

    //  Seta ou adiciona estoque ao produto (true-> adiciona; false-> remove)
    public static void setEstoqueProduto(Tabela produtos, Tabela compras, boolean mode) throws NotFoundException, InvalidException{
        Scanner s = new Scanner(System.in);

        //  imprime a tabela de produtos sem o total
        produtos.show(false);
        System.out.print("DIGITE O ID:  ");
        
        int id = s.nextInt();
        
        //  se mode = true, então adiciona ao estoque do produto, senão remove
        System.out.print("QUANTIDADE:   ");
        int quant = s.nextInt();
        Produto p = (Produto) produtos.getItemById(id);
        if (mode){
            p.addEstoque(quant);
            compras.insertItem(new Item(p, quant, 0));
        }else{
            p.decrEstoque(quant);
        }
        
        Msg.showMessage("ESTOQUE ATUALIZADO COM SUCESSO!");
        
    }
    
    //  Cria um novo produto
    public static void newProduto(Tabela produtos) throws InvalidException{
        Scanner s = new Scanner(System.in);
        
        System.out.println("INFORME OS DADOS DO PRODUTO");
        System.out.println("DESCRICAO:   ");
        String descricao = s.nextLine();
        
        System.out.println("PREÇO VENDA: ");
        double venda = s.nextFloat();
        
        System.out.println("PREÇO CUSTO: ");
        double custo = s.nextFloat();
        
        //  insere na tabela produtos um novo produto
        produtos.insertItem(new Produto(descricao, venda, custo));
        
        Msg.showMessage("PRODUTO CRIADO COM SUCESSO!");
    }
}

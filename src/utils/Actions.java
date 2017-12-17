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
                Msg.printTabela(produtos, false);
                System.out.println("ID DO PRODUTO (0 PARA FINALIZAR):");
                int id = s.nextInt();
                
                //  Flag para finalizar o pedido
                if (id == 0) {
                    Msg.printPedido(pedido, itensPedido);
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
        Msg.printPedido(pedido, itens);
        Msg.showMessage("PEDIDO CRIADO COM SUCESSO!");
    }
    
    //  Mostra a lista de peididos com o total vendido e o rendimento
    public static void showRendimento(Tabela pedidos){
        Msg.printTabela(pedidos, true);
        
        double lucro = 0;
        //  soma os lucros de todos os pedidos
        for (ListItem p: pedidos.getLista())
            lucro += p.getLucro();
        
        //  variáveis auxiliares para impressão do total do rendimento
        int len = pedidos.getTableWidth();
        int colTotal = pedidos.getTotalColSize();
        String format = "%-" + (len - colTotal) + "sR$ %" + (colTotal - 3)+ ".2f";
        
        //  imprime o total do rendimento e pausa o programa
        Msg.printFrame(String.format(format, "RENDIMENTO(LUCRO):", lucro), Lines.TOP_CORNER, Lines.BOTTON_CORNER);
        Msg.showMessage("");
    }

    //  Seta ou adiciona estoque ao produto (true-> adiciona; false-> seta)
    public static void setEstoqueProduto(Tabela produtos) throws NotFoundException, InvalidException{
        Scanner s = new Scanner(System.in);

        //  imprime a tabela de produtos sem o total
        Msg.printTabela(produtos, false);
        System.out.print("DIGITE O ID:  ");
        
        int id = s.nextInt();
        
        //  se mode = true, então adiciona ao estoque do produto
        System.out.print("NOVO ESTOQUE: ");
        ((Produto)produtos.getItemById(id)).setEstoque(s.nextInt());
    }

    //  Seta ou adiciona estoque ao produto (true-> adiciona; false-> remove)
    public static void setEstoqueProduto(Tabela produtos, boolean mode) throws NotFoundException, InvalidException{
        Scanner s = new Scanner(System.in);

        //  imprime a tabela de produtos sem o total
        Msg.printTabela(produtos, false);
        System.out.print("DIGITE O ID:  ");
        
        int id = s.nextInt();
        
        //  se mode = true, então adiciona ao estoque do produto, senão remove
        System.out.print("QUANTIDADE:   ");
        int quant = s.nextInt();
        if (mode){
            ((Produto)produtos.getItemById(id)).addEstoque(quant);
        }else{
            ((Produto)produtos.getItemById(id)).decrEstoque(quant);
        }
        
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

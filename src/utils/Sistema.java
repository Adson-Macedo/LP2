/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import Classes.*;
import exceptions.EmptyException;
import exceptions.InvalidException;
import exceptions.NotFoundException;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Adson Macêdo
 */
public abstract class Sistema {
    private static Map<String, String> users = new TreeMap<>();
    private static Tabela pedidos, produtos, itens;
    private static Menu   mainMenu, admMenu, estoqueMenu;

    //Testes de cadastros
    public static void testes() {
        for (int i = 0; i < 10; i++) 
            try {
                produtos.insertItem(new Produto("TESTE " + i, 5, 3));
            } catch (InvalidException ex) {
                
            }
        for (int i = 1; i < 10; i++) 
            try {
                 ((Produto) produtos.getItemById(i)).addEstoque(10);
            } catch (NotFoundException | InvalidException ex) {
                Msg.showMessage(ex.getMessage());
            }
        
        for (int i = 0; i < 10; i++) pedidos.insertItem(new Pedido());
        

        for (int i = 2; i < 5; i++) {
            try {
                itens.insertItem(new Item((Produto) produtos.getItemById(i), 5, 1));
            } catch (NotFoundException ex) {
                Msg.showMessage(ex.getMessage());
            }
        }
        
        for (int i = 1; i < 3; i++) {
            try {
                itens.insertItem(new Item((Produto) produtos.getItemById(i), 5, 2));
            } catch (NotFoundException ex) {
                Msg.showMessage(ex.getMessage());
            }
        }
        
        
//        Msg.showPedido((Pedido)pedidos.getItemById(2), itens);
//        Msg.printTabela(pedidos, true);
        
    }
    
    // Configurações iniciais do sistema
    public static void setup(){
        users.put("ADMIN", "admin");
        users.put("Adson", "12345");

        //  cria os menus
        mainMenu = new Menu("MENU PRINCIPAL", 
                new String[]{"LISTAR PRODUTOS E INICIAR VENDA", "ADMINISTRAR ESTOQUE"});
        admMenu  = new Menu("***ADMINISTRAR ESTOQUE***", 
                new String[]{"ORGANIZAR ESTOQUE", "VER RENDIMENTO"});
        estoqueMenu  = new Menu("ORGANIZAR ESTOQUE", 
                new String[]{"CADASTRAR PRODUTO","ADICIONAR AO ESTOQUE","REMOVER DO ESTOQUE", "EDITAR ESTOQUE"});

        //  cria as tabelas
        produtos = new Tabela("LISTA DE PRODUTOS", 
                new String[]{"ID", "DESCRIÇÃO", "ESTOQUE", "PREÇO"}, new int[]{11, 38, 9, 13});
        pedidos  = new Tabela("PEDIDOS",  
                new String[]{"ID", "VALOR LUCRO", "VALOR TOTAL"},  new int[]{8, 13, 13});
        itens    = new Tabela("ITENS DO PEDIDO",  
                new String[]{"ID", "DESCRIÇÃO", "QUANT", "PREÇO", "TOTAL"},  new int[]{8, 25, 7, 13, 13});

        Pedido.setItens(itens);
    }
    
    //  Inicializa o sistema, chamando o menu principal
    public static void initialize(){
        int o;
        
        do{
            o = mainMenu.getMenuOption();
            switch (o){
                //  Listar produtos e iniciar venda
                case 1: try {
                            Actions.newVenda(produtos, pedidos, itens);
                        } catch (EmptyException e) {
                            Msg.showMessage(e.getMessage());
                        }
            
                        break;
                    
                //  Menu administrar
                case 2: if (Actions.login(users))
                            admSistema();
                        else
                            Msg.showMessage("ACESSO NEGADO!");
            }
        
        } while (o != 0);
        
    }

    private static void orgEstoque(){
        int o;
        do {
            o = estoqueMenu.getMenuOption();
            switch (o){
                //  CADASTRAR PRODUTO
                case 1: try {
                            Actions.newProduto(produtos);
                        } catch (InvalidException ex) {
                            Msg.showMessage(ex.getMessage());
                        }
                        
                        break;

                //  ADICIONAR ESTOQUE
                case 2: try {
                            Actions.setEstoqueProduto(produtos, true);
                        } catch (NotFoundException | InvalidException ex) {
                            Msg.showMessage(ex.getMessage());
                        }
                    
                        break;

                //  REMOVER ESTOQUE
                case 3: try {
                            Actions.setEstoqueProduto(produtos, false);
                        } catch (NotFoundException | InvalidException ex) {
                            Msg.showMessage(ex.getMessage());
                        }
                    
                        break;

                //  EDITAR ESTOQUE
                case 4: try {
                            Actions.setEstoqueProduto(produtos);
                        } catch (NotFoundException | InvalidException ex) {
                            Msg.showMessage(ex.getMessage());
                        }
                    
                        break;

            }  
        } while (o != 0);
    }
    private static void admSistema() {
        int o;
        do {
            o = admMenu.getMenuOption();
            switch (o){
                    //  ORGANIZAR ESTOQUE
                case 1: orgEstoque();
                        break;

                //  VER RENDIMENTO
                case 2: Actions.showRendimento(pedidos);
                        break;
            }  
        } while (o != 0);
    
    }
}

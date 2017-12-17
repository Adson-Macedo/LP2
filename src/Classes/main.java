package Classes;

/*
*   Classe principal que apenas prepara a classe sistema
*   e inicializa (chamando o menu principal)
*   @author Adson Macêdo
*/
import utils.Sistema;

public class main {

    public static void main(String[] args) {
        Sistema.setup();
        Sistema.testes();
        Sistema.initialize();
    }
}

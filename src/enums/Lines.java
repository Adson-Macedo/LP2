package enums;

/**
 *
 * @author Adson Macêdo
 * 
 * Tipos de cantos das linhas
 */
public enum Lines {
    TOP_CORNER   ('╔', '╗'),    //cantoneiras de topo
    MIDDLE_CORNER('╠', '╣'),    //cantoneiras de meio
    BOTTON_CORNER('╚', '╝'),    //cantoneiras de baixo
    LINE         ('║', '=');    //caracter de linha e lateral
    
    private final  char l;  
    private final  char r;

    public char getL() {
        return l;
    }

    public char getR() {
        return r;
    }

    private Lines(char l, char r) {
        this.l = l;
        this.r = r;
    }
    
}

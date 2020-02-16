
package olc1.practica1;


public class Token {
    public int Contador;
    public String Lexema;
    public String Descripcion;

    public Token(int Contador, String Lexema, String Descripcion) {
        this.Contador = Contador;
        this.Lexema = Lexema;
        this.Descripcion = Descripcion;
    }

    public int getContador() {
        return Contador;
    }

    public void setContador(int Contador) {
        this.Contador = Contador;
    }

    public String getLexema() {
        return Lexema;
    }

    public void setLexema(String Lexema) {
        this.Lexema = Lexema;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    
}

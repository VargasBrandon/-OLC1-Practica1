
package olc1.practica1;


public class Error {
    public int Contador;
    public String Caracter;
    public String Descripcion;

    public Error(int Contador, String Caracter, String Descripcion) {
        this.Contador = Contador;
        this.Caracter = Caracter;
        this.Descripcion = Descripcion;
    }

    public int getContador() {
        return Contador;
    }

    public void setContador(int Contador) {
        this.Contador = Contador;
    }

    public String getCaracter() {
        return Caracter;
    }

    public void setCaracter(String Caracter) {
        this.Caracter = Caracter;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }



    

}

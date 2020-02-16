/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc1.practica1;

/**
 *
 * @author HP
 */
public class Lexema {
    public String Nombre;
    public String Lexema;

    public Lexema(String Nombre, String Lexema) {
        this.Nombre = Nombre;
        this.Lexema = Lexema;
    }   
    
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getLexema() {
        return Lexema;
    }

    public void setLexema(String Lexema) {
        this.Lexema = Lexema;
    }
    
    
}

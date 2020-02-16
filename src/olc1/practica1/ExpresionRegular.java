/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc1.practica1;
import java.util.ArrayList;
/**
 *
 * @author HP
 */
public class ExpresionRegular {
    public String Nombre;
    public String Expresion;
    public String hoja;

    public ExpresionRegular(String Nombre, String Expresion) {
        this.Nombre = Nombre;
        this.Expresion = Expresion;
    }
       

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getExpresion() {
        return Expresion;
    }

    public void setExpresion(String Expresion) {
        this.Expresion = Expresion;
    }

    public String getHoja() {
        return hoja;
    }

    public void setHoja(String hoja) {
        this.hoja = hoja;
    }

    
    
    
    
}

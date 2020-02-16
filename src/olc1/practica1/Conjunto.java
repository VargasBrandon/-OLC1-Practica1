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
public class Conjunto {
    public String Nombre;
    public String Conjuntos;

    public Conjunto(String Nombre, String Conjuntos) {
        this.Nombre = Nombre;
        this.Conjuntos = Conjuntos;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getConjuntos() {
        return Conjuntos;
    }

    public void setConjuntos(String Conjuntos) {
        this.Conjuntos = Conjuntos;
    }
    
    
}

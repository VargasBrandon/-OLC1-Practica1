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
public class Arbol {
    public String Expresion;
    public String nombre;
    public int identificador;
    public String anulable;
    public String primero;
    public String ultimo;
    public int padre;
    public int hijo1;
    public int hijo2;
    public String tipo;
    public String estado;
    public String siguientes;


    public Arbol(String Expresion, String nombre, int identificador, String anulable, String primero, String ultimo, int padre, String tipo, String estado) {
        this.Expresion = Expresion;
        this.nombre = nombre;
        this.identificador = identificador;
        this.anulable = anulable;
        this.primero = primero;
        this.ultimo = ultimo;
        this.padre = padre;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getExpresion() {
        return Expresion;
    }

    public void setExpresion(String Expresion) {
        this.Expresion = Expresion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getAnulable() {
        return anulable;
    }

    public void setAnulable(String anulable) {
        this.anulable = anulable;
    }

    public String getPrimero() {
        return primero;
    }

    public void setPrimero(String primero) {
        this.primero = primero;
    }

    public String getUltimo() {
        return ultimo;
    }

    public void setUltimo(String ultimo) {
        this.ultimo = ultimo;
    }

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }

    public int getHijo1() {
        return hijo1;
    }

    public void setHijo1(int hijo1) {
        this.hijo1 = hijo1;
    }

    public int getHijo2() {
        return hijo2;
    }

    public void setHijo2(int hijo2) {
        this.hijo2 = hijo2;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSiguientes() {
        return siguientes;
    }

    public void setSiguientes(String siguientes) {
        this.siguientes = siguientes;
    }

    
    
    
    
    
}

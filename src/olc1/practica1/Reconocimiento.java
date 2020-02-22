
package olc1.practica1;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;

public class Reconocimiento {
    public ArrayList<Conjunto> Conjuntos = new ArrayList<Conjunto>();
    public ArrayList<ExpresionRegular> Expresiones = new ArrayList<ExpresionRegular>();
    public ArrayList<Lexema> Lexemas = new ArrayList<Lexema>();
    public ArrayList<Arbol> Arboles = new ArrayList<Arbol>();
    
    
    int estado;
    String palabra;
    String tipo;
    String nombre = "";
    String completo= "";
    public void Definir(ArrayList<Token> token){
        for (int i = 0; i < token.size(); i++) {
            palabra = token.get(i).getLexema();
            tipo = token.get(i).getDescripcion();
            String verificar = palabra.toLowerCase();
            switch (estado)
            {
                case 0:
                    if(verificar.equals("conj")){
                        estado = 1;
                    }else if(tipo.equals("identificador")){
                        nombre = palabra;
                        estado = 3;
                    }else if(verificar.equals("%%")){
                        estado = 5;
                    }else{
                        estado = 0;
                    }
                    break;
                case 1:
                    if(tipo.equals("identificador")){
                        nombre = palabra;
                        estado = 1;
                    }else if(verificar.equals("->")){
                        estado = 2;
                    }else{
                        estado =1;
                    }
                    break;
                case 2:
                    if(verificar.equals(";")){
                        Conjuntos.add(new Conjunto(nombre,completo));
                        completo = "";
                        nombre = "";
                        estado = 0;
                    }else{
                        completo += palabra;
                    }
                    break;
                case 3:
                    if(verificar.equals("->") || verificar.equals(":") ){
                        estado = 4;
                    }else{
                        estado = 3;
                    }
                    break;
                case 4:
                    if(verificar.equals(";")){
                        Expresiones.add(new ExpresionRegular(nombre,completo));
                        completo = "";
                        nombre = "";
                        estado = 0;
                    }else{
                        completo += palabra;
                    }
                    break;
                case 5:
                    if(tipo.equals("identificador")){
                        nombre = palabra;
                        estado = 5;
                    }else if(verificar.equals("->") || verificar.equals(":")){
                        estado = 6;
                    }else{
                        estado = 5;
                    }
                    break;
                case 6:
                    if(tipo.equals("Lexema")){
                        Lexemas.add(new Lexema(nombre,palabra));
                        completo = "";
                        nombre = "";
                        estado = 5;
                    }else{
                        estado = 6;
                    }
                    break;          
            }           
        }
        
        for (ExpresionRegular p : Expresiones) {
            if("Expresion2".equals(p.getNombre())){
                p.setHoja("soy un hoja");
            }
        }

        /*
        //recorrerconjunto
        System.out.println("\n---Lista Conjuntos---");
        for (int i = 0; i < Conjuntos.size(); i++) {           
            System.out.print("conjuntos->"+Conjuntos.get(i).getNombre() + "->" +"("+ Conjuntos.get(i).getConjuntos()+")"+"\n");
        }
System.out.println("\n---Lista Lexemas---");
        for (int i = 0; i < Lexemas.size(); i++) {           
           System.out.print("lexemas->"+Lexemas.get(i).getNombre() + "->" +"("+ Lexemas.get(i).getLexema()+")"+"\n");
        } 
        System.out.println("\n---Lista Expresiones---");
        for (int i = 0; i < Expresiones.size(); i++) {           
            System.out.print("expresiones->"+Expresiones.get(i).getNombre() + "->" +"("
                    + Expresiones.get(i).getExpresion()+")"+"---->arbol: "+Expresiones.get(i).getHoja()+"\n");
        } */
         
        ArbolDeExpresion();
    }      
    

    String ExpresionRecorrer;
    String ExpresionActual;
    int ContExpresionActual;
    int ContSimbolosExpresion;
    public void ArbolDeExpresion(){        
        for (int i = 0; i < Expresiones.size() ; i++) {
            ExpresionRecorrer = Expresiones.get(i).getExpresion();
            ExpresionActual = Expresiones.get(i).getNombre();
            System.out.println("expreision :"+ ExpresionRecorrer +"\n");
            RecorrerExpresion(ExpresionRecorrer);
            IncioRecorrerArbol(Arboles);
            Grafica();
            GraficaSiguientes();
            ContExpresionActual = 0;               
          }
       
             System.out.println("\n");
            for (int j = 0; j < Arboles.size(); j++) {           
            System.out.print("---"+Arboles.get(j).getExpresion() + "->" +"nombre :" +" ( " + Arboles.get(j).getNombre()+") "
                            +" nombre :"+ Arboles.get(j).getNombre()
                            +" siguien :" + Arboles.get(j).getSiguientes()
                            +"\n");
        } 
                            
    }   
    int contPadre = 1;
    public void RecorrerExpresion(String cadena){
        ContSimbolosExpresion = 0;
        int estadoInicio = 0;
        int estadoPrincipal = 0;        
        char cadenaConcatenar;
        String expresion = "";
        int cont= 1;
        
        for (estadoInicio = 0; estadoInicio < cadena.length(); estadoInicio++)
        {
            cadenaConcatenar = cadena.charAt(estadoInicio);            
            switch (estadoPrincipal)
            {
                case 0:
                    if(cadenaConcatenar =='"'){
                        estadoPrincipal = 2; 
                    }else if(cadenaConcatenar == '{'){
                        estadoPrincipal = 3;
                    }
                    else if(cadenaConcatenar == '*' || cadenaConcatenar == '?' ){
                        expresion += cadenaConcatenar;
                        estadoInicio-=1;
                        estadoPrincipal = 4;
                    }else{
                        expresion += cadenaConcatenar;
                        estadoInicio -= 1;
                        estadoPrincipal = 1; 
                    }
                    break;
                case 1:
                    Arboles.add(new Arbol(ExpresionActual,expresion,0,"","0","0",contPadre,"simbolo","falso"));
                    ContExpresionActual++;
                    contPadre++ ;
                    ContSimbolosExpresion++;
                    expresion = "";
                    estadoPrincipal = 0;
                    break;
                case 2:
                    if(cadenaConcatenar == '"'){
                        Arboles.add(new Arbol(ExpresionActual,expresion,cont,"N",String.valueOf(cont),String.valueOf(cont),contPadre,"cadena","falso"));
                        cont++;
                        ContExpresionActual++;
                        contPadre++;
                        expresion = "";
                        estadoPrincipal = 0;
                    }else{
                        expresion+=cadenaConcatenar;
                        estadoPrincipal = 2;
                    }
                    break;
                case 3:
                    if(cadenaConcatenar == '}'){
                        Arboles.add(new Arbol(ExpresionActual,expresion,cont,"N",String.valueOf(cont),String.valueOf(cont),contPadre,"cadena","falso"));
                        cont++;
                        ContExpresionActual++;
                        contPadre++;
                        expresion = "";
                        estadoPrincipal = 0;
                    }else{
                        expresion+=cadenaConcatenar;
                        estadoPrincipal = 3;
                    }
                    break;    
                case 4:
                    Arboles.add(new Arbol(ExpresionActual,expresion,0,"A","0","0",contPadre,"simbolo","falso"));
                    ContExpresionActual++;
                    contPadre++ ;
                    ContSimbolosExpresion++;
                    expresion = "";
                    estadoPrincipal = 0;
                    break;
                    
            }
        }
    }
    
    int contArbol;
    String TemporalHoja;
    String TemporalExpresion;
    int estadoArbol;
    public void IncioRecorrerArbol(ArrayList<Arbol> token){
        int estadoInicio;
        int estadoPrincipal = 0;
        int cantidadCadenas = 0;
        int datosrecorridos = 0;
        
        for (estadoInicio =token.size()-ContExpresionActual; estadoInicio < token.size(); estadoInicio++) {
            
            String pArbol = token.get(estadoInicio).getNombre();
            String tipoA = token.get(estadoInicio).getTipo();
            String estadoA = token.get(estadoInicio).getEstado();
            String verificar = pArbol.toLowerCase();           
            switch (estadoPrincipal)
            {
                case 0:
                    if(tipoA.equals("simbolo") && estadoA.equals("falso")){
                        if(verificar.equals(".")){
                            datosrecorridos=0;
                            datosrecorridos++;                         
                            estadoPrincipal = 1;
                        }else if(verificar.equals("|")){
                            datosrecorridos=0;
                            datosrecorridos++;                           
                            estadoPrincipal = 1;
                        }else if(verificar.equals("*") || verificar.equals("+") || verificar.equals("?") ){
                            datosrecorridos=0;
                            datosrecorridos++;                           
                            estadoPrincipal = 3;
                        }else{
                        
                        }
                    }else if(tipoA.equals("cadena") && estadoA.equals("falso")){
                        if(cantidadCadenas >= 1){
                         //   datosrecorridos++;
                            estadoInicio--;
                            estadoPrincipal =2;
                        }else{
                            System.out.println("no entro cadena mano");
                        }                       
                        
                    }
                    else{
                        datosrecorridos++;
                        estadoPrincipal=0;
                    }
                    break;
                case 1:
                    if(tipoA.equals("cadena")&& estadoA.equals("falso")){
                        datosrecorridos++;
                        cantidadCadenas++;
                        estadoPrincipal =2;
                    }else{
                        estadoInicio--;
                        estadoPrincipal = 0; 
                    }                       
                    break;
                case 2:
                    if(tipoA.equals("cadena")&& estadoA.equals("falso")){
                        int datopadre = 0 ;
                        datopadre = estadoInicio - datosrecorridos;
                        int posH1= datopadre+1;
                        int posH2= estadoInicio;
                        token.get(datopadre).setHijo1(token.get(posH1).getPadre());
                        token.get(datopadre).setHijo2(token.get(posH2).getPadre());
                        token.get(datopadre).setTipo("cadena");                       
                        token.get(estadoInicio).setEstado("verdadero");
                        token.get(datopadre+1).setEstado("verdadero");
                //ANULABLES        
                        if(token.get(datopadre).getNombre().equals(".")){
                            if(token.get(posH1).getAnulable().equals("A") && token.get(posH2).getAnulable().equals("A")){
                                token.get(datopadre).setAnulable("A");
                            }else{
                                token.get(datopadre).setAnulable("N");
                            }
                        }else{
                            if(token.get(posH1).getAnulable().equals("A") || token.get(posH2).getAnulable().equals("A")){
                                token.get(datopadre).setAnulable("A");
                            }else{
                                token.get(datopadre).setAnulable("N");
                            }
                        }  
                  //Primeros      
                        if(token.get(datopadre).getNombre().equals(".")){
                            if(token.get(posH1).getAnulable().equals("N")){
                                token.get(datopadre).setPrimero(token.get(posH1).getPrimero());
                            }else if(token.get(posH1).getAnulable().equals("A")){
                                token.get(datopadre).setPrimero(token.get(posH1).getPrimero()+","+token.get(posH2).getPrimero());;
                            }
                        }else{
                            token.get(datopadre).setPrimero(token.get(posH1).getPrimero()+","+token.get(posH2).getPrimero());
                        } 
                  //Ultimos     
                        if(token.get(datopadre).getNombre().equals(".")){
                            if(token.get(posH2).getAnulable().equals("N")){
                                token.get(datopadre).setUltimo(token.get(posH2).getUltimo());
                            }else if(token.get(posH2).getAnulable().equals("A")){
                                token.get(datopadre).setUltimo(token.get(posH1).getUltimo()+","+token.get(posH2).getUltimo());
                            }
                        }else{
                            token.get(datopadre).setUltimo(token.get(posH1).getUltimo()+","+token.get(posH2).getUltimo());
                        }
                    //dar siguientes  
                        if(token.get(datopadre).getNombre().equals(".")){
                            String strMain = token.get(posH1).ultimo;   
                            String[] arrSplit = strMain.split(","); 
                            for (int i = 0; i < arrSplit.length; i++) {
                                for (int j =token.size()-ContExpresionActual; j < token.size(); j++) {
                                    if(token.get(j).getIdentificador()==Integer.parseInt(arrSplit[i])){
                                        token.get(j).setSiguientes(token.get(posH2).getPrimero());
                                    }
                                }
                            }
                        }
                        
                        cantidadCadenas = 0;
                        estadoInicio = token.size()-ContExpresionActual -1;
                        estadoPrincipal =0;                                             
                    }else{
                        estadoInicio--;
                        estadoPrincipal = 0;                       
                    } 
                    break;
                case 3:
                   if(tipoA.equals("cadena")&& estadoA.equals("falso")){
                        int datopadre = 0 ;
                        datopadre = estadoInicio -1;
                        int posH1= datopadre+1;
                        token.get(datopadre).setHijo1(token.get(posH1).getPadre());
                        token.get(datopadre).setTipo("cadena");                       
                        token.get(datopadre+1).setEstado("verdadero");
                        
                 //ANULABLES       
                        if(token.get(datopadre).getNombre().equals("+")){
                            if(token.get(posH1).getAnulable().equals("N")){
                                token.get(datopadre).setAnulable("N");
                            }else{
                                token.get(datopadre).setAnulable("A");
                            }
                        }
                  //Primeros y Ultimos
                        token.get(datopadre).setPrimero(token.get(posH1).getPrimero());
                        token.get(datopadre).setUltimo(token.get(posH1).getUltimo());
                        
                        
                  //siguientes      
                        if(token.get(datopadre).getNombre().equals("*") || token.get(datopadre).getNombre().equals("+")){
                            String strMain = token.get(posH1).ultimo;   
                            String[] arrSplit = strMain.split(","); 
                            for (int i = 0; i < arrSplit.length; i++) {
                                for (int j =token.size()-ContExpresionActual; j < token.size(); j++) {
                                    if(token.get(j).getIdentificador()==Integer.parseInt(arrSplit[i])){
                                        token.get(j).setSiguientes(token.get(posH1).getPrimero());
                                    }
                                }
                            }
                        }
                        
                        
                        cantidadCadenas = 0;
                        estadoInicio = token.size()-ContExpresionActual -1;
                        estadoPrincipal =0;                                             
                    }else{
                        estadoInicio--;
                        estadoPrincipal = 0;                       
                    } 
                    break;
            }
        }
   /*     System.out.println("\n");
        System.out.println("\n contador acutal de datos ->" + (Arboles.size()-ContExpresionActual));
        for (int j = Arboles.size()-ContExpresionActual; j < Arboles.size(); j++) {           
            System.out.print(Arboles.get(j).getExpresion() + "->" +" nombre:" +" (" + Arboles.get(j).getNombre()+")" +"-"
                            +" estado:"+ Arboles.get(j).getEstado()+"-"
                            +" tipo:" + Arboles.get(j).getTipo() +"-"
                            +" padre:"+ Arboles.get(j).getPadre()+"-"
                            +" hijo1:"+ Arboles.get(j).getHijo1() +"-"
                            +" hijo2:"+ Arboles.get(j).getHijo2()+"\n");
        }  */
        
    }
    
    
    public void Grafica(){
        FileWriter fichero =null;
        PrintWriter archivo = null;
        String nombre =ExpresionActual ;
        String nombreDot = nombre + ".dot";
        try{
            fichero  = new FileWriter(nombreDot);
            archivo = new PrintWriter(fichero);
            archivo.println("digraph structs { \n node [shape=record]; \n");
            for (int i = Arboles.size()-ContExpresionActual; i < Arboles.size(); i++) {
                String anulable =  Arboles.get(i).getAnulable();
                String identificadores = Arboles.get(i).getIdentificador()+"";
                String primeros = Arboles.get(i).getPrimero();
                String ultimos = Arboles.get(i).getUltimo();
                String nombreA = Arboles.get(i).getNombre();
                archivo.print(Arboles.get(i).getPadre()+"[label="+ "\"{"+anulable+"|{"+primeros+"|"+"<here>"+nombreA+"|"+ultimos+"}|"+identificadores+"}"+"\""+" ];\n");
            }            
            for (int i = Arboles.size()-ContExpresionActual; i < Arboles.size(); i++) {
                if(Arboles.get(i).getHijo1()>0){
                    archivo.print(Arboles.get(i).getPadre()+"->"+Arboles.get(i).getHijo1()+";\n");
                }
                if(Arboles.get(i).getHijo2()>0){
                    archivo.print(Arboles.get(i).getPadre()+"->"+Arboles.get(i).getHijo2()+";\n");
                } 
            }           
            archivo.print("\n}");
            fichero.close();
        }catch(Exception e){
            System.out.println(e);
        }       
        try{
            String cmd = "dot.exe -Tpng "+nombre+".dot -o" +nombre+".png";
            Runtime.getRuntime().exec(cmd);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }    
    
    public void GraficaSiguientes(){
        FileWriter fichero =null;
        PrintWriter archivo = null;
        String nombre =ExpresionActual ;
        String nombreDot = "siguientes"+nombre + ".dot";
        try{
            fichero  = new FileWriter(nombreDot);
            archivo = new PrintWriter(fichero);
            archivo.println("digraph structs {\n");
            archivo.println(" graph [pad=\"0.5\", nodesep=\"0.5\", ranksep=\"2\"];\n");
            archivo.println("node [shape=plain]; \n rankdir=LR;\n\n\n");
            archivo.println("Foo [label=<\n" + "<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">\n");
            archivo.println("<tr><td><i>Enum</i></td><td><i>Etiqueta</i></td><td><i>siguientes</i></td></tr>\n");
            for (int i = Arboles.size()-ContExpresionActual; i < Arboles.size(); i++) {
                if(Arboles.get(i).getIdentificador()>0){
                    archivo.println("<tr><td>"+Arboles.get(i).getIdentificador()+"</td><td>"+Arboles.get(i).getNombre()+"</td><td>"+Arboles.get(i).getSiguientes()+"</td></tr>");
                }
              
            }               
            archivo.print("</table>>];");
            archivo.print("\n}");
            fichero.close();
        }catch(Exception e){
            System.out.println(e);
        }       
        try{
            String cmd = "dot.exe -Tpng "+"siguientes"+nombre+".dot -o"+"siguientes"+nombre+".png";
            Runtime.getRuntime().exec(cmd);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }  
    
    
    
    
    public void Punto(){
        System.out.println("este punto");
        Disyuncion();
        System.out.println("fin 1");
    }
    public void Disyuncion(){
        System.out.println("este |");
        Asterisco();
        System.out.println("fin 2");
    }
    public void Asterisco(){
        System.out.println("este *");
        Mas();
        Pregunta();
        System.out.println("fin 3");
    }
    public void Mas(){
        Asterisco();
        System.out.println("este +");
    }
    public void Pregunta(){
        System.out.println("este ?");
        System.out.println("fin 4");
    }
}


package olc1.practica1;
import java.util.ArrayList;


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
    public void ArbolDeExpresion(){        
      //  for (int i = 0; i < Expresiones.size() ; i++) {
            ExpresionRecorrer = Expresiones.get(0).getExpresion();
            ExpresionActual = Expresiones.get(0).getNombre();
            System.out.println("expreision :"+ ExpresionRecorrer +"\n");
        RecorrerExpresion(ExpresionRecorrer);
   //     ContExpresionActual = 0;
        
  //      }
        for (int j = 0; j < Arboles.size(); j++) {           
            System.out.print(ContExpresionActual+")"+"->"+Arboles.get(j).getExpresion() + "->" + Arboles.get(j).getNombre()+"\n");
        }
        
    }   
    
    public void RecorrerExpresion(String cadena){
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
                        estadoPrincipal = 4;
                    }else{
                        expresion += cadenaConcatenar;
                        estadoInicio -= 1;
                        estadoPrincipal = 1; 
                    }
                    break;
                case 1:
                    Arboles.add(new Arbol(ExpresionActual,expresion,0,"","0","0","simbolo"));
                    ContExpresionActual++;
                    expresion = "";
                    estadoPrincipal = 0;
                    break;
                case 2:
                    if(cadenaConcatenar == '"'){
                        Arboles.add(new Arbol(ExpresionActual,expresion,cont,"N",String.valueOf(cont),String.valueOf(cont),"cadena"));
                        cont++;
                        ContExpresionActual++;
                        expresion = "";
                        estadoPrincipal = 0;
                    }else{
                        expresion+=cadenaConcatenar;
                        estadoPrincipal = 2;
                    }
                    break;
                case 3:
                    if(cadenaConcatenar == '}'){
                        Arboles.add(new Arbol(ExpresionActual,expresion,cont,"N",String.valueOf(cont),String.valueOf(cont),"cadena"));
                        cont++;
                        ContExpresionActual++;
                        expresion = "";
                        estadoPrincipal = 0;
                    }else{
                        expresion+=cadenaConcatenar;
                        estadoPrincipal = 3;
                    }
                    break;    
                case 4:
                    Arboles.add(new Arbol(ExpresionActual,expresion,0,"N","0","0","simbolo"));
                    ContExpresionActual++;
                    expresion = "";
                    estadoPrincipal = 0;
                    break;
            }
        }
    }
    
    int contArbol;
    String TemporalHoja;
    String TemporalExpresion;
    public void IncioRecorrerArbol(){
        if(contArbol<ContExpresionActual){
            TemporalExpresion = Arboles.get(0).getExpresion();
            if(TemporalExpresion == ExpresionActual){
               // temporalHoja = 
            }
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

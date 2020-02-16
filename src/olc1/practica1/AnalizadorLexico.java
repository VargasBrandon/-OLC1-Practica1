
package olc1.practica1;
import java.lang.*; 
import java.util.ArrayList;
import olc1.practica1.Token;

public class AnalizadorLexico {
    
    //Analizar Archivo
    public int Fila = 0;
    public int Columna = 0;
    public void AnalizadorLexico(String cadena){
        int estadoInicio = 0;
        int estadoPrincipal = 0;
        
        char cadenaConcatenar;
        char com = '"';
        char otro = '”';
        String token = "";
        String error = "";
        int ascii = 0;
        for (estadoInicio = 0; estadoInicio < cadena.length(); estadoInicio++)
        {
            cadenaConcatenar = cadena.charAt(estadoInicio);            
            switch (estadoPrincipal)
            {
                case 0:
                     ascii= (int)cadenaConcatenar;
                    if (cadenaConcatenar=='}'|| cadenaConcatenar=='{'
                        || cadenaConcatenar==';'|| cadenaConcatenar==':'|| cadenaConcatenar=='.'|| cadenaConcatenar=='|'
                        || cadenaConcatenar=='?'|| cadenaConcatenar=='*'|| cadenaConcatenar=='+'
                        || cadenaConcatenar=='~'|| cadenaConcatenar==',')
                        {
                        token += cadenaConcatenar;
                        estadoInicio -= 1;
                        Columna += 1;
                        estadoPrincipal = 1;                            
                    }
                    else if(Character.isLetter(cadenaConcatenar)){
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 2; 
                    }
                    else if(Character.isDigit(cadenaConcatenar)){
                       token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 3; 
                    }
                    else if(cadenaConcatenar == '/'){
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 4; 
                    }
                    else if(cadenaConcatenar == '<'){
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 6; 
                    }
                    else if(cadenaConcatenar == '"'){                         
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoInicio -= 1;
                        estadoPrincipal = 10; 
                    }
                    else if(cadenaConcatenar == '-'){
                        token += cadenaConcatenar;                        
                        Columna += 1;
                        estadoPrincipal = 13; 
                    }
                    else if(cadenaConcatenar == '%'){
                        token += cadenaConcatenar;                        
                        Columna += 1;
                        estadoPrincipal = 14; 
                    }
                    else{
                        error += cadenaConcatenar;
                        Columna += 1;
                        TablaErrores(error,"simbolo desconocido");
                        error = "";
                        estadoPrincipal = 0; 
                    }
                    break;
                case 1:
                    TablaToken(token,"simbolo");
                    token = "";
                    estadoPrincipal = 0;
                    break;
                case 2:
                    if (Character.isLetter(cadenaConcatenar) ||Character.isDigit(cadenaConcatenar) || cadenaConcatenar == '_'){
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 2;                           
                    }else if(cadenaConcatenar==':' || cadenaConcatenar=='-'|| cadenaConcatenar==','|| cadenaConcatenar==';'
                           ||cadenaConcatenar=='~' || cadenaConcatenar=='['|| cadenaConcatenar=='}'){
                        TablaToken(token,"identificador");
                        token = "";
                        estadoInicio -= 1;
                        estadoPrincipal = 0;
                    }else{
                        error += cadenaConcatenar;
                        Columna += 1;
                        TablaErrores(error,"simbolo desconocido");
                        error = "";
                        estadoPrincipal = 2;
                    }                  
                    break;
                case 3:
                    if (Character.isDigit(cadenaConcatenar)){
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 3;                           
                    }else if(cadenaConcatenar==':' || cadenaConcatenar=='-'|| cadenaConcatenar==','|| cadenaConcatenar==';'
                           ||cadenaConcatenar=='~'){
                        TablaToken(token,"digito");
                        token = "";
                        estadoInicio -= 1;
                        estadoPrincipal = 0;
                    }else{
                        error += cadenaConcatenar;
                        Columna += 1;
                        TablaErrores(error,"simbolo desconocido");
                        error = "";
                        estadoPrincipal = 3;
                    }                    
                    break;    
                case 4:
                    if (cadenaConcatenar=='/')
                    {
                        estadoInicio -= 1;
                        estadoPrincipal = 5;
                    }
                    else {
                        TablaToken(token,"Simbolo");
                        token = "";
                        Columna += 1;
                        estadoInicio -= 1;
                        estadoPrincipal = 0;
                    } 
                    break;
                case 5:
                    if (cadenaConcatenar=='/')
                    {
                        token += cadenaConcatenar;
                        TablaToken(token,"DobleDiagonal");
                        token = "";
                        Columna += 1;
                        estadoPrincipal = 5;
                    }
                    else if (cadenaConcatenar=='\n')
                    {
                        TablaToken(token,"Comentario");
                        token = "";
                        Columna = 0;
                        Fila += 1;
                        estadoPrincipal = 0;
                    }
                    else if (cadenaConcatenar=='\t')
                    {
                        Columna += 1;
                    }
                    else
                    {
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 5;
                    }
                    break;
                case 6:
                    if (cadenaConcatenar=='!' ){
                        token += cadenaConcatenar;
                        Columna += 1;
                        TablaToken(token,"SimboloComentarioA");
                        token = "";
                        estadoPrincipal = 7;                           
                    }else{
                        TablaToken(token,"Simbolo");
                        token = "";
                        estadoInicio -= 1;
                        estadoPrincipal = 0;
                    }
                    break;
                case 7:
                    if(cadenaConcatenar=='!'){                                            
                        estadoPrincipal = 8;
                    }else{
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 7;  
                    }
                    break;
                case 8:
                    if(cadenaConcatenar=='>'){
                        TablaToken(token,"ComentarioLargo");
                        token = "";
                        token += '!';
                        token += cadenaConcatenar;
                        estadoInicio -= 1;
                        estadoPrincipal = 9;
                    }else{
                        token += '!';
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 7;  
                    }
                    break;
                case 9:
                    TablaToken(token,"SimboloComentarioC");
                    token = "";
                    estadoPrincipal = 0;
                    break;
                case 10:
                    TablaToken(token,"ReservadaComillas");
                    token = "";
                    estadoPrincipal = 11;
                    break;
                case 11:
                    if (cadenaConcatenar=='"'){
                        TablaToken(token,"Lexema");
                        token = "";
                        estadoInicio -= 1;
                        Columna += 1;
                        estadoPrincipal = 12;
                    }
                    else if (cadenaConcatenar==' ')
                    {
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 11;                          
                    }
                    else if (cadenaConcatenar=='\n')
                    {
                        Columna = 0;
                        Fila += 1;
                        estadoPrincipal = 11;                           
                    }
                    else
                    {
                        token += cadenaConcatenar;
                        Columna += 1;
                        estadoPrincipal = 11;                           
                    }
                    break;
                case 12:
                    if (cadenaConcatenar=='"')
                    {
                        token += com;
                        TablaToken(token,"ReservadaComillas");
                        token = "";
                        Columna += 1;
                        estadoPrincipal = 0;                           
                    }
                    else
                    {
                        estadoPrincipal = 0;
                    }
                    break;
                case 13:
                    if (cadenaConcatenar=='>')
                    {
                        token += cadenaConcatenar;
                        TablaToken(token,"Flecha");
                        token = "";
                        Columna += 1;
                        estadoPrincipal = 0;
                    }
                    else {
                        TablaToken(token,"Simbolo");
                        token = "";
                        Columna += 1;
                        estadoInicio -= 1;
                        estadoPrincipal = 0;
                    } 
                    break;
                case 14:
                    if (cadenaConcatenar=='%')
                    {
                        token += cadenaConcatenar;
                        TablaToken(token,"simConjunto");
                        token = "";
                        Columna += 1;
                        estadoPrincipal = 0;
                    }
                    else {
                        TablaToken(token,"Simbolo");
                        token = "";
                        Columna += 1;
                        estadoInicio -= 1;
                        estadoPrincipal = 0;
                    }
                    break;
            }
        }
    }
    
    public ArrayList<Token> Tokens = new ArrayList<Token>();
    public int ContToken = 1;
    public void TablaToken(String token,String descripcion)
    {
        Tokens.add(new Token(ContToken,token,descripcion));
        ContToken++;
    }
    
    public ArrayList<Error> Errores = new ArrayList<Error>();
    public int ContError = 1;
    public void TablaErrores(String error,String descripcion)
   {
        Errores.add(new Error(ContToken,error,descripcion));
        ContError++;
        /*
        if (ContError > 1)
        {
            anderaGrafica = true;
        }
        */
        }
}

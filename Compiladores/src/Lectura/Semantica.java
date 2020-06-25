package Lectura;

import java.util.List;
import java.util.ArrayList;

public class Semantica {
    public static List<Object> tipadosGlobales; // Tipados de constantes y variables  globales
    public static List<Object> identificadoresGlobales; // Identificadores de constantes y variables globales
    public static List<Object> declaraciones; // Constante o variable

    public static List<Object> tipadosFunciones; // Tipados de las funciones
    public static List<Object> identificadoresFunciones; // Identificadores de las funciones

    public static List<Object> tipadosLocales; // Tipados de las variables dentro de las funciones
    public static List<Object> identificadoresLocales; // Identificadores de las variables dentro de las funciones

    static {
        tipadosGlobales = new ArrayList<>();
        identificadoresGlobales = new ArrayList<>();
        declaraciones = new ArrayList<>();
        
        tipadosFunciones = new ArrayList<>();
        identificadoresFunciones = new ArrayList<>();

        tipadosLocales = new ArrayList<>();
        identificadoresLocales = new ArrayList<>();
    }

    public static boolean exists(String ident) {
        
        if(!tipadosGlobales.isEmpty()) {
            if(identificadoresGlobales.contains(ident) || identificadoresFunciones.contains(ident))
                return true;
        }

        if(!tipadosFunciones.isEmpty()) {
            int index = tipadosFunciones.size()-1; 
            //System.out.println(tipadosFunciones.size());
            //System.out.println(identificadoresLocales.size());
            List<Object> list = (List<Object>)identificadoresLocales.get(index);
            List<Object> parametros = (List<Object>)list.get(0);
            
            if(!parametros.isEmpty()) {
                if(parametros.contains(ident))
                    return true;
            }
            
            if(exists(ident,list)) 
                return true;
        }
 
        return false;
    }

    private static Boolean exists(String ident, List<Object> list) {
        if(list.contains(ident)) 
            return true;

        if(!list.isEmpty()) {
            int index = list.size()-1;
            String cls = list.get(index).getClass().getName();
    
            if(cls.equals("java.util.List") || cls.equals("java.util.ArrayList")) {
                List<Object> l = (List<Object>)list.get(index);
    
                if(exists(ident,l))
                    return true;
            }
        }
        
        return false;
    }
}
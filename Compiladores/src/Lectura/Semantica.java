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
            List<Object> list = (List<Object>) identificadoresLocales.get(index);
            List<Object> parametros = (List<Object>) list.get(0);
            
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
                List<Object> l = (List<Object>) list.get(index);
    
                if(exists(ident,l))
                    return true;
            }
        }
        
        return false;
    }

    public static Boolean verificarTipado(String ident1, String ident2) {
        String tipo1 = getTipo(ident1);
        String tipo2 = getTipo(ident2);

        if(tipo1.equals(tipo2))
            return true;

        return false;
    }

    private static String getTipo(String ident) {
        int i;
        String tipo = "";
        
        if(identificadoresGlobales.contains(ident)) {
            i = identificadoresGlobales.indexOf(ident);
            tipo = (String) tipadosGlobales.get(i);
        } else {
            int index = tipadosLocales.size()-1;
            List<Object> fun = (List<Object>) identificadoresLocales.get(index);
            List<Object> tip = (List<Object>) tipadosLocales.get(index);
            List<Object> par = (List<Object>) fun.get(0);

            if(!par.isEmpty()) {
                if(par.contains(ident)) {
                    tip = (List<Object>) tip.get(0);
                    i = par.indexOf(ident);
                    tipo = (String) tip.get(i);
                }
            }
    
            while(tipo.equals("")) {
                if(fun.contains(ident)) {
                    i = fun.indexOf(ident);
                    tipo = (String) tip.get(i);
                } else if(!fun.isEmpty()){
                    index = fun.size()-1;
                    fun = (List<Object>) fun.get(index);
                    tip = (List<Object>) tip.get(index);
                }
            }
        }

        return tipo;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectura;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Orlando Odiseo Belmonte Flores
 * @boleta 2018671281
 * @author Guillermo Ingacio Bautista García
 * @boleta 2018670021
 * @evidencia Compilador(sintaxis fase 1)
 * @programa Sistemas computacionales
 * @uniad de aprendizaje Construcción de analizadoes sintácticos
 * @maestra Karina Mejia Rodrígez 
 * @fecha de entrega 20/05/2020
 * 
 */

public class Lexico {
    private List<String> tabla;
    public static ArrayList<String> identificadores;
    
    public Lexico() {
        String[] palabras = {
          "const",
          "void",
          "num",
          "String",
          "bool",
          "while",
          "if",
          "else",
          "true",
          "false",
          "return",
          "!!",
          "??",
          "=+",
          "=-",
          "==",
          "=!",
          "+!",
          "-!",
          "=",
          "+",
          "-",
          "*",
          "/",
          "+=",
          "-=",
          "/=",
          "*=",
          "++",
          "--",
          "**",
          "//",
          "[",
          "]",
          "(",
          ")",
          ",",
          ";"
        };
        
        tabla = Arrays.asList(palabras);
        identificadores = new ArrayList<>();
    }
    
    public String verificar(String s) {
        if(tabla.contains(s)) {
            return s;
        }  else if(numValido(s)){
            return "NUMERO";
        } else if(palabraValida(s)) {
            identificadores.add(s);
            return "IDENTIFICADOR";
        }
        
        return "ERROR";
    }
    
    private boolean caracterValido(byte x){
        //char x = readCharacter();
        return (x > 64 && x < 91 ) || (x > 96 && x < 123 ) || (x == 95) || (x > 47 && x < 58); 
    }
    
    private boolean numValido(String s){
        try {
            double d = Double.parseDouble(s);
        } catch (Exception e) {
            return false;
        }
        
        return true; 
    }
    
    private boolean  palabraValida(String s) {
        byte[] palabra = s.getBytes();
        
        for(int i=0;i<palabra.length;i++) {
            if(!caracterValido(palabra[i])) {
                return false;
            }
        }
        
        return true;
    }

    public List<String> generarTokens(ArrayList<String> p) {
        List<String> tokens = new ArrayList<String>();

        ArrayList<String> palabras = p;
        
        palabras.forEach(s -> {
            System.out.println("-----------------------");
            System.out.println("Analizando: "+s);
            String res = this.verificar(s);
            System.out.println("Encontrado: "+res);
            tokens.add(res);
        });
        
        //System.out.println("\n"+identificadores);

        return tokens;
    }
}


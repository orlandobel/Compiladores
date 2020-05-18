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
 * @author Alumno
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
          ";"
        };
        
        tabla = Arrays.asList(palabras);
        identificadores = new ArrayList<>();
    }
    
    public String verificar(String s) {
        if(tabla.contains(s)) {
            return s;
        } else if(palabraValida(s)) {
            identificadores.add(s);
            return "IDENTIFICADOR";
        } else if(numValido(s)){
            return "NUMERO";
        }
        
        return "ERROR";
    }
    
    private boolean caracterValido(byte x){
        //char x = readCharacter();
        return (x > 64 && x < 91 ) || (x > 96 && x < 123 ) || (x == 95); 
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
    
    public static void main(String[] args) {
        Lexico l = new Lexico();
        
        Archivo a = new Archivo();
        ArrayList<String> palabras = a.leerArchivo();
        
        palabras.forEach(s -> {
            System.out.println("-----------------------");
            System.out.println("Analizando: "+s);
            String res = l.verificar(s);
            System.out.println("Encontrado: "+res);
        });
        
        System.out.println("\n"+identificadores);
    }
}

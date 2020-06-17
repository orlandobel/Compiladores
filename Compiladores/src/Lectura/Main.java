/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectura;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alumno
 */
public class Main {
    public static void main(String args[]) {
        Archivo archivo = new Archivo();
        Lexico lexico = new Lexico();

//        char x = '\n';
        ArrayList<String> p = archivo.leerArchivo();
        List<String> t =lexico.generarTokens(p);

        Syntaxis.setListTokens(t, p);
        Syntaxis.CUERPO();
        
//        for(int i=0;i<10;i++) {
//            x = archivo.readCharacter();
//        }
        
        
    }
}

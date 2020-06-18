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


        ArrayList<String> p = archivo.leerArchivo();
        List<String> t =lexico.generarTokens(p);
        System.out.println();
        
        Syntaxis.setListTokens(t, p);
        Syntaxis.CUERPO();
        
        /*p.forEach(s -> {
            System.out.println(s);
        });*/
    }
}

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
        

        System.out.println("tipados globales: "+Semantica.tipadosGlobales);
        System.out.println("identificadores glovales: "+Semantica.identificadoresGlobales);
        System.out.println("declracion de: "+Semantica.declaraciones+"\n");

        System.out.println("tipados de funciones: "+Semantica.tipadosFunciones);
        System.out.println("identificadores de funciones: "+Semantica.identificadoresFunciones+"\n");

        System.out.println("tipados en funciones: "+Semantica.tipadosLocales);
        System.out.println("identificadores en funciones: "+Semantica.identificadoresLocales);
        /*p.forEach(s -> {
            System.out.println(s);
        });*/
    }
}

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
 * @author Orlando Odiseo Belmonte Flores
 * @boleta 2018671281
 * @author Guillermo Ingacio Bautista García
 * @boleta 2018670021
 * @evidencia Compilador(semantico)
 * @programa Sistemas computacionales
 * @uniad de aprendizaje Analisis semantico y generacion de codigo intermedio
 * @maestra Karina Mejia Rodrígez 
 * @fecha de entrega 29/06/2020
 * 
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
        
    }
}

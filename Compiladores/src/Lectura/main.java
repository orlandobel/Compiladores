/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectura;

import Lectura.Archivo;
/**
 *
 * @author Alumno
 */
public class main {
    public static void main(String args[]) {
        Archivo archivo = new Archivo();
        char x = '\n';
        
        for(int i=0;i<10;i++) {
            x = archivo.readCharacter();
        }
        
        System.out.println(x);
    }
}
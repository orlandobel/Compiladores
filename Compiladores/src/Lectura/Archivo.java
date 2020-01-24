/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Orlando Odiseo Belmonte Flores
 * @boleta 2018671281
 * @author Guillermo Ingacio Bautista García
 * @boleta 2018670021
 * @evidencia Compilador(leer)
 * @programa Sistemas computacionales
 * @uniad de aprendizaje Arquitectura de los compiladores e interpretes
 * @maestra Karina Mejia Rodrígez 
 * @fecha de entrega 24/01/2020
 * 
 */
public class Archivo {
    private JFileChooser file;
    private File abre;
    private FileReader archivo;
    private BufferedReader lee;
    
    public Archivo() {
        file = new JFileChooser();
        file.showOpenDialog(file);
        
        try {
            abre = file.getSelectedFile();

            if(abre != null) {
                archivo = new FileReader(abre);
                lee = new BufferedReader(archivo);
                
                String aux;
//                while((aux  = lee.readLine()) != null) {
//                    System.out.println(aux);
//                }
            }
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, e + ""
                    + "\nNo se ha encontrado el archivo",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public char readCharacter() {
        char c = '\n';
        try {
            c = (char)lee.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return c;
    }
}

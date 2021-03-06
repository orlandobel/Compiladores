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
 * @fecha de entrega 07/02/2020
 * 
 */

package Lectura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Archivo {
    //private JFileChooser file;
    private File abre;
    private FileReader archivo;
    private BufferedReader lee;
    private char x;

    public Archivo() {
        try {
            abre = new File("Suma.wop");

            if(abre != null) {
                archivo = new FileReader(abre);
                lee = new BufferedReader(archivo);
                x = readCharacter();
//                String aux;
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

    public Archivo(String path) {
        try {
            abre = new File("path");

            if(abre != null) {
                archivo = new FileReader(abre);
                lee = new BufferedReader(archivo);
                x = readCharacter();
//                String aux;
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
    
    public ArrayList<String> leerArchivo() {
        ArrayList<String> palabras = new ArrayList<>();
        String aux = cadenaAvil();
        while(!aux.equals("\uffff")) {
            //System.out.println(aux.trim().length() == 0);
            if(aux.trim().length() > 0) {
                palabras.add(aux);
            }
            aux = cadenaAvil();
        }
        
        return palabras;
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
    
    private boolean caracterValido(char x){
        //char x = readCharacter();
        return ((byte)x>64 && (byte)x<91 ) || ( (byte)x>96 && (byte)x<123 ) || ( (byte) x == 95); 
    }
    
    private boolean numValido(char x){
        return ( (byte)x>47 && (byte)x<58 ); 
    }

    private boolean simValido(char x) {
        return ((byte)x == 33) || ((byte)x == 42) || ((byte)x == 43) ||
               ((byte)x == 45) || ((byte)x == 47) || ((byte)x == 61) ||
               ((byte)x == 63);
    }
    
     public String cadenaAvil() {
        String aux = "";
//        System.out.println(x);

        if (caracterValido(x)) {
            aux =""+x;
            x = readCharacter();
//            System.out.println(x);

            while (caracterValido(x) || numValido(x)) {
                aux +=x;
                x = readCharacter();
//                System.out.println(x);
            }
        } else if(numValido(x)) {
            aux += x;
            x = this.readCharacter();

            while(numValido(x)) {
                aux += x;
                x = this.readCharacter();
            }

            if((byte)x == 46) {
                x = this.readCharacter();
                if(numValido(x)) {
                    aux += "."+x;
                    x = this.readCharacter();
                    while(numValido(x)) {
                        aux += x;
                        x = this.readCharacter();
                    }
                }
            }
        } else if(simValido(x)) {
            aux += x;
            x = this.readCharacter();

            while(simValido(x)) {
                aux += x;
                x = this.readCharacter();
            }
        } else {
            aux += x;
            x = readCharacter();
        }
 //       if (aux.equals("")) return "Parche de NullPointerExeption"; 
        return aux;
    } 
}

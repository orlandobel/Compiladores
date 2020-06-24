package Lectura;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class pruebas {
    public static void main(String[] args) {
        List<Object> l1 = new ArrayList<>();
        List<Object> l2 = new ArrayList<>();

        l1.add("hola");
        l1.add("caca");
        l1.add(l2);

        l2.add("lista2");
        
        for(int i=0;i<l1.size();i++) {
            System.out.println(l1.get(i).getClass().getName());
            //if(l1.get(i).getClass().)
        }
        
    }
}
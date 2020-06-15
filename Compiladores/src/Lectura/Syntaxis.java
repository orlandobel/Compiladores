package Lectura;

import java.util.List;
import java.util.Arrays;

public class Syntaxis {
    private static int i = -1;
    private static List<String> tokens;
    private static List<String> program;

    private static List<String> tipos;
    private static List<String> valores;

    public static void setListTokens(List<String> t, List<String> p) {
        tokens = t;
        program = p;

        String[] tip = {
            "void",
            "num",
            "String",
            "bool"
        };
        tipos = Arrays.asList(tip);

        String[] val = {
            "num",
            "nombre",
            "true",
            "false"
        };
        valores = Arrays.asList(val);
    }

    public static void CUERPO() {
        if(!tokens.isEmpty()) {
            CONSTANTE();
            VARIABLE();
            FUN();
        }
    }

    private static void CONSTANTE() {
        i++;
        if(tokens.get(i).equals("const")) {
            TIPO();
            ConstAux();
            
        } else {
            i--;
        }
    }

    private static void ConstAux() {
        i++;
        if(tokens.get(i).equals("nombre")) {
            i++;
            if(tokens.get(i).equals("=")) {
                VALOR();
                if(tokens.get(i+1).equals(";")) {
                    CONSTANTE();
                } else if(tokens.get(i+1).equals(",")) {
                    ConstAux();
                }
            } else {
                ERRORS(2);
            }
        } else {
            ERRORS(1);
        }
    }

    private static void VARIABLE() {
        if(i+1<tokens.size()) {
            TIPO();
            if(!tokens.get(i+2).equals("[")) {
                VarAux();
            } else {
                i--;
            }
        }
        
    }

    private static void VarAux() {
        i++;
        if(tokens.get(i).equals("nombre")) {
            i++;
            if(tokens.get(i).equals(";")) {
                VARIABLE();
            } else if(tokens.get(i).equals("=")) {
                ASIGNAR();
                VarAux();
            } else if(tokens.get(i).equals(",")) {
                VarAux();
            }
        } else {
            ERRORS(1);
        }
    }

    private static void FUN() {
        if(i+1<tokens.size()) {
            i++;
            TIPO();
            i++;
            if(tokens.get(i).equals("[")) {
                PARAMETRO();
                i++;
                if(tokens.get(i).equals("]")) {
                    i++;
                    if(tokens.get(i).equals("(")) {
                        i++;
                        INSTRUCCIONES();
                        i++;
                        if(tokens.get(i).equals(")")) {
                            FUN();
                        } else {
                            ERRORS(8);
                        }
                    } else {
                        ERRORS(7);
                    }
                } else {
                    ERRORS(6);
                }
            } else {
                ERRORS(5);
            }
        }
    }

    public static void TIPO() {
        i++;
        if(tipos.contains(tokens.get(i))) {

        } else {
            ERRORS(0);
        }
    }

    public static void VALOR() {
        i++;
        if(valores.contains(tokens.get(i))) {

        } else {
            ERRORS(3);
        }
    }

    private static void PARAMETRO() {

    }

    private static void BUCLE() {

    }

    private static void IF() {

    }

    private static void CONDICIONAL() {

    }

    private static void EXPRESION() {

    }

    private static void FACTOR() {

    }

    private static void ASIGNAR() {
        i++;

        if(valores.contains(tokens.get(i))) {

        } else {
            ERRORS(4);
        }
    }

    private static void LLAMAR() {

    }

    private static void INSTRUCCIONES() {

    }

    private static void RETORNO() {
        
    }

    private static void ERRORS(int code) {
        String esp = "";
        String token = program.get(i);

        switch(code) {
            case 0:
                esp = "\"void\", \"num\", \"String\" o \"bool\"";
                break;
            case 1:
                esp = "un nombre o identificador";
                break;
            case 2:
                esp = "\"=\"";
                break;
            case 3:
                esp = "un valor";
                break;
            case 4:
                esp = "un valor o un identificador";
                break;
            case 5:
                esp = "\"[\"";
                break;
            case 6:
                esp = "\"]\"";
                break;
            case 7:
                esp = "\"(\"";
                break;
            case 8:
                esp = "\")\"";
                break;
            default: break;
        }

        System.err.println("Se esperaba "+esp+" se ha encontrado: \""+token+"\"");
        System.exit(1);
    }
}
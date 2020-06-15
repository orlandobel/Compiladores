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

    public static void TIPO() {
        i++;
        if(tipos.contains(tokens.get(i))) {

        } else {
            ERRORS(1);
        }
    }

    private static void CONSTANTE() {
        if(!tipos.contains(tokens.get(i+1))) {
            i++;
            if(tokens.get(i).equals("const")) {
                TIPO();
                AuxConst();
            } else {
                ERRORS(0);
            }
        }
    }

    private static void VARIABLE() {
        if(i+1 < tokens.size()) {
            TIPO();
            if(!tokens.get(i+2).equals("[")) {
                AuxVar();
            }
            
        }
    }

    private static void FUN() {
        if(i+1 < tokens.size()) {
            TIPO();
            i++;
            if(tokens.get(i).equals("nombre")) {
                i++;
                if(tokens.get(i).equals("[")) {
                    if(!tokens.get(i+1).equals("]")) {
                        PARAMETRO();
                    }

                    i++;
                    if(tokens.get(i).equals("]")) {
                        i++;
                        if(tokens.get(i).equals("(")) {
                            if(!tokens.get(i+1).equals(")")) {
                                INSTRUCCIONES();
                            }

                            i++;
                            if(tokens.get(i).equals(")")) {
                                FUN();
                            } else {
                                ERRORS(9);
                            }
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
                ERRORS(2);
            }
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

    }

    private static void LLAMAR() {

    }

    private static void INSTRUCCIONES() {

    }

    private static void RETORNO() {

    }

    private static void VALOR() {
        i++;
        if(valores.contains(tokens.get(i))) {

        } else {
            ERRORS(4);
        }
    }

    private static void AuxConst() {
        i++;
        if(tokens.get(i).equals("nombre")) {
            i++;
            if(tokens.get(i).equals("=")) {
                VALOR();
                i++;
                switch(tokens.get(i)) {
                    case ",":
                        AuxConst();
                        break;
                    case ";":
                        CONSTANTE();
                        break;
                    default: 
                        ERRORS(5);
                        break;
                } 
            } else {
                ERRORS(3);
            }
        } else {
            ERRORS(2);
        }
    }

    private static void AuxVar() {
        i++;
        if(tokens.get(i).equals("nombre")) {
            i++;
            switch(tokens.get(i)) {
                case ",":
                    AuxVar();
                    break;
                case "=":
                    ASIGNAR();
                    break;
                case ";":
                    VARIABLE();
                    break;
                default: 
                    ERRORS(6);
                    break;
            }
        } else {
            ERRORS(2);
        }
    }

    private static void ERRORS(int code) {
        String esp = "";
        String token = program.get(i);

        switch(code) {
            case 0:
                esp = "\"const\"";
                break;
            case 1:
                esp = "\"void\", \"num\", \"String\" o \"bool\"";
                break;
            case 2:
                esp = "un nombre o identificador";
                break;
            case 3:
                esp = "\"=\"";
                break;
            case 4:
                esp = "un valor";
                break;
            case 5:
                esp = "\",\" o \";\"";
                break;
            case 6:
                esp = "\",\", \"=\", \";\" o \"[\"";
                break;
            case 7:
                esp = "\"]\"";
                break;
            case 8:
                esp = "\"(\"";
                break;
            case 9:
                esp = "\")\"";
            default: break;
        }

        System.err.println("Se esperaba "+esp+" se ha encontrado: \""+token+"\"");
        System.exit(1);
    }
}
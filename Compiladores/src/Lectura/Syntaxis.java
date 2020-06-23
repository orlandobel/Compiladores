package Lectura;

import java.util.List;
import java.util.Arrays;

public class Syntaxis {
    private static int i = -1;
    private static List<String> tokens;
    private static List<String> program;

    private static List<String> tipos;
    private static List<String> booleanos;
    private static List<String> operadores;

    public static void setListTokens(List<String> t, List<String> p) {
        tokens = t;
        program = p;

        String[] tip = {
            "void",
            "num",
            "String",
            "bool"
        };
        String[] bools = {
            "==",
            "=!",
            "=+",
            "=-",
            "-!",
            "=!"
        };
        String[] ops = {
            "=",
            "+",
            "-",
            "*",
            "/",
            "+=",
            "-=",
            "**",
            "//",
            "*=",
            "/="
        };
        
        tipos = Arrays.asList(tip);
        booleanos = Arrays.asList(bools);
        operadores = Arrays.asList(ops);
    }

    public static void CUERPO() {
        if(!tokens.isEmpty()) {
            CONSTANTE();
            VARIABLE();
            FUN();
        }
        System.out.println("Compilacion exitosa");
    }

    public static void TIPO() {
        i++;
        if(tipos.contains(tokens.get(i))) {

        } else {
            System.out.println("Error en TIPO");
            ERRORS(1);
        }
    }

    private static void CONSTANTE() {
        if(i+1 < tokens.size()) {
            if(!tipos.contains(tokens.get(i+1))) {
                i++;
                if(tokens.get(i).equals("const")) {
                    TIPO();
                    i++;
                    if(tokens.get(i).equals("IDENTIFICADOR")) {
                        i++;
                        if(tokens.get(i).equals("=")) {
                            i++;
                            if(tokens.get(i).equals("NUMERO") || tokens.get(i).equals("IDENTIFICADOR")) {
                                AUX1();
                            } else {
                                ERRORS(4);
                            }
                        } else {
                            ERRORS(3);
                        }
                    } else {
                        ERRORS(2);
                    }
                } else {
                    ERRORS(0);
                }
            }
        }
    }

    private static void AUX1() {
        i++;
        switch(tokens.get(i)) {
            case ",":
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    i++;
                    if(tokens.get(i).equals("=")) {
                        i++;
                        if(tokens.get(i).equals("NUMERO") || tokens.get(i).equals("IDENTIFICADOR")) {
                            AUX1();
                        } else {
                            ERRORS(4);
                        }
                    } else {
                        ERRORS(3);
                    }
                } else {
                    ERRORS(2);
                }
                break;
            case ";":
                CONSTANTE();
                break;
            default:
                ERRORS(5);
                break;
        }
    }

    private static void VARIABLE() {
        if(i+1 < tokens.size()) {
            TIPO();
            if(!tokens.get(i+2).equals("[")) {
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    AUX2();
                } else {
                    ERRORS(2);
                }
            } else {
                i--;
            }
        }
    }

    private static void AUX2() {
        i++;
        switch(tokens.get(i)) {
            case ",":
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    AUX2();
                }
                break;
            case "=":
                ASIGNAR();
                AUX3();
                break;
            case ";":
                if(tipos.contains(tokens.get(i+1))) {
                    VARIABLE();
                }
                break;
            default:
                ERRORS(6);
        }
    }

    private static void AUX3() {
        i++;
        switch(tokens.get(i)) {
            case ",":
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    AUX2();
                }
                break;
            case ";":
                if(tipos.contains(tokens.get(i+1))) {
                    VARIABLE();
                }
                break;
            default:
                ERRORS(5);
                break;
        }
    }

    private static void FUN() {
        if(i+1 < tokens.size()) {
            TIPO();
            i++;
            if(tokens.get(i).equals("IDENTIFICADOR")) {
                i++;
                if(tokens.get(i).equals("[")) {
                    PARAMETRO();

                    i++;
                    if(tokens.get(i).equals("]")) {
                        i++;
                        if(tokens.get(i).equals("(")) {
                            INSTRUCCIONES();
                            
                            i++;
                            if(tokens.get(i).equals(")")) {
                                FUN();
                            } else {
                                ERRORS(10);
                            }
                        } else {
                            ERRORS(9);
                        }
                    } else {
                        ERRORS(8);
                    }
                } else {
                    ERRORS(6);
                }
            }
        }
    }

    private static void PARAMETRO() {
        if(!tokens.get(i+1).equals("]")) {
            TIPO();
            i++;
            if(tokens.get(i).equals("IDENTIFICADOR")) {
                AUX4();
            }
        }
        
    }

    private static void AUX4() {
        if(!tokens.get(i+1).equals("]")) {
            i++;
            if(tokens.get(i).equals(",")) {
                PARAMETRO();
            } else {
                ERRORS(11);
            }
        }
    }

    private static void BUCLE() {
        i++;
        if(tokens.get(i).equals("while")) {
            i++;
            if(tokens.get(i).equals("[")) {
                CONDICIONAL();
                i++;
                if(tokens.get(i).equals("]")) {
                    i++;
                    if(tokens.get(i).equals("(")) {
                        INSTRUCCIONES();

                        i++;
                        if(tokens.get(i).equals(")")) {

                        } else {
                            ERRORS(10);
                        }
                    } else {
                        ERRORS(9);
                    }
                } else {
                    ERRORS(11);
                }
            } else {
                ERRORS(7);
            }
        } else {
            ERRORS(-1);
        }
    }

    private static void IF() {
        i++;
        if(tokens.get(i).equals("if")) {
            i++;
            if(tokens.get(i).equals("[")) {
                CONDICIONAL();

                i++;
                if(tokens.get(i).equals("]")) {
                    i++;
                    if(tokens.get(i).equals("(")) {
                        INSTRUCCIONES();

                        i++;
                        if(tokens.get(i).equals(")")) {
                            AUX5();
                        } else {
                            ERRORS(10);
                        }
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
            ERRORS(-1);
        }
    }

    private static void AUX5() {
        if(!tipos.contains(tokens.get(i+1))
         && !tokens.get(i+1).equals("IDENTIFICADOR")
         && !tokens.get(i+1).equals("NUMERO")
         && !tokens.get(i+1).equals("if")
         && !tokens.get(i+1).equals("while")
         && !tokens.get(i+1).equals(")")
         && !tokens.get(i+1).equals("return")) {
            i++;
            if(tokens.get(i).equals("else")) {
                i++;
                if(tokens.get(i).equals("(")) {
                    INSTRUCCIONES();

                    i++;
                    if(tokens.get(i).equals(")")) {

                    } else {
                        ERRORS(10);
                    }
                } else {
                    ERRORS(9);
                }
            } else {
                ERRORS(-1);;
            }
        }
    }

    private static void CONDICIONAL() {
        EXPRESION();
        AUX6();
    }

    private static void AUX6() {
        if(!tokens.get(i+1).equals("]")) {
            i++;
            if(booleanos.contains(tokens.get(i))) {
                EXPRESION();
                AUX7();
            } else {
                ERRORS(12);
            }
        }
    }

    private static void AUX7() {
        if(!tokens.get(i+1).equals("]")) {
            i++;
            if(tokens.get(i).equals("!!") || tokens.get(i).equals("??")) {
                CONDICIONAL();
            } else {
                ERRORS(13);
            }
        }
    }

    private static void EXPRESION() {
        FACTOR();
        AUX8();
    }

    private static void AUX8() {
        if(!booleanos.contains(tokens.get(i+1))
         && !tokens.get(i+1).equals("!!")
         && !tokens.get(i+1).equals("??")
         && !tokens.get(i+1).equals("]")
         && !tokens.get(i+1).equals(";")) {
            i++;
            if(operadores.contains(tokens.get(i))) {
                EXPRESION();
            } else {
                ERRORS(-1);
            }
        }
    }

    private static void FACTOR() {
        i++;
        switch(tokens.get(i)) {
            case "IDENTIFICADOR":
            case "NUMERO":
                break;
            case "-":
                i++;
                if(tokens.get(i).equals("NUMERO")) {

                } else {
                    ERRORS(4);
                }
                break;
            case "[":
                EXPRESION();
                i++;
                if(tokens.get(i).equals("]")) {

                } else {
                    ERRORS(8);
                }
            default:
                ERRORS(14);
                break;
        }
    }

    private static void ASIGNAR() {
        i++;
        switch(tokens.get(i)) {
            case "-":
                i++;
                if(tokens.get(i).equals("NUMERO")) {
                    
                } else {
                    ERRORS(4);
                }
                break;
            case "NUMERO":
                break;
            case "IDENTIFICADOR":
                if(tokens.get(i+1).equals("[")){ 
                    i--;
                    LLAMAR();
                }
                break;
            default:
                ERRORS(15);
                break;
        }
    }

    private static void LLAMAR() {
        i++;
        if(tokens.get(i).equals("IDENTIFICADOR")) {
            i++;
            if(tokens.get(i).equals("[")) {
                AUX9();

                i++;
                if(tokens.get(i).equals("]")) {
                    i++;
                    if(tokens.get(i).equals(";")) {

                    } else {
                        ERRORS(17);
                    }
                } else {
                    ERRORS(8);
                }
            } else {
                ERRORS(7);
            }
        } else {
            ERRORS(-1);
        }
    }

    private static void AUX9() {
        if(!tokens.get(i+1).equals("]")) {
            i++;
            if(tokens.get(i).equals("IDENTIFICADOR")) {
                AUX10();
            } else {
                ERRORS(2);
            }
        }
    }

    private static void AUX10() {
        if(!tokens.get(i+1).equals("]")) {
            i++;
            if(tokens.get(i).equals(",")) {
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    AUX10();
                } else {
                    ERRORS(2);
                }
            } else {
                ERRORS(11);
            }
        }
    }

    private static void INSTRUCCIONES() {
        AUX11();
        RETORNO();
    }

    private static void AUX11() {
        if(!tokens.get(i+1).equals("return")
         && !tokens.get(i+1).equals(")")) {

            
             if(tipos.contains(tokens.get(i+1))) {
                VARIABLE();    
                AUX11();
             } else {
                switch(tokens.get(i+1)) {
                    case "if":
                        IF();
                        AUX11();
                        break;
                    case "while":
                        BUCLE();
                        AUX11();
                        break;
                    case "NUMERO":
                    case "[":
                        EXPRESION();
                        AUX11();
                        break;
                    case "IDENTIFICADOR":
                        if(tokens.get(i+2).equals("[")) {
                            LLAMAR();
                        } else {
                            EXPRESION();
                            i++;
                            if(tokens.get(i).equals(";")) {

                            }
                        }
                        AUX11();
                        break;
                    default:
                        i++;
                        ERRORS(-1);
                        break;
                }
            }
        }
    }

    private static void RETORNO() {
        if(!tokens.get(i+1).equals(")")) {
            i++;
            if(tokens.get(i).equals("return")) {
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    i++;
                    if(tokens.get(i).equals(";")) {

                    } else {
                        ERRORS(17);
                    }
                } else {
                    ERRORS(2);
                }
            } else {
                ERRORS(-1);
            }
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
                esp = "un identificador";
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
                esp = "\"[\"";
                break;
            case 8:
                esp = "\"]\"";
                break;
            case 9:
                esp = "\"(\"";
                break;
            case 10:
                esp = "\")\"";
                break;
            case 11:
                esp = "\",\" o \"]\"";
                break;
            case 12:
                esp = "\"==\", \"+!\", \"=+\", \"=-\", \"+!\" o \"-!\"";
                break;
            case 13:
                esp = "\"!!\" o \"??\"";
                break;
            case 14:
                esp = "un identificador, numero o \"[\"";
                break;
            case 15:
                esp = "un identificador o numero";
                break;
            case 16:
                esp = "identificador o \"]\"";
                break;
            case 17:
                esp = "\";\"";
                break;
            default: 
                System.out.println("\""+tokens.get(i)+"\" inesperado");
                System.exit(1);
                break;
        }

        System.err.println("Se esperaba "+esp+" se ha encontrado: \""+token+"\"");
        System.exit(1);
    }
}
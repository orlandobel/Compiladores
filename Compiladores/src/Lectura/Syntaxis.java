package Lectura;

import java.util.List;
import java.util.Arrays;

public class Syntaxis {
    private static int i = -1;
    private static List<String> tokens;
    private static List<String> program;

    private static List<String> tipos;
    private static List<String> valores;
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
        String[] val = {
            "num",
            "IDENTIFICADOR",
            "true",
            "false"
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
        valores = Arrays.asList(val);
        booleanos = Arrays.asList(bools);
        operadores = Arrays.asList(ops);
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
            if(tokens.get(i).equals("IDENTIFICADOR")) {
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
            } else {
                ERRORS(2);
            }
        }
    }

    private static void PARAMETRO() {
        TIPO();
        i++;
        if(tokens.get(i).equals("IDENTIFICADOR")) {
            if(!tokens.get(i+1).equals("]")) {
                i++;
                if(tokens.get(i).equals(",")) {
                    PARAMETRO();
                } else {
                    ERRORS(11);
                }
            }
        } else {
            ERRORS(2);
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
                        if(!tokens.get(i+1).equals(")")) {
                            INSTRUCCIONES();
                        }
                        
                        i++;
                        if(tokens.get(i).equals(")")) {

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
                        if(!tokens.get(i+1).equals(")")) {
                            INSTRUCCIONES();
                        } 
                        
                        i++;
                        if(tokens.get(i).equals(")")) {
                            if(tokens.get(i+1).equals("else")) {
                                i = i+2;
                                if(tokens.get(i).equals("(")) {
                                    if(!tokens.get(i+1).equals(")")) {
                                        INSTRUCCIONES();
                                    }

                                    i++;
                                    if(tokens.get(i).equals(")")) {

                                    } else {
                                        ERRORS(10);
                                    }
                                } else {
                                    ERRORS(9);
                                }
                            }
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

    private static void CONDICIONAL() {
        EXPRESION();
        if(!tokens.get(i+1).equals("]")) {
            i++;
            if(booleanos.contains(tokens.get(i))) {
                EXPRESION();
                if(!tokens.get(i+1).equals("]")) {
                    i++;
                    if(tokens.get(i).equals("!!") || tokens.get(i).equals("??")) {
                        CONDICIONAL();
                    } else {
                        ERRORS(13);
                    }
                }
            } else {
                ERRORS(12);
            }
        }
    }

    private static void EXPRESION() {
        FACTOR();
        if(!booleanos.contains(tokens.get(i+1)) && !tokens.get(i+1).equals("!!") && !tokens.get(i+1).equals("??") && !tokens.get(i).equals("]")) {
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
        if(tokens.get(i).equals("IDENTIFICADOR") || tokens.get(i).equals("NUMERO")) {

        } else if(tokens.get(i).equals("[")) {
            EXPRESION();
            i++;
            if(tokens.get(i).equals("]")) {

            } else {
                ERRORS(8);
            }
        } else {
            ERRORS(14);
        }
    }

    private static void ASIGNAR() {
        i++;
        switch(tokens.get(i)) {
            case "IDENTIFICADOR":
                if(tokens.get(i+1).equals("[")) {
                    i--;
                    LLAMAR();
                }
                break;
            case "NUMERO":
                break;
            default:
                ERRORS(15);
        }
    }

    private static void LLAMAR() {
        i++;
        if(tokens.get(i).equals("IDENTIFICADOR")) {
            i++;
            if(tokens.get(i).equals("[")) {
                if(!tokens.get(i+1).equals("]")) {
                    i++;
                    if(tokens.get(i).equals("IDENTIFICADOR")) {
                        if(!tokens.get(i+1).equals("]")) {
                            i++;
                            if(tokens.get(i).equals(",")) {
                                while(tokens.get(i).equals(",")) {
                                    i++;
                                    if(tokens.get(i).equals("IDENTIFICADOR")) {
                                        i++;
                                    } else {
                                        ERRORS(2);
                                    }
                                }
                                i--;
                            } else {
                                ERRORS(11);
                            }
                        }

                        i++;
                        if(tokens.get(i).equals("]")) {
                            i++;
                            if(tokens.get(i).equals(";")) {

                            } else {
                                ERRORS(17);
                            }
                        } else {
                            ERRORS(11);
                        }
                    } else {
                        ERRORS(16);
                    }
                }
            } else {
                ERRORS(7);
            }
        } else {
            ERRORS(2);
        }
    }

    private static void INSTRUCCIONES() {
        switch(tokens.get(i+1)) {
            case "num":
            case "String":
            case "bool":
                VARIABLE();
                INSTRUCCIONES();
                break;
            case "if":
                IF();
                INSTRUCCIONES();
                break;
            case "while":
                BUCLE();
                INSTRUCCIONES();
                break;
            case "IDENTIFICADOR":
                if(tokens.get(i+2).equals("[")) {
                    LLAMAR();
                } else {
                    EXPRESION();
                }
                INSTRUCCIONES();
                break;
            case "NUMERO":
                EXPRESION();
                break;
            case "return":
                RETORNO();
                break;
            case ")":
                break;
            default:
                ERRORS(-1);
                break;
        }
    }

    private static void RETORNO() {
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

    private static void VALOR() {
        i++;
        if(valores.contains(tokens.get(i))) {

        } else {
            ERRORS(4);
        }
    }

    private static void AuxConst() {
        i++;
        if(tokens.get(i).equals("IDENTIFICADOR")) {
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
        if(tokens.get(i).equals("IDENTIFICADOR")) {
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
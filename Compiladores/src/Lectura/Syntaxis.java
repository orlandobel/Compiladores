package Lectura;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Syntaxis {
    /* Desplazamiento en el programa para el analicis y manejo de errores */
    private static int i = -1;
    private static List<String> tokens;
    private static List<String> program;

    /* Para el manejo de tipados, y simbolos validos del lenguaje */
    private static List<String> tipos;
    private static List<String> booleanos;
    private static List<String> operadores;

    /* Para el analisis semantico */
    public static String tipado; // Manejo del tipado de la constante, variable o funcion evaluando
    public static String identificador; // Manejo del identificador de la constante, variable o funcion evaluando

    /* Inicio del analisis sintactico y semantico */
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
            VARIABLE(Semantica.tipadosGlobales,Semantica.identificadoresGlobales,true);
            FUN();
        }
        System.out.println("Compilacion exitosa\n");
    }

    public static void TIPO() {
        i++;
        if(tipos.contains(tokens.get(i))) {

        } else {
            ERRORS(1);
        }
    }

    private static void CONSTANTE() {
        if(i+1 < tokens.size()) {
            if(!tipos.contains(tokens.get(i+1))) {
                i++;
                if(tokens.get(i).equals("const")) {
                    TIPO();
                    tipado = tokens.get(i);
                    i++;
                    if(tokens.get(i).equals("IDENTIFICADOR")) {
                        identificador = program.get(i);
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
        if(Semantica.exists(identificador))
            ERRORS(18);

        Semantica.tipadosGlobales.add(tipado);
        Semantica.identificadoresGlobales.add(identificador);
        Semantica.declaraciones.add("CONSTANTE");

        i++;
        switch(tokens.get(i)) {
            case ",":
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    identificador = tokens.get(i);
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

    private static void VARIABLE(List<Object> tip, List<Object>id, Boolean global) {
        if(i+1 < tokens.size()) {
            TIPO();
            if(!tokens.get(i+2).equals("[")) {
                tipado = tokens.get(i);
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    identificador = program.get(i);
                    AUX2(tip,id,global);
                } else {
                    ERRORS(2);
                }
            } else {
                i--;
            }
        }
    }

    private static void AUX2(List<Object> tip, List<Object>id, Boolean global) {
        if(Semantica.exists(identificador))
            ERRORS(18);
            
        tip.add(tipado);
        id.add(identificador);
        if(global)
            Semantica.declaraciones.add("VARIABLE");

        i++;
        switch(tokens.get(i)) {
            case ",":
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    identificador = program.get(i);
                    AUX2(tip,id,global);
                }
                break;
            case "=":
                ASIGNAR();
                AUX3(tip,id,global);
                break;
            case ";":
                if(tipos.contains(tokens.get(i+1))) {
                    VARIABLE(tip,id,global);
                }
                break;
            default:
                ERRORS(6);
        }
    }

    private static void AUX3(List<Object> tip, List<Object>id, Boolean global) {
        i++;
        switch(tokens.get(i)) {
            case ",":
                i++;
                if(tokens.get(i).equals("IDENTIFICADOR")) {
                    identificador = program.get(i);
                    AUX2(tip,id,global);
                }
                break;
            case ";":
                if(tipos.contains(tokens.get(i+1))) {
                    VARIABLE(tip,id,global);
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
            tipado = tokens.get(i);
            i++;
            if(tokens.get(i).equals("IDENTIFICADOR")) {
                identificador = program.get(i);
                if(Semantica.exists(identificador))
                    ERRORS(18);

                Semantica.tipadosFunciones.add(tipado);
                Semantica.identificadoresFunciones.add(identificador);

                i++;
                if(tokens.get(i).equals("[")) {
                    List<Object> tip = new ArrayList<>();
                    List<Object> id = new ArrayList<>();

                    Semantica.tipadosLocales.add(tip);
                    Semantica.identificadoresLocales.add(id);

                    List<Object> tipPar = new ArrayList<>();
                    List<Object> idPar = new ArrayList<>();

                    tip.add(tipPar);
                    id.add(idPar);

                    PARAMETRO(tipPar,idPar);
                    
                    i++;
                    if(tokens.get(i).equals("]")) {
                        i++;
                        if(tokens.get(i).equals("(")) {            
                            INSTRUCCIONES(tip,id);
                            
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

    private static void PARAMETRO(List<Object> tip, List<Object> id) {
        if(!tokens.get(i+1).equals("]")) {
            TIPO();
            tipado = tokens.get(i);
            i++;
            if(tokens.get(i).equals("IDENTIFICADOR")) {
                identificador = program.get(i);

                if(Semantica.exists(identificador))
                    ERRORS(18);

                tip.add(tipado);
                id.add(identificador);
                AUX4(tip,id);
            }
        }
        
    }

    private static void AUX4(List<Object> tip, List<Object> id) {
        if(!tokens.get(i+1).equals("]")) {
            i++;
            if(tokens.get(i).equals(",")) {
                PARAMETRO(tip,id);
            } else {
                ERRORS(11);
            }
        }
    }

    private static void BUCLE(List<Object> tip, List<Object> id) {
        List<Object> tipados = new ArrayList<>(); // Tipados loclaes
        List<Object> identificadores = new ArrayList<>(); // Identificadores locales

        tip.add(tipados);
        id.add(identificadores);

        i++;
        if(tokens.get(i).equals("while")) {
            i++;
            if(tokens.get(i).equals("[")) {
                CONDICIONAL();
                i++;
                if(tokens.get(i).equals("]")) {
                    i++;
                    if(tokens.get(i).equals("(")) {
                        INSTRUCCIONES(tipados,identificadores);

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

    private static void IF(List<Object> tip, List<Object> id) {
        List<Object> tipados = new ArrayList<>(); // Tipados loclaes
        List<Object> identificadores = new ArrayList<>(); // Identificadores locales

        tip.add(tipados);
        id.add(identificadores);

        i++;
        if(tokens.get(i).equals("if")) {
            i++;
            if(tokens.get(i).equals("[")) {
                CONDICIONAL();

                i++;
                if(tokens.get(i).equals("]")) {
                    i++;
                    if(tokens.get(i).equals("(")) {
                        INSTRUCCIONES(tipados,identificadores);

                        i++;
                        if(tokens.get(i).equals(")")) {
                            AUX5(tip,id);
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

    private static void AUX5(List<Object> tip, List<Object> id) {
        if(!tipos.contains(tokens.get(i+1))
         && !tokens.get(i+1).equals("IDENTIFICADOR")
         && !tokens.get(i+1).equals("NUMERO")
         && !tokens.get(i+1).equals("if")
         && !tokens.get(i+1).equals("while")
         && !tokens.get(i+1).equals(")")
         && !tokens.get(i+1).equals("return")) {

            List<Object> tipados = new ArrayList<>(); // Tipados loclaes
            List<Object> identificadores = new ArrayList<>(); // Identificadores locales

            tip.add(tipados);
            id.add(identificadores);

            i++;
            if(tokens.get(i).equals("else")) {
                i++;
                if(tokens.get(i).equals("(")) {
                    INSTRUCCIONES(tipados,identificadores);

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
                /*identificador = program.get(i);
                if(!Semantica.exists(identificador))
                    ERRORS(19);
                break;*/
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
                identificador = program.get(i);
                if(!Semantica.exists(identificador))
                    ERRORS(19);

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
                    identificador = program.get(i);
                    if(!Semantica.exists(identificador))
                        ERRORS(19);
                        
                    AUX10();
                } else {
                    ERRORS(2);
                }
            } else {
                ERRORS(11);
            }
        }
    }

    private static void INSTRUCCIONES(List<Object> tip, List<Object> id) {
        AUX11(tip,id);
        RETORNO();
    }

    private static void AUX11(List<Object> tip, List<Object> id) {
        if(!tokens.get(i+1).equals("return")
         && !tokens.get(i+1).equals(")")) {

            
             if(tipos.contains(tokens.get(i+1))) {
                VARIABLE(tip,id,false);    
                AUX11(tip,id);
             } else {
                switch(tokens.get(i+1)) {
                    case "if":
                        IF(tip,id);
                        AUX11(tip,id);
                        break;
                    case "while":
                        BUCLE(tip,id);
                        AUX11(tip,id);
                        break;
                    case "NUMERO":
                    case "[":
                        EXPRESION();
                        AUX11(tip,id);
                        break;
                    case "IDENTIFICADOR":
                        identificador = program.get(i+1);
                        if(!Semantica.exists(identificador))
                            ERRORS(19);

                        if(tokens.get(i+2).equals("[")) {
                            LLAMAR();
                        } else {
                            EXPRESION();
                            i++;
                            if(tokens.get(i).equals(";")) {

                            }
                        }
                        AUX11(tip,id);
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
            case 18:
                System.out.println("El identificador \""+identificador+"\" ya esta en uso en el ambito global o local");
                System.exit(1);
                break;
            case 19:
                System.out.println("El identificador \""+identificador+"\" no ha sido declarado");
                System.exit(1);
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
package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    private String text;
    private int i;
    private ArrayList<Character> punctuations = new ArrayList<Character>(Arrays.asList(':', ';', ',', '(', ')', '.'));
    private ArrayList<String> keywords = new ArrayList<String>(Arrays.asList("programme", "variable", "integer", "char", "float",
            "begin", "end", "if", "then", "while", "do", "read", "write", "else"));
    private ArrayList<String> oprel = new ArrayList<String>(Arrays.asList("==", "<>", "<", ">", "<=", ">="));
    private ArrayList<String> opadd = new ArrayList<String>(Arrays.asList("+", "-", "||"));
    private ArrayList<String> opmul = new ArrayList<String>(Arrays.asList("*", "/", "%", "&&"));

    public Lexer init(String text) throws Exception {
        i = 0;
        this.text = skipComment(text);
        return this;
    }
    private boolean isEOT() {
        return text.length() <= i;
    }

    private char c() throws Exception {
        if (isEOT()) {
            throw new Exception("No more character");
        }
        return text.charAt(i);
    }

    private char next() throws Exception {
        char c = c();
        ++i;
        return c;
    }
    private void skipSpace() throws Exception {
        while (!isEOT() && Character.isWhitespace(c())) {
            next();
        }
    }
    private boolean isSignStart(char c) {
        return c == '|' || c == '+' || c == '-' || c == '*' || c == '/'|| c == '<'|| c == '>'|| c == '%'|| c == '&';
    }
    private boolean isPunctuationStart(char c) throws Exception{
        return punctuations.contains(c) || c == '=';
    }

    private boolean isDigitStart(char c) throws Exception {
        return Character.isDigit(c);
    }

    private boolean isVariableStart(char c) throws Exception {
        return Character.isAlphabetic(c);
    }
    private String punctuation() throws Exception {
        StringBuilder b = new StringBuilder();
        String t;
        b.append(next());
        while (!isEOT() && isPunctuationStart(c())) {
            b.append(next());
        }
        t = b.toString();
        return t;
    }
    private String sign() throws Exception {
        StringBuilder b = new StringBuilder();
        String t;
        b.append(next());
        while (!isEOT() && isSignStart(c())) {
            b.append(next());
        }
        t = b.toString();
        if (oprel.contains(t))
        {
            t = "oprel";
        }
        else if(opadd.contains(t))
        {
            t = "opadd";
        }
        else if(opmul.contains(t))
        {
            t = "opmul";
        }
        return t;
    }

    private String digit() throws Exception {
        StringBuilder b = new StringBuilder();
        b.append(next());
        while (!isEOT() && Character.isDigit(c())) {
            b.append(next());
        }
        return "nb";
    }

    private String variable() throws Exception {
        StringBuilder b = new StringBuilder();
        b.append(next());
        while (!isEOT() && (Character.isAlphabetic(c()) || Character.isDigit(c()))) {
            b.append(next());
        }
        if (keywords.contains(b.toString()))
        {
            return b.toString();
        }else
        {
            return "id";
        }
    }
    public String nextString() throws Exception {
        skipSpace();
        if (isEOT()) {
            return null;
        } else if (isSignStart(c())) {
            return sign();
        } else if (isDigitStart(c())) {
            return digit();
        } else if (isVariableStart(c())) {
            return variable();
        } else if (isPunctuationStart(c())) {
            return punctuation();
        }
        else {
            System.out.println(c());
            throw new Exception("Character error");
        }
    }
    public ArrayList LexWriter() throws Exception {
        ArrayList<String> Lex = new ArrayList<String>() ;
        String s = nextString();
        while(s != null){
            Lex.add(s);
            s = nextString();
        }
        Lex.add("$");
        return Lex;
    }

    private String skipComment(String text) throws Exception {
        StringBuilder b = new StringBuilder(text);
        b.replace(b.indexOf("(*"),b.indexOf("*)")+2, "");
        return b.toString();
    }

    public static void main(String[] args) throws Exception {
        String text = "programme a1; variable a , b : integer ; variable c : char ; begin a := 1 ; if b < c then read(b) ; end .";
        ArrayList<String> s = new Lexer().init(text).LexWriter();
        System.out.println(s);

    }
}
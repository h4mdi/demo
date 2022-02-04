package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Character.isWhitespace;

public class Parser {
    public ArrayList<String> input= new ArrayList<String>();

    private int indexOfInput=-1;
    //Stack
    Stack<String> strack=new Stack<String>();
    String eps="";
    ArrayList<String> grammaire =  new ArrayList<String>(Arrays.asList("programme id ; Dcl Inst_composee .", "variable Liste_id : Type ; Dcl","id Liste_id'",", id Liste_id'","integer","char","float","begin Inst end","Liste_inst","I Liste_inst'","; I Liste_inst'","id := Exp_simple","if Exp then I I'","while Exp do I","read ( I'' )","write ( I'' )","else I","id","nb","Exp_simple Exp'","oprel Exp_simple","Terme Exp_simple'","opadd Terme Exp_simple'","Facteur Terme'","opmul Facteur Terme'","id","nb","( Exp_simple )"));
    //Table of rules
    String [][] table=
            {
                    {grammaire.get(0),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null}
                    ,
                    {null,grammaire.get(1),null,null,null,eps,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null}
                    ,
                    {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,grammaire.get(2),null,null,null,null,null}
                    ,
                    {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,eps,grammaire.get(3),null,null,null,null,null,null,null,null,null}
                    ,
                    {null,null,grammaire.get(4),grammaire.get(5),grammaire.get(6),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null}
                    ,
                    {null,null,null,null,null,grammaire.get(7),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null}
                    ,
                    {null,null,null,null,null,null,eps,grammaire.get(8),null,grammaire.get(8),null,grammaire.get(8),grammaire.get(8),null,null,null,null,null,null,null,null,grammaire.get(8),null,null,null,null,null}
                    ,
                    {null,null,null,null,null,null,null,grammaire.get(9),null,grammaire.get(9),null,grammaire.get(9),grammaire.get(9),null,null,null,null,null,null,null,null,grammaire.get(9),null,null,null,null,null}
                    ,
                    {null,null,null,null,null,null,eps,null,null,null,null,null,null,null,grammaire.get(10),null,null,null,null,null,null,null,null,null,null,null,null}
                    ,
                    {null,null,null,null,null,null,eps,grammaire.get(12),null,grammaire.get(13),null,grammaire.get(14),grammaire.get(15),null,null,null,null,null,null,null,null,grammaire.get(11),null,null,null,null,null}
                    ,
                    {null,null,null,null,null,null,eps,null,null,null,null,null,null,grammaire.get(16),eps,null,null,null,null,null,null,null,null,null,null,null,null}
                    ,
                    {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,grammaire.get(17),null,null,null,grammaire.get(18),null}
                    ,
                    {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,grammaire.get(19),null,grammaire.get(19),null,null,null,grammaire.get(19),null}
                    ,
                    {null,null,null,null,null,null,null,null,eps,null,eps,null,null,null,null,null,null,null,null,null,null,null,null, grammaire.get(20),null,null,null}
                    ,
                    {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,grammaire.get(21),null,grammaire.get(21),null,null,null,grammaire.get(21),null}
                    ,
                    {null,null,null,null,null,null,eps,null,eps,null,eps,null,null,eps,eps,null,null,null,null,null,eps,null,grammaire.get(22),eps,null,null,null}
                    ,
                    {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,grammaire.get(23),null,grammaire.get(23),null,null,null,grammaire.get(23),null}
                    ,
                    {null,null,null,null,null,null,eps,null,eps,null,eps,null,null,eps,eps,null,null,null,null,null,eps,null,eps,eps,grammaire.get(24),null,null}
                    ,
                    {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,grammaire.get(27),null,grammaire.get(25),null,null,null,grammaire.get(26),null}

            };
    String [] nonTers={"P", "Dcl", "Liste_id", "Liste_id'", "Type", "Inst_composee", "Inst", "Liste_inst", "Liste_inst'",
    "I", "I'", "I''", "Exp", "Exp'", "Exp_simple", "Exp_simple'", "Terme", "Terme'", "Facteur"};
    String [] terminals={"programme", "variable", "integer", "char", "float", "begin", "end", "if", "then", "while", "do", "read",
            "write", "else", ";", ".", ":", ",", ":=", "(", ")", "id", "opadd", "oprel", "opmul", "nb", "$"};
    private String resultat ="";


    public Parser(ArrayList<String> in)
    {
        this.input=in;
    }

    private  void pushRule(String rule)
    {
        String[] words = rule.split(" ");
        for(int i=words.length-1;i>=0;i--) {
            push(words[i]);
            System.out.println(words[i]);
        }
    }

    //algorithm
    public String algorithm    () throws Exception {


        push(this.input.get(0)+"");//
        push("P");
        //Read one token from input

        String token=read();
        String top=null;

        do
        {
            top=this.pop();
            //if top is non-terminal
            if(isNonTerminal(top))
            {
                String rule=this.getRule(top, token);
                if(rule == eps) {
                    continue;
                }else {
                    this.pushRule(rule);

                }
            }
            else if(isTerminal(top))
            {
                if(!top.equals(token))
                {
                     resultat =("this token is not correct , By Grammer rule . Token : ("+token+")");
               }
                else
                {
                    resultat =("Matching: Terminal :( "+token+" )");
                    token =read();

                    continue;

//top=pop();
                }

            }
            else
            {
                 resultat =("Never Happens , Because top : ( "+top+" )");
            }

            if(token.equals("$"))
            {
                break;
            }
            //if top is terminal

        }while(true);//out of the loop when $

        //accept
        if(token.equals("$"))
        {
            return resultat = "Input is Accepted by LL1";

        }
        else
        {
            return resultat ="Input is not Accepted by LL1";


        }

    }

    private boolean isTerminal(String s) {
        for(int i=0;i<this.terminals.length;i++)
        {
            if(s.equals(this.terminals[i]))
            {
                return true;
            }

        }
        return false;
    }

    private boolean isNonTerminal(String s) {
        for(int i=0;i<this.nonTers.length;i++)
        {
            if(s.equals(this.nonTers[i]))
            {
                return true;
            }

        }
        return false;
    }

    private String read() {
        indexOfInput++;
        String str=this.input.get(indexOfInput);
        return str;
    }

    private void push(String s) {
        this.strack.push(s);
    }
    private String pop() {
        return this.strack.pop();
    }

    private String error(String message) {
        System.out.println(message);
        throw new RuntimeException(message) ;
    }
    public String getRule(String non,String term) throws Exception {

        int row=getnonTermIndex(non);
        int column=getTermIndex(term);
        String rule=this.table[row][column];
        if(rule==null)
        {
            resultat =("There is no Rule by this , Non-Terminal("+non+") ,Terminal("+term+") ");
        }
        return rule;
    }

    private int getnonTermIndex(String non) throws Exception {
        for(int i=0;i<this.nonTers.length;i++)
        {
            if(non.equals(this.nonTers[i]))
            {
                return i;
            }
        }
        resultat =(non +" is not NonTerminal");
        return -1;
    }

    private int getTermIndex(String term) throws Exception {
        for(int i=0;i<this.terminals.length;i++)
        {
            if(term.equals(this.terminals[i]))
            {
                return i;
            }
        }
        resultat =(term +" is not Terminal");
        return -1;
    }

    //main
    public static void main(String[] args) throws Exception {
        ArrayList<String> temp=new ArrayList<String>(Arrays.asList("programme","id", ";", "variable", "id",",", "id", ":", "integer", ";", "variable", "id", ":", "char", ";", "begin", "id", ":=", "nb", ";", "if", "id", "oprel", "id", "then", "read", "(", "id", ")", ";", "end", ".", "$"));
        Parser parser=new Parser(temp);
        parser.algorithm();

    }

}

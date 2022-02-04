package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


@Controller
public class MainController {
    String m = "" ;

    @RequestMapping("/")
    public String index(Model model) {
        
        return "index";
    }

    @RequestMapping("/code")
    public String buildModel(@RequestParam String input, Model model) throws Exception {

        try {
            input = input.replace("\n","") ;
            Lexer lexer = new Lexer() ;
            ArrayList<String> s  =lexer.init(input).LexWriter() ;
            Parser parser = new Parser(s) ;
            String result = parser.algorithm();
            model.addAttribute("m", result ) ;
            model.addAttribute("in", input ) ;

        } catch(Exception e) {
            model.addAttribute("errorData", e.getMessage());

        }





        return  "index";
    }




}

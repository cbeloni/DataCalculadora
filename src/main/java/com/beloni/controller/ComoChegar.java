package com.beloni.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ComoChegar {

    @RequestMapping("endereco")
    public String endereco(){
        return "endereco";
    }
}

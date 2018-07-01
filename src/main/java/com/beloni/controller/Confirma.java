package com.beloni.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Confirma {

    @RequestMapping("confirma")
    public String presenca(){
        return "convite";
    }
}

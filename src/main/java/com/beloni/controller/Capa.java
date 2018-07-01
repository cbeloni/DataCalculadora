package com.beloni.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class Capa {

    @RequestMapping(value="/")
    public String start(Map<String, Object> model, HttpSession session, HttpServletRequest request){
        String data_casamento = "18 11 2017";
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Long dias_faltantes = null;
        try{
            Date date2 = dateFormat.parse(data_casamento);
            Date date = new Date();
            Date newDate = new Date(date.getTime() - 7200000);

            Date hoje_sem_horas = dateFormat.parse(dateFormat.format(newDate));
            System.out.println("date2.getTime(): " + date2);
            System.out.println("hoje_sem_horas.getTime(): "  + hoje_sem_horas);
            System.out.println("Hoje com horas:" + date);
            long diff = date2.getTime() - (hoje_sem_horas.getTime());
            dias_faltantes = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            System.out.println(dateFormat.format(date));
        }catch (ParseException e){
            e.printStackTrace();

        }

        System.out.println("Current dir:");
        System.out.println(System.getProperty("user.dir"));
        request.setAttribute("dias_faltantes", dias_faltantes);
        return "capa";

    }
}

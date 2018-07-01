package com.beloni.controller;

import com.beloni.model.DataCalc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class Capa {
    final public static int inicioJornada = 9;
    final public static int fimJornada = 18;

    @RequestMapping(value="/")
    public String start(Map<String, Object> model, Model model2, HttpSession session, HttpServletRequest request){
        model2.addAttribute("dataCalc", new DataCalc());
        return "capa";

    }
    @PostMapping(value="/")
    public String calcularData(@ModelAttribute DataCalc dataCalc, HttpSession session, HttpServletRequest request) {
        //String entrada = request.("entrada");
        System.out.println("Entrada: " + dataCalc.getTempoAdd());
        Long tempoSeconds = Long.parseLong(dataCalc.getTempoAdd(), 10);
        Date dataFinal = calculaPrazo8x5(dataCalc.getDataEntrada(),tempoSeconds);
        String dataSLA = String.format("%td/%tm/%tY %<tH:%<tM:%<tS", dataFinal, dataFinal, dataFinal, dataFinal, dataFinal, dataFinal);;
        request.setAttribute("dataSLA", dataSLA);
        return "capa";
    }

    public static Date calculaPrazo8x5(Date dataInicial, long progresso) {
        dataInicial = getDataComercialInicial(dataInicial);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataInicial);
		//HashMap<String, String> feriados = staticRepo.getFeriados();
		while(progresso>0){
			date = calendar.getTime();
			//System.out.println("Data: "+date);
			if(progresso>32400){
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				//if(!isFeriado(calendar.getTime(), feriados)){
					if(diaUtil(calendar.getTime())){
						progresso -=32400;
					}
				//}
			}
			else if(progresso>3600){
				progresso -=3600;
				calendar.add(Calendar.HOUR_OF_DAY, 1);
				if((calendar.get(Calendar.HOUR_OF_DAY)>=9&&(calendar.get(Calendar.HOUR_OF_DAY)<18)||
						(calendar.get(Calendar.HOUR_OF_DAY)==17 &&calendar.get(Calendar.MINUTE)<=59))){
				}
				else{
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					calendar.set(Calendar.HOUR_OF_DAY, inicioJornada);
				}
			}
			else if(progresso>600){
				progresso -=600;
				calendar.add(Calendar.MINUTE, 10);
				if((calendar.get(Calendar.HOUR_OF_DAY)>=9&&(calendar.get(Calendar.HOUR_OF_DAY)<18)||
						(calendar.get(Calendar.HOUR_OF_DAY)==17 &&calendar.get(Calendar.MINUTE)<=59))){
				}
				else{
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					calendar.set(Calendar.HOUR_OF_DAY, inicioJornada);
				}
			}
			else if(progresso>60){
				progresso -=60;
				calendar.add(Calendar.MINUTE, 1);
				if((calendar.get(Calendar.HOUR_OF_DAY)>=9&&(calendar.get(Calendar.HOUR_OF_DAY)<18)||
						(calendar.get(Calendar.HOUR_OF_DAY)==17 &&calendar.get(Calendar.MINUTE)<=59))){
				}
				else{
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					calendar.set(Calendar.HOUR_OF_DAY, inicioJornada);
				}
			}
			else{
				progresso -=1;
				calendar.add(Calendar.SECOND,1);
				if((calendar.get(Calendar.HOUR_OF_DAY)>=9&&(calendar.get(Calendar.HOUR_OF_DAY)<18)||
						(calendar.get(Calendar.HOUR_OF_DAY)==17 &&calendar.get(Calendar.MINUTE)<=59))){
				}
				else{
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					calendar.set(Calendar.HOUR_OF_DAY, inicioJornada);
				}

			}
		}

		calendar = validaFinalDeSemana(calendar);
		//if (isFeriado(calendar.getTime(), feriados)) {
		//	calendar.add(Calendar.DAY_OF_MONTH, 1);
		//}
		Calendar calendarDataInicio = Calendar.getInstance();
		calendarDataInicio.setTime(dataInicial);
		if(!(calendarDataInicio.get(Calendar.HOUR_OF_DAY)>=9&&(calendarDataInicio.get(Calendar.HOUR_OF_DAY)<18)||
				(calendarDataInicio.get(Calendar.HOUR_OF_DAY)==17 &&calendarDataInicio.get(Calendar.MINUTE)<=59))){
		}

		date = calendar.getTime();

		return date;
	}

    public static Date dataStringToDate(String data) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date dataInicial = fmt.parse(data);
            return dataInicial;
        } catch (Exception e) {
            System.out.println("Error ao converter a data");
            return null;
        }

    }

    public static Calendar validaFinalDeSemana(Calendar calendar){
		// Verifica se e domingo
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		// Verifica se e sabado
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			calendar.add(Calendar.DAY_OF_MONTH, 2);
		}
		return calendar;
	}

    public static boolean diaUtil(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        }

        // Verifica se e sabado
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return false;
        }

        return true;
    }

    private static Date getDataComercialInicial(Date dataInicioSLA) {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(dataInicioSLA);
        if (cal.get(Calendar.HOUR_OF_DAY) >= 0 && (cal.get(Calendar.HOUR_OF_DAY) <= 8)) {
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
        } else if (cal.get(Calendar.HOUR_OF_DAY) >= 18 && (cal.get(Calendar.HOUR_OF_DAY) <= 23)) {
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        dataInicioSLA = cal.getTime();

        //if(!isFeriado(calendar.getTime(), feriados)){
        //    cal.setTime(dataInicioSLA);
        //    cal.add(Calendar.DAY_OF_MONTH, 1);
        //}
        dataInicioSLA = cal.getTime();

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.setTime(dataInicioSLA);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
        }

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.setTime(dataInicioSLA);
            cal.add(Calendar.DAY_OF_MONTH, 2);
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
        }
        //Se feriado na segunda
        //if(!isFeriado(calendar.getTime(), feriados)){
        //    cal.setTime(dataInicioSLA);
        //    cal.add(Calendar.DAY_OF_MONTH, 1);
        //    cal.set(Calendar.HOUR_OF_DAY, 9);
        //    cal.set(Calendar.MINUTE, 0);
        //    cal.set(Calendar.SECOND, 0);
        //}
        dataInicioSLA = cal.getTime();
        return dataInicioSLA;
    }
}

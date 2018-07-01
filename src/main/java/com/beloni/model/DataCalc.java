package com.beloni.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DataCalc {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dataEntrada;
    String tempoAdd;

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getTempoAdd() {
        return tempoAdd;
    }

    public void setTempoAdd(String tempoAdd) {
        this.tempoAdd = tempoAdd;
    }
}

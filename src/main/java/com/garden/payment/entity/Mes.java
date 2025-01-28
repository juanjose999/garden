package com.garden.payment.entity;

public enum Mes {
    FEBRERO("Febrero"),MARZO("Marzo"),ABRIL("Abril"),
    MAYO("Mayo"),JUNIO("Junio"),JULIO("Julio"),AGOSTO("Agosto"),
    SEPTIEMBRE("Septiembre"),OCTUBRE("Octubre"),NOVIEMBRE("Noviembre"),DICIEMBRE("Diciembre");

    private String nombreMes;

    private Mes (String mes){
        nombreMes = mes;
    }

    public String getNombreMes(){
        return nombreMes;
    }

}

package com.PruebaTecnica.Energym.Exceptions.Clase; 

public class ClaseNotFoundException extends RuntimeException{

    public  ClaseNotFoundException(){
        super("Clase no encontrada :(");
    }

}
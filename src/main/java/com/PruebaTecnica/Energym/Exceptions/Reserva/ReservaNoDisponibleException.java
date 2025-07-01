package com.PruebaTecnica.Energym.Exceptions.Reserva; 

public class ReservaNoDisponibleException  extends RuntimeException{
    
    public  ReservaNoDisponibleException(String mensaje){
        super(mensaje);
    }

}
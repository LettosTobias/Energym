package com.PruebaTecnica.Energym.Exceptions.Usuario; 

public class UsuarioNotFoundException extends RuntimeException{

    public  UsuarioNotFoundException(){
        super("Usuario no encontrado :(");
    }

}
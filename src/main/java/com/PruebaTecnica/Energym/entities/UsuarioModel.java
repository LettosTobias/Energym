package com.PruebaTecnica.Energym.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "usuario")
@Getter
@Setter
public class UsuarioModel {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String password;
    private String rol;


}

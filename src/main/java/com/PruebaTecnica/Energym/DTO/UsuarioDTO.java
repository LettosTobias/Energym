package com.PruebaTecnica.Energym.DTO;

import java.io.Serializable;

import com.PruebaTecnica.Energym.entities.UsuarioModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO implements Serializable {
    private Integer id;
    private String nombre;
    private String email;
    private String telefono;

        
    public UsuarioDTO(){
        
    }

    public UsuarioDTO(UsuarioModel u) {
        this.id = u.getId();
        this.nombre = u.getNombre();
        this.email = u.getEmail();
        this.telefono = u.getTelefono();
    }

    // Constructor que recibe los campos
    public UsuarioDTO(int id, String nombre, String email , String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }
    

}

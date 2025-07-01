package com.PruebaTecnica.Energym.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.PruebaTecnica.Energym.entities.ClaseModel;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ClaseDTO implements Serializable{
 
    
    private int id;
    private String nombre;
    private String entrenador;
    private String horario;
    private int capacidad;

    public ClaseDTO(int id, String nombre, String entrenador, String horario , int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.horario = horario;
        this.capacidad = capacidad;
    }

    public ClaseDTO(ClaseModel clase) {
        this.id = clase.getId();
        this.nombre = clase.getNombre();
        this.entrenador = clase.getEntrenador();
        this.horario = clase.getDia().concat(": " + clase.getHorario()); //miercoles: 16.30hrs
        this.capacidad = clase.getCapacidad();
    }

    
}

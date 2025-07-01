
package com.PruebaTecnica.Energym.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clases")
@Getter
@Setter
public class ClaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private int capacidad;

    private String entrenador;

    private String dia; //lunes , miercoles, viernes, etc...

    private String horario; // 16:30 hrs , 17:00 hrs...
}

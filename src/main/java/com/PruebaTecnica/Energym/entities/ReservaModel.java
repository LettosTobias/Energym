package com.PruebaTecnica.Energym.entities;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reservas")
public class ReservaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

     @ManyToOne
    private UsuarioModel usuario;
     @ManyToOne
    private ClaseModel clase;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fecha_creacion;

     private LocalDateTime  fechaReserva;
    private Boolean asistio;
    private boolean cancelo;
    
}

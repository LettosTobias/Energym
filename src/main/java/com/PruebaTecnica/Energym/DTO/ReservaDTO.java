package com.PruebaTecnica.Energym.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.PruebaTecnica.Energym.entities.ReservaModel;

import lombok.Data;


@Data
public class ReservaDTO implements  Serializable {

    private int id;
    private String usuarioNombre;
    private int usuarioId;
    private int claseId;
    private String claseNombre;
    private LocalDateTime  fechaReserva;
    private Boolean asistio;
    private boolean cancelo;


    public ReservaDTO(){ }

    public ReservaDTO( ReservaModel reserva){

        this.id = reserva.getId();
        this.usuarioId = reserva.getUsuario().getId();
        this.usuarioNombre = reserva.getUsuario().getNombre();
        this.claseId = reserva.getClase().getId();
        this.claseNombre = reserva.getClase().getNombre();
        this.fechaReserva = reserva.getFechaReserva();
        this.asistio = reserva.getAsistio();
        this.cancelo = reserva.isCancelo();
        
    }
}

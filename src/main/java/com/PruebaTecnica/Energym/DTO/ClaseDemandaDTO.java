package com.PruebaTecnica.Energym.DTO;

import java.io.Serializable;

import com.PruebaTecnica.Energym.entities.ClaseModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClaseDemandaDTO implements Serializable{
    
    private int claseId;
    private String nombreClase;
    private long cantidadReservas;
    private long capacidad;
    private long cuposDisponibles;

    public ClaseDemandaDTO(ClaseModel clase, long cantidadReservas) {
        this.claseId = clase.getId();
        this.nombreClase = clase.getNombre();
        this.cantidadReservas = cantidadReservas;
        this.capacidad = clase.getCapacidad();
        this.cuposDisponibles = (capacidad) - (cantidadReservas);
    }
}

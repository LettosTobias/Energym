package com.PruebaTecnica.Energym.DTO;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AsistenciasUsuarioDTO implements Serializable{

    private int usuarioId;
    private String usuarioNombre;
    private List<ReservaDTO> reservasAsistidas;

        public int getCantidadTotalAsistencias() {
        return reservasAsistidas != null ? reservasAsistidas.size() : 0;
    }


    public AsistenciasUsuarioDTO(int usuarioId, String usuarioNombre, List<ReservaDTO> reservasAsistidas) {
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.reservasAsistidas = reservasAsistidas;
    }

}

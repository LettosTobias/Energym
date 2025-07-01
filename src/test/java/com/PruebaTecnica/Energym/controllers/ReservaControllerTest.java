package com.PruebaTecnica.Energym.controllers;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.PruebaTecnica.Energym.DTO.ReservaDTO;
import com.PruebaTecnica.Energym.services.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearReserva_DeberiaRetornarCreated() throws Exception {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setId(1);
        reservaDTO.setUsuarioNombre("Juan");
        reservaDTO.setClaseNombre("Crossfit");

        Mockito.when(reservaService.save(any(ReservaDTO.class))).thenReturn(reservaDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuarioNombre").value("Juan"))
                .andExpect(jsonPath("$.claseNombre").value("Crossfit"));
    }

    @Test
    void borrarReserva_DeberiaRetornarOkSiExiste() throws Exception {
        when(reservaService.borrarReserva(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Eliminado correctamente"));
    }

    @Test
    void borrarReserva_DeberiaRetornarNotFoundSiNoExiste() throws Exception {
        when(reservaService.borrarReserva(1)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cancelarReserva_DeberiaRetornarReservaCancelada() throws Exception {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setId(1);
        reservaDTO.setCancelo(true);

        when(reservaService.cancelarReserva(1)).thenReturn(reservaDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/reservas/1/cancelar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cancelo").value(true));
    }

    @Test
    void marcarAsistencia_DeberiaRetornarCantidadAsistencias() throws Exception {
        when(reservaService.marcarAsistencia(1)).thenReturn(4);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/reservas/1/asistio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad de clases asistidas este mes").value(4));
    }

}

package com.PruebaTecnica.Energym.controllers;

import com.PruebaTecnica.Energym.DTO.ClaseDTO;
import com.PruebaTecnica.Energym.services.ClaseService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClaseController.class)
class ClaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaseService claseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearClase_DeberiaRetornar201() throws Exception {
        ClaseDTO dto = new ClaseDTO();
        dto.setId(1);
        dto.setNombre("Crossfit");
        dto.setEntrenador("Juan");
        dto.setHorario("Lunes: 18hs");
        dto.setCapacidad(20);

        Mockito.when(claseService.save(any(ClaseDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/clases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Crossfit"))
                .andExpect(jsonPath("$.horario").value("Lunes: 18hs"));
    }

    @Test
    void getClases_DeberiaRetornarLista() throws Exception {
        ClaseDTO dto = new ClaseDTO();
        dto.setId(1);
        dto.setNombre("Crossfit");
        dto.setEntrenador("Juan");
        dto.setHorario("Lunes: 18hs");
        dto.setCapacidad(20);

        Mockito.when(claseService.getClases()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/clases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].horario").value("Lunes: 18hs"));
    }

    @Test
    void getClaseById_DeberiaRetornarClase() throws Exception {
        ClaseDTO dto = new ClaseDTO();
        dto.setId(1);
        dto.setNombre("Crossfit");
        dto.setEntrenador("Juan");
        dto.setHorario("Lunes: 18hs");
        dto.setCapacidad(20);

        Mockito.when(claseService.getClaseById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/clases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Crossfit"))
                .andExpect(jsonPath("$.horario").value("Lunes: 18hs"));
    }

    @Test
    void borrarClase_DeberiaRetornar200() throws Exception {
        Mockito.when(claseService.borrarClase(1)).thenReturn(true);

        mockMvc.perform(delete("/api/clases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Clase eliminada correctamente"));
    }

    @Test
    void actualizarClase_DeberiaRetornar200() throws Exception {
        ClaseDTO dto = new ClaseDTO();
        dto.setId(1);
        dto.setNombre("Actualizada");
        dto.setEntrenador("Luis");
        dto.setHorario("Martes: 20hs");
        dto.setCapacidad(25);

        Mockito.when(claseService.actualizarClase(Mockito.eq(1), any(ClaseDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/clases/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Actualizada"))
                .andExpect(jsonPath("$.horario").value("Martes: 20hs"));
    }
}

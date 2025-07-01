package com.PruebaTecnica.Energym.controllers;

import com.PruebaTecnica.Energym.DTO.UsuarioDTO;
import com.PruebaTecnica.Energym.services.UsuarioService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearUsuario_DeberiaRetornar201() throws Exception {
        UsuarioDTO dto = new UsuarioDTO(1, "Juan", "juan@mail.com", "123456");

        Mockito.when(usuarioService.save(any(UsuarioDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void getUsuarios_DeberiaRetornarLista() throws Exception {
        UsuarioDTO dto = new UsuarioDTO(1, "Juan", "juan@mail.com", "123456");
        Mockito.when(usuarioService.getUsuarios()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getUsuarioById_DeberiaRetornarUsuario() throws Exception {
        UsuarioDTO dto = new UsuarioDTO(1, "Juan", "juan@mail.com", "123456");
        Mockito.when(usuarioService.getUsuarioById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void borrarUsuario_DeberiaRetornar200() throws Exception {
        Mockito.when(usuarioService.borrarUsuario(1)).thenReturn(true);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Se eliminó correctamente"));
    }

    @Test
    void editarUsuario_DeberiaRetornar200() throws Exception {
        UsuarioDTO dto = new UsuarioDTO(1, "Nuevo", "nuevo@mail.com", "222");
        Mockito.when(usuarioService.actualizarUsuario(Mockito.eq(1), any(UsuarioDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo"));
    }
}

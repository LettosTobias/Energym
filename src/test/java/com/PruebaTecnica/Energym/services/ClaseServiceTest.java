package com.PruebaTecnica.Energym.services;

import com.PruebaTecnica.Energym.DTO.ClaseDTO;
import com.PruebaTecnica.Energym.entities.ClaseModel;
import com.PruebaTecnica.Energym.repositories.ClaseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClaseServiceTest {

    @Mock
    private ClaseRepository claseRepository;

    @InjectMocks
    private ClaseService claseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_DeberiaCrearClase() {
        ClaseDTO dto = new ClaseDTO();
        dto.setNombre("Crossfit");
        dto.setEntrenador("Juan");
        dto.setHorario("Lunes:18hs");
        dto.setCapacidad(20);

        ClaseModel guardada = new ClaseModel();
        guardada.setId(1);
        guardada.setNombre("Crossfit");
        guardada.setEntrenador("Juan");
        guardada.setDia("Lunes");
        guardada.setHorario("18hs");
        guardada.setCapacidad(20);

        when(claseRepository.save(any(ClaseModel.class))).thenReturn(guardada);

        ClaseDTO resultado = claseService.save(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Crossfit", resultado.getNombre());
        assertEquals("Juan", resultado.getEntrenador());
        assertEquals("Lunes: 18hs", resultado.getHorario());
        assertEquals(20, resultado.getCapacidad());
    }

    @Test
    void getClases_DeberiaDevolverLista() {
        ClaseModel clase = new ClaseModel();
        clase.setId(1);
        clase.setNombre("Crossfit");
        clase.setEntrenador("Juan");
        clase.setDia("Lunes");
        clase.setHorario("18hs");
        clase.setCapacidad(20);

        when(claseRepository.findAll()).thenReturn(List.of(clase));

        List<ClaseDTO> resultado = claseService.getClases();

        assertEquals(1, resultado.size());
        assertEquals("Crossfit", resultado.get(0).getNombre());
        assertEquals("Lunes: 18hs", resultado.get(0).getHorario());
    }

    @Test
    void getClaseById_DeberiaDevolverClase() {
        ClaseModel clase = new ClaseModel();
        clase.setId(1);
        clase.setNombre("Crossfit");
        clase.setEntrenador("Juan");
        clase.setDia("Lunes");
        clase.setHorario("18hs");
        clase.setCapacidad(20);

        when(claseRepository.findById(1)).thenReturn(Optional.of(clase));

        ClaseDTO resultado = claseService.getClaseById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Lunes: 18hs", resultado.getHorario());
    }

    @Test
    void actualizarClase_DeberiaActualizarCorrectamente() {
        ClaseModel clase = new ClaseModel();
        clase.setId(1);
        clase.setNombre("Viejo");
        clase.setEntrenador("Pedro");

        when(claseRepository.findById(1)).thenReturn(Optional.of(clase));
        when(claseRepository.save(any(ClaseModel.class))).thenReturn(clase);

        ClaseDTO actualizado = new ClaseDTO();
        actualizado.setNombre("Nuevo");
        actualizado.setEntrenador("Luis");
        actualizado.setHorario("Martes:20hs");
        actualizado.setCapacidad(25);

        ClaseDTO resultado = claseService.actualizarClase(1, actualizado);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("Luis", resultado.getEntrenador());
        assertEquals("Martes: 20hs", resultado.getHorario());
        assertEquals(25, resultado.getCapacidad());
    }

    @Test
    void borrarClase_DeberiaEliminarSiExiste() {
        when(claseRepository.existsById(1)).thenReturn(true);

        boolean eliminado = claseService.borrarClase(1);

        assertTrue(eliminado);
        verify(claseRepository).deleteById(1);
    }
}

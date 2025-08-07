package com.PruebaTecnica.Energym.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.PruebaTecnica.Energym.DTO.ReservaDTO;
import com.PruebaTecnica.Energym.entities.ClaseModel;
import com.PruebaTecnica.Energym.entities.ReservaModel;
import com.PruebaTecnica.Energym.entities.UsuarioModel;
import com.PruebaTecnica.Energym.repositories.ClaseRepository;
import com.PruebaTecnica.Energym.repositories.ReservaRepository;
import com.PruebaTecnica.Energym.repositories.UsuarioRepository;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private ClaseRepository claseRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void save_DeberiaCrearReservaCorrectamente() {
        // DTO de entrada
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setClaseId(1);
        reservaDTO.setUsuarioId(2);
        reservaDTO.setFechaReserva(LocalDateTime.now());

        // Mock Clase
        ClaseModel clase = new ClaseModel();
        clase.setId(1);
        clase.setNombre("Crossfit");
        clase.setCapacidad(20);

        // Mock Usuario
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(2);
        usuario.setNombre("Juan");

        // Configuración de mocks
        when(claseRepository.findById(1)).thenReturn(Optional.of(clase));
        when(reservaRepository.cantidadReservasPorClase(1)).thenReturn(5L);
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario));
        when(reservaRepository.existsByUsuarioIdAndClaseId(2, 1))
                .thenReturn(false);

        // Simulación de reserva guardada
        ReservaModel reservaGuardada = new ReservaModel();
        reservaGuardada.setId(100);
        reservaGuardada.setClase(clase);
        reservaGuardada.setUsuario(usuario);
        reservaGuardada.setFechaReserva(reservaDTO.getFechaReserva());
        reservaGuardada.setAsistio(false);
        reservaGuardada.setCancelo(false);

        when(reservaRepository.save(any(ReservaModel.class))).thenReturn(reservaGuardada);

        // Ejecución
        ReservaDTO resultado = reservaService.save(reservaDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        assertEquals("Crossfit", resultado.getClaseNombre());
        assertEquals("Juan", resultado.getUsuarioNombre());
        assertEquals(1, resultado.getClaseId());
        assertEquals(2, resultado.getUsuarioId());
        assertEquals(reservaDTO.getFechaReserva(), resultado.getFechaReserva());
        assertFalse(resultado.isCancelo());
        assertFalse(resultado.getAsistio());

        verify(reservaRepository, times(1)).save(any(ReservaModel.class));
    }


    @Test
    void borrarReserva_DeberiaEliminarReserva() {
        when(reservaRepository.existsById(1)).thenReturn(true);

        boolean resultado = reservaService.borrarReserva(1);

        assertTrue(resultado);
        verify(reservaRepository).deleteById(1);
    }

    @Test
    void borrarReserva_DeberiaRetornarFalseSiNoExiste() {
        when(reservaRepository.existsById(1)).thenReturn(false);

        boolean resultado = reservaService.borrarReserva(1);

        assertFalse(resultado);
    }

    @Test
    void cancelarReserva_DeberiaCancelarCorrectamente() {
        ReservaModel reserva = new ReservaModel();
        reserva.setId(1);
        reserva.setAsistio(true);
        reserva.setCancelo(false);
        
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(2);
        usuario.setNombre("Juan");
        reserva.setUsuario(usuario);


        ClaseModel clase = new ClaseModel();
        clase.setId(1);
        clase.setNombre("Crossfit");
        reserva.setClase(clase);

        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(ReservaModel.class))).thenReturn(reserva);

        ReservaDTO resultado = reservaService.cancelarReserva(1);

        assertTrue(resultado.isCancelo());
        assertFalse(resultado.getAsistio());
    }

    @Test
    void marcarAsistencia_DeberiaRetornarCantidadAsistencias() {
        ReservaModel reserva = new ReservaModel();
        reserva.setId(1);
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(2);
        reserva.setUsuario(usuario);
        reserva.setAsistio(false);

        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(ReservaModel.class))).thenReturn(reserva);
        when(reservaRepository.cantidadAsistenciasMes(eq(2), any(), any())).thenReturn(3L);

        int resultado = reservaService.marcarAsistencia(1);

        assertEquals(3, resultado);
}

    @Test
    void cancelarReserva_DeberiaLanzarExcepcionSiNoExiste() {
        when(reservaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservaService.cancelarReserva(1));
    }

    @Test
    void marcarAsistencia_DeberiaLanzarExcepcionSiNoExiste() {
        when(reservaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservaService.marcarAsistencia(1));
    }



    @Test
    void getReservasPorUsuario_deberiaRetornarListaDeReservasDTO() {
        // Arrange
        int usuarioId = 1;

        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(usuarioId);
        usuario.setNombre("Juan");

        ClaseModel clase1 = new ClaseModel();
        clase1.setId(11);
        clase1.setNombre("Spinning");

        ClaseModel clase2 = new ClaseModel();
        clase2.setId(12);
        clase2.setNombre("Yoga");

        ReservaModel reserva1 = new ReservaModel();
        reserva1.setId(101);
        reserva1.setUsuario(usuario);
        reserva1.setClase(clase1);
        reserva1.setFechaReserva(LocalDateTime.now());
        reserva1.setAsistio(true);
        reserva1.setCancelo(false);

        ReservaModel reserva2 = new ReservaModel();
        reserva2.setId(102);
        reserva2.setUsuario(usuario);
        reserva2.setClase(clase2);
        reserva2.setFechaReserva(LocalDateTime.now());
        reserva2.setAsistio(false);
        reserva2.setCancelo(true);

        List<ReservaModel> reservas = List.of(reserva1, reserva2);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(reservaRepository.findByUsuarioId(usuarioId)).thenReturn(reservas);

        // Act
        List<ReservaDTO> resultado = reservaService.getReservasPorUsuario(usuarioId);

        // Assert
        assertEquals(2, resultado.size());

        ReservaDTO dto1 = resultado.get(0);
        assertEquals(101, dto1.getId());
        assertEquals(usuarioId, dto1.getUsuarioId());
        assertEquals("Juan", dto1.getUsuarioNombre());
        assertEquals(11, dto1.getClaseId());
        assertEquals("Spinning", dto1.getClaseNombre());
        assertTrue(dto1.getAsistio());
        assertFalse(dto1.isCancelo());

        ReservaDTO dto2 = resultado.get(1);
        assertEquals(102, dto2.getId());
        assertEquals(12, dto2.getClaseId());
        assertEquals("Yoga", dto2.getClaseNombre());
        assertFalse(dto2.getAsistio());
        assertTrue(dto2.isCancelo());

        verify(usuarioRepository).findById(usuarioId);
        verify(reservaRepository).findByUsuarioId(usuarioId);
    }

    @Test
    void getReservasPorUsuario_usuarioNoExiste_deberiaLanzarExcepcion() {
        int usuarioId = 999;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaService.getReservasPorUsuario(usuarioId);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository).findById(usuarioId);
        verify(reservaRepository, never()).findByUsuarioId(anyInt());
    }
}

package com.PruebaTecnica.Energym.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.PruebaTecnica.Energym.DTO.UsuarioDTO;
import com.PruebaTecnica.Energym.Exceptions.Usuario.UsuarioNotFoundException;
import com.PruebaTecnica.Energym.entities.UsuarioModel;
import com.PruebaTecnica.Energym.repositories.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_DeberiaCrearUsuario() {
        UsuarioDTO dto = new UsuarioDTO(0, "Juan", "juan@mail.com", "123456" , "Apellido");

        UsuarioModel guardado = new UsuarioModel();
        guardado.setId(1);
        guardado.setNombre("Juan");
        guardado.setEmail("juan@mail.com");
        guardado.setTelefono("123456");

        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(guardado);

        UsuarioDTO resultado = usuarioService.save(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(UsuarioModel.class));
    }

    @Test
    void getUsuarios_DeberiaDevolverLista() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1);
        usuario.setNombre("Juan");
        usuario.setEmail("juan@mail.com");
        usuario.setTelefono("123456");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioDTO> resultado = usuarioService.getUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void getUsuarioById_DeberiaDevolverUsuario() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1);
        usuario.setNombre("Juan");
        usuario.setEmail("juan@mail.com");
        usuario.setTelefono("123456");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioDTO resultado = usuarioService.getUsuarioById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void borrarUsuario_DeberiaEliminarSiExiste() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        boolean eliminado = usuarioService.borrarUsuario(1);

        assertTrue(eliminado);
        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void actualizarUsuario_DeberiaActualizarCorrectamente() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1);
        usuario.setNombre("Viejo");
        usuario.setEmail("viejo@mail.com");
        usuario.setTelefono("111");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuario);

        UsuarioDTO actualizado = new UsuarioDTO(1, "Nuevo", "nuevo@mail.com", "222" , "Apellido");

        UsuarioDTO resultado = usuarioService.actualizarUsuario(1, actualizado);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("nuevo@mail.com", resultado.getEmail());
    }
}

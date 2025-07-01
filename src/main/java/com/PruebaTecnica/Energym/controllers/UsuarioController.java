package com.PruebaTecnica.Energym.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PruebaTecnica.Energym.DTO.UsuarioDTO;
import com.PruebaTecnica.Energym.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/usuarios")
@RestController
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario en el sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        }
    )
    @PostMapping()
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve la lista de todos los usuarios",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class)))
        }
    )
    @GetMapping()
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getUsuarios());
    }

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario dado su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(
        @Parameter(description = "ID del usuario a obtener", required = true) 
        @PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getUsuarioById(id));
    }

    @Operation(summary = "Eliminar usuario por ID", description = "Elimina un usuario dado su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> borrarUsuario(
        @Parameter(description = "ID del usuario a eliminar", required = true) 
        @PathVariable int id) {
        boolean eliminado = usuarioService.borrarUsuario(id);
        if (eliminado) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se eliminó correctamente");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar usuario por ID", description = "Actualiza los datos de un usuario dado su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editarUsuario(
        @Parameter(description = "ID del usuario a actualizar", required = true) 
        @PathVariable int id, 
        @RequestBody UsuarioDTO usuarioActualizado) {
        try {
            UsuarioDTO usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

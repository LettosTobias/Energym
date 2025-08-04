package com.PruebaTecnica.Energym.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PruebaTecnica.Energym.DTO.ClaseDTO;
import com.PruebaTecnica.Energym.services.ClaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Clases", description = "Operaciones relacionadas a Clases")
@RestController
@RequestMapping("/api/clases")
public class ClaseController {

    @Autowired
    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @Operation(summary = "Crear una nueva clase")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Clase creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClaseDTO> crearClase(@RequestBody ClaseDTO claseDTO) {
        ClaseDTO nuevaClase = claseService.save(claseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaClase);
    }

    @Operation(summary = "Listar todas las clases")
    @GetMapping
    public ResponseEntity<List<ClaseDTO>> getClases() {
        
        return ResponseEntity.ok(claseService.getClases());
    }

    @Operation(summary = "Obtener clase por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clase encontrada"),
        @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClaseDTO> getClase(@PathVariable int id) {
        try {
            ClaseDTO clase = claseService.getClaseById(id);
            return ResponseEntity.ok(clase);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar una clase por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clase actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClaseDTO> actualizarClase(@PathVariable int id, @RequestBody ClaseDTO claseActualizada) {
        try {
            ClaseDTO clase = claseService.actualizarClase(id, claseActualizada);
            return ResponseEntity.ok(clase);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una clase por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clase eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> borrarClase(@PathVariable int id) {
        boolean eliminado = claseService.borrarClase(id);
        if (eliminado) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Clase eliminada correctamente");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

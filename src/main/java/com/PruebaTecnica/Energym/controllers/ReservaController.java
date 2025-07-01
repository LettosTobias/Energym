package com.PruebaTecnica.Energym.controllers;

import com.PruebaTecnica.Energym.DTO.ApiResponseDTO;
import com.PruebaTecnica.Energym.DTO.AsistenciasUsuarioDTO;
import com.PruebaTecnica.Energym.DTO.ClaseDemandaDTO;
import com.PruebaTecnica.Energym.DTO.ReservaDTO;
import com.PruebaTecnica.Energym.services.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Reservas", description = "Operaciones relacionadas con las reservas de clases")
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Crear una nueva reserva")
    @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente")
    @PostMapping()
    public ResponseEntity<ReservaDTO> crearReserva(@RequestBody ReservaDTO reserva) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reserva));
    }

    @Operation(summary = "Obtener todas las reservas")
    @GetMapping()
    public ResponseEntity<List<ReservaDTO>> getAllReservas() {
        return ResponseEntity.ok(reservaService.getAllReservas());
    }

    @Operation(summary = "Obtener historial de asistencias por usuario")
    @GetMapping("/asistencias/{usuarioId}")
    public ResponseEntity<AsistenciasUsuarioDTO> getHistorialPorUsuario(@PathVariable int usuarioId){
        return ResponseEntity.ok(reservaService.obtenerHistorialAsistencias(usuarioId));
    }

    @Operation(summary = "Obtener una reserva por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReserva(@PathVariable int id) {
        return ResponseEntity.ok(reservaService.getReservaById(id));
    }

    @Operation(summary = "Obtener listado de clases más demandadas")
    @GetMapping("/clases")
    public ResponseEntity<List<ClaseDemandaDTO>> getClasesMasDemandadas() {
        return ResponseEntity.ok(reservaService.obtenerClases());
    }

    @Operation(summary = "Obtener disponibilidad de una clase")
    @GetMapping("/disponibilidad/{claseId}")
    public ResponseEntity<ClaseDemandaDTO> getDisponiblidadClase(@PathVariable int claseId){
        return ResponseEntity.ok(reservaService.obtenerCapacidadOcupada(claseId));
    }

    @Operation(summary = "Eliminar una reserva")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> borrarReserva(@PathVariable int id) {
        boolean eliminado = reservaService.borrarReserva(id);
        if (eliminado) {
            return ResponseEntity.ok(new ApiResponseDTO("Eliminado correctamente", 200, eliminado));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO("Error al eliminar la reserva", 404, eliminado));
    }

    @Operation(summary = "Editar una reserva")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> editarReserva(@PathVariable int id, @RequestBody ReservaDTO reservaActualizada) {
        try {
            ReservaDTO reserva = reservaService.actualizarReserva(id, reservaActualizada);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Marcar asistencia a una clase")
    @PutMapping("/{id}/asistio")
    public ResponseEntity<Map<String , Integer>> marcarAsistencia(@PathVariable int id) {
        int cantidadAsistenciasMes = reservaService.marcarAsistencia(id);
        Map<String, Integer> response = new HashMap<>();
        response.put("cantidad de clases asistidas este mes", cantidadAsistenciasMes);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancelar una reserva")
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReservaDTO> cancelarReserva(@PathVariable int id) {
        ReservaDTO reservaCancelada = reservaService.cancelarReserva(id);
        return ResponseEntity.ok(reservaCancelada);
    }
}

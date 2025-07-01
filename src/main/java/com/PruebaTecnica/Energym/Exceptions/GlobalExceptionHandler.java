package com.PruebaTecnica.Energym.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.PruebaTecnica.Energym.Exceptions.Reserva.ReservaNoDisponibleException;
import com.PruebaTecnica.Energym.Exceptions.Usuario.UsuarioNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo para usuario no encontrado
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Object> handleUsuarioNoEncontrado(UsuarioNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Manejo para reserva no disponible
    @ExceptionHandler(ReservaNoDisponibleException.class)
    public ResponseEntity<Object> handleReservaNoDisponible(ReservaNoDisponibleException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Manejo genérico para cualquier otra excepción
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneral(Exception ex) {
        String mensaje = ex.getMessage() != null ? ex.getMessage() : "Ocurrió un error inesperado.";
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, mensaje);
    }

    // Método auxiliar para construir la respuesta
    private ResponseEntity<Object> buildResponse(HttpStatus status, String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", mensaje);

        return new ResponseEntity<>(response, status);
    }
}

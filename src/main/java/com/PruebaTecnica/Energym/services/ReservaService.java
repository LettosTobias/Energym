package com.PruebaTecnica.Energym.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.PruebaTecnica.Energym.DTO.AsistenciasUsuarioDTO;
import com.PruebaTecnica.Energym.DTO.ClaseDemandaDTO;
import com.PruebaTecnica.Energym.DTO.ReservaDTO;
import com.PruebaTecnica.Energym.Exceptions.Clase.ClaseNotFoundException;
import com.PruebaTecnica.Energym.Exceptions.Reserva.ReservaNoDisponibleException;
import com.PruebaTecnica.Energym.Exceptions.Usuario.UsuarioNotFoundException;
import com.PruebaTecnica.Energym.entities.ClaseModel;
import com.PruebaTecnica.Energym.entities.ReservaModel;
import com.PruebaTecnica.Energym.entities.UsuarioModel;
import com.PruebaTecnica.Energym.repositories.ClaseRepository;
import com.PruebaTecnica.Energym.repositories.ReservaRepository;
import com.PruebaTecnica.Energym.repositories.UsuarioRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClaseRepository claseRepository;
    private final UsuarioRepository usuarioRepository;
    
    public ReservaService(ReservaRepository reservaRepository, ClaseRepository claseRepository, UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.claseRepository = claseRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ReservaDTO save(ReservaDTO reservaDTO){

        ClaseModel clase = claseRepository
            .findById(reservaDTO.getClaseId())
            .orElseThrow(() -> new ClaseNotFoundException());        
        
        if(reservaRepository.cantidadReservasPorClase(reservaDTO.getClaseId()) >= clase.getCapacidad()){

            throw new ReservaNoDisponibleException("La clase no tiene cupos disponibles :( ");
        }
        UsuarioModel usuario = usuarioRepository
            .findById(reservaDTO.getUsuarioId())
            .orElseThrow(() -> new UsuarioNotFoundException());        

        if (reservaRepository.existsByUsuarioIdAndClaseId(
            usuario.getId(), clase.getId())) {
        throw new ReservaNoDisponibleException("Ya estás inscripto en esta clase ese día.");
    }

        ReservaModel reserva = new ReservaModel();
        reserva.setFechaReserva(reservaDTO.getFechaReserva());
        reserva.setClase(clase);
        reserva.setUsuario(usuario);
        ReservaModel reservaGuardada = reservaRepository.save(reserva);
        return new ReservaDTO(reservaGuardada);
    }

    public List<ReservaDTO> getAllReservas() {
        return reservaRepository.findAll().stream()
                .map(ReservaDTO::new)
                .toList();
    }

    public ReservaDTO getReservaById(int id) {
        ReservaModel reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return new ReservaDTO(reserva);
    }

    public boolean borrarReserva(int id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ReservaDTO actualizarReserva(int id, ReservaDTO reservaDTO) {
        
        ReservaModel reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reservaExistente.setFechaReserva(reservaDTO.getFechaReserva());
        reservaExistente.setAsistio(reservaDTO.getAsistio());
        reservaExistente.setCancelo(reservaDTO.isCancelo());

        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(reservaDTO.getUsuarioId());
        reservaExistente.setUsuario(usuario);

        ClaseModel clase = new ClaseModel();
        clase.setId(reservaDTO.getClaseId());
        reservaExistente.setClase(clase);

        return new ReservaDTO(reservaRepository.save(reservaExistente));
    }

    public AsistenciasUsuarioDTO obtenerHistorialAsistencias(int usuarioId) {

        UsuarioModel usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<ReservaModel> reservas = reservaRepository.clasesAsistidasPorUsuario(usuarioId);

        List<ReservaDTO> reservasDTO = reservas.stream()
                .map(ReservaDTO::new)
                .toList();

        return new AsistenciasUsuarioDTO(usuario.getId(), usuario.getNombre(), reservasDTO);
    }


    public List<ReservaDTO> getReservasPorUsuario(int usuarioId) {
        UsuarioModel usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<ReservaModel> reservas = reservaRepository.findByUsuarioId(usuarioId);

        return reservas.stream()
                .map(ReservaDTO::new)
                .toList();
    }

    public int marcarAsistencia(int reservaId) {

        ReservaModel reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new ReservaNoDisponibleException("No se encontro la reserva solicitada"));

        reserva.setAsistio(true);
        reserva.setCancelo(false);

    try {
        reservaRepository.save(reserva);
    } catch (Exception e) {
        // Loguear o lanzar excepción con detalle
        throw new RuntimeException("Error guardando asistencia: " + e.getMessage(), e);
    }

        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finMes = inicioMes.plusMonths(1).minusSeconds(1);        
        long asistencias = reservaRepository.cantidadAsistenciasMes(reserva.getUsuario().getId() , inicioMes , finMes);

    return (int) asistencias;

    }
   
    public List<ClaseDemandaDTO> obtenerClases() {
        
        List<Object[]> resultados = reservaRepository.obtenerClases();
        return resultados.stream()
            .map(obj -> new ClaseDemandaDTO((ClaseModel) obj[0], (long) obj[1]))
            .toList();
}

    public ClaseDemandaDTO obtenerCapacidadOcupada(int claseId){

        ClaseModel clase = claseRepository
            .findById(claseId)
            .orElseThrow(() -> new ClaseNotFoundException());

        long reservasConfirmadas = reservaRepository.cantidadReservasPorClase(claseId);

    return new ClaseDemandaDTO(clase, reservasConfirmadas);
    }

    public ReservaDTO cancelarReserva(int reservaId) {
        ReservaModel reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new ReservaNoDisponibleException("No se encontró la reserva solicitada"));
        reserva.setAsistio(false);
        reserva.setCancelo(true); 
        reservaRepository.save(reserva);
     return new ReservaDTO(reserva);
}
}
package com.PruebaTecnica.Energym.services;

import com.PruebaTecnica.Energym.DTO.ClaseDTO;
import com.PruebaTecnica.Energym.entities.ClaseModel;
import com.PruebaTecnica.Energym.repositories.ClaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaseService {

    
    @Autowired
    private final ClaseRepository claseRepository;


    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    public ClaseDTO save(ClaseDTO claseDTO) {
        ClaseModel clase = new ClaseModel();

        clase.setNombre(claseDTO.getNombre());
        clase.setEntrenador(claseDTO.getEntrenador());

        // Separar día y horario del string horario
        if (claseDTO.getHorario() != null && claseDTO.getHorario().contains(":")) {
            String[] partes = claseDTO.getHorario().split(":");
            clase.setDia(partes[0].trim());
            clase.setHorario(partes[1].trim());
        } else {
            clase.setDia(claseDTO.getHorario());
            clase.setHorario("");
        }

        clase.setCapacidad(claseDTO.getCapacidad());

        ClaseModel guardada = claseRepository.save(clase);
        return new ClaseDTO(guardada);
    }

    public List<ClaseDTO> getClases() {
        List<ClaseModel> clases = claseRepository.findAll();
        return clases.stream()
                .map(ClaseDTO::new)
                .collect(Collectors.toList());
    }

    public ClaseDTO getClaseById(int id) {
        ClaseModel clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + id));
        return new ClaseDTO(clase);
    }

    public ClaseDTO actualizarClase(int id, ClaseDTO claseActualizada) {
        ClaseModel clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + id));

        clase.setNombre(claseActualizada.getNombre());
        clase.setEntrenador(claseActualizada.getEntrenador());

        if (claseActualizada.getHorario() != null && claseActualizada.getHorario().contains(":")) {
            String[] partes = claseActualizada.getHorario().split(":");
            clase.setDia(partes[0].trim());
            clase.setHorario(partes[1].trim());
        } else {
            clase.setDia(claseActualizada.getHorario());
            clase.setHorario("");
        }

        clase.setCapacidad(claseActualizada.getCapacidad());

        ClaseModel actualizada = claseRepository.save(clase);
        return new ClaseDTO(actualizada);
    }

    public boolean borrarClase(int id) {
        if (claseRepository.existsById(id)) {
            claseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

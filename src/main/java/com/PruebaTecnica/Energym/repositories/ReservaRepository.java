package com.PruebaTecnica.Energym.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.PruebaTecnica.Energym.entities.ReservaModel;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaModel, Integer> {

    @Query("SELECT COUNT(r) " +
       "FROM ReservaModel r " + 
       "WHERE (r.clase.id  = :claseId) AND (r.cancelo = false) "  )
    long cantidadReservasPorClase(@Param("claseId") int claseId);


   boolean existsByUsuarioIdAndClaseId(int usuarioId, int claseId);

    @Query("SELECT r FROM ReservaModel r " +
       "WHERE r.usuario.id = :usuarioId AND r.asistio = true " +
       "ORDER BY r.fechaReserva ASC")
    List<ReservaModel> clasesAsistidasPorUsuario(@Param("usuarioId") int usuarioId);


   @Query("SELECT COUNT(r) FROM ReservaModel r " +
       "WHERE r.usuario.id = :usuarioId " +
       "AND r.asistio = true " +
       "AND r.fechaReserva BETWEEN :inicioMes AND :finMes")
   long cantidadAsistenciasMes(@Param("usuarioId") int usuarioId,
                            @Param("inicioMes") LocalDateTime inicioMes,
                            @Param("finMes") LocalDateTime finMes);


   @Query("SELECT r.clase, COUNT(r) as totalReservas " +
            "FROM ReservaModel r " +
            "GROUP BY r.clase " +
            "ORDER BY totalReservas DESC")
   List<Object[]> obtenerClases();


    @Query("SELECT r FROM ReservaModel r " +
            "WHERE r.usuario.id = :usuarioId " +
            "ORDER BY r.fechaReserva ASC")
    List<ReservaModel> findByUsuarioId(int usuarioId);

}


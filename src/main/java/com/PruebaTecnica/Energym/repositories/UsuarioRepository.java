package com.PruebaTecnica.Energym.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PruebaTecnica.Energym.entities.UsuarioModel;



@Repository
public interface  UsuarioRepository extends JpaRepository <UsuarioModel, Integer> {

    Optional<UsuarioModel> findByEmail(String email);
}

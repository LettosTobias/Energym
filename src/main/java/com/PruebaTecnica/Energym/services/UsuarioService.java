package com.PruebaTecnica.Energym.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PruebaTecnica.Energym.DTO.UsuarioDTO;
import com.PruebaTecnica.Energym.Exceptions.Usuario.UsuarioNotFoundException;
import com.PruebaTecnica.Energym.entities.UsuarioModel;
import com.PruebaTecnica.Energym.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private final UsuarioRepository usuarioRepository;
    
  
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }




    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        
        //validar los body de las request

       UsuarioModel nuevo = new UsuarioModel();
       nuevo.setEmail(usuarioDTO.getEmail());
       nuevo.setNombre(usuarioDTO.getNombre());
       nuevo.setTelefono(usuarioDTO.getTelefono());
       nuevo = usuarioRepository.save(nuevo);
       return new UsuarioDTO(nuevo);
    }

    public List<UsuarioDTO> getUsuarios(){
        return usuarioRepository.findAll()
            .stream()
            .map(UsuarioDTO::new)
            .toList();
    }
    
    public UsuarioDTO getUsuarioById(int id) {
        return usuarioRepository.findById(id)
                .map(u -> new UsuarioDTO(u.getId(), u.getNombre(), u.getEmail(), u.getTelefono()))
                .orElse(null);
    }

    public boolean borrarUsuario(int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
 
    public UsuarioDTO actualizarUsuario(int id, UsuarioDTO usuarioDTO) {
            UsuarioModel usuario = usuarioRepository
            .findById(id).orElseThrow(() -> new UsuarioNotFoundException());
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setTelefono(usuarioDTO.getTelefono());
            usuarioRepository.save(usuario);
            
            return new UsuarioDTO(usuario);
    }


}

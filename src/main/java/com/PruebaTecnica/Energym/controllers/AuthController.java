package com.PruebaTecnica.Energym.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PruebaTecnica.Energym.Auth.JwtUtil;
import com.PruebaTecnica.Energym.DTO.RegisterRequest;
import com.PruebaTecnica.Energym.entities.LoginRequest;
import com.PruebaTecnica.Energym.entities.LoginResponse;
import com.PruebaTecnica.Energym.entities.UsuarioModel;
import com.PruebaTecnica.Energym.repositories.UsuarioRepository;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UsuarioModel usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado, por favor registrese"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }

        String token = jwtUtil.generateToken(usuario);

        LoginResponse response = new LoginResponse(
                token,
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getId()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Verificar si el email ya está registrado
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El email ya está en uso");
        }

        // Crear nuevo usuario
        UsuarioModel newUser = new UsuarioModel();
        newUser.setEmail(request.getEmail());
        newUser.setNombre(request.getNombre());
        newUser.setApellido(request.getApellido());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRol("USER"); 
        newUser.setTelefono("123"); 

        usuarioRepository.save(newUser);


        return ResponseEntity.ok(newUser);

    }

}



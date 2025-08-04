package com.PruebaTecnica.Energym.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegisterRequest {
    private String email;
    private String nombre;
    private String apellido;
    private String password;

}

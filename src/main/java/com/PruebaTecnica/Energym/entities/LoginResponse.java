package com.PruebaTecnica.Energym.entities;
import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;
    private int id;
    private String email;
    private String rol;

    public LoginResponse(String token, String email, String rol , int id) {
        this.token = token;
        this.email = email;
        this.rol = rol;
        this.id = id;
    }

}

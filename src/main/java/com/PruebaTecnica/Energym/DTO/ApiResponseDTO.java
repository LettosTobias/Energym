package com.PruebaTecnica.Energym.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO {
    
    private String message;
    private int code;
    private boolean success;
}

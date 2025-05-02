package com.usuarios.NTTData.Infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank
    @Email
    private String correo;

    @NotBlank
    private String contraseña;
}

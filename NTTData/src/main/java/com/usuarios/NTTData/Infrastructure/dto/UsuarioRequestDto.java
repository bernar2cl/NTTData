package com.usuarios.NTTData.Infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsuarioRequestDto {
    @NotBlank
    private String nombre;

    @NotBlank
    @Email
    private String correo;

    @NotBlank
    private String contraseña;

    private List<TelefonoRequest> telefonos;

    @Data
    public static class TelefonoRequest {
        private String numero;
        private String codigoCiudad;
        private String codigoPais;
    }
}

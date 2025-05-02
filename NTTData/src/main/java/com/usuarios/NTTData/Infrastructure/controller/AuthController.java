package com.usuarios.NTTData.Infrastructure.controller;

import com.usuarios.NTTData.Infrastructure.security.JwtUtil;
import com.usuarios.NTTData.application.get.GetUsuarioGateway;
import com.usuarios.NTTData.application.put.PutUsuarioGateway;
import com.usuarios.NTTData.Infrastructure.db.entity.Usuario;
import com.usuarios.NTTData.Infrastructure.dto.ApiResponseDto;
import com.usuarios.NTTData.Infrastructure.dto.LoginRequestDto;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController{

    private final GetUsuarioGateway getUsuarioGateway;
    private final PutUsuarioGateway putUsuarioGateway;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> authLoginPost(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Usuario usuario = getUsuarioGateway.obtenerCorreo(loginRequestDto.getCorreo())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!loginRequestDto.getContraseña().equals(usuario.getContraseña())) {
                throw new RuntimeException("Credenciales incorrectas");
            }

            if (!usuario.isActivo()) {
                throw new RuntimeException("Usuario no se encuentra activo");
            }


            String token = JwtUtil.generarToken(usuario.getId());
            usuario.setToken(token);
            usuario.setUltimoLogin(LocalDateTime.now());
            UsuarioResponseDto usuarioResponseDto = putUsuarioGateway.actualizarToken(usuario);
            ApiResponseDto<UsuarioResponseDto> apiResponseDto = new ApiResponseDto<>(usuarioResponseDto, null);

            return ResponseEntity.status(HttpStatus.OK).body(apiResponseDto);
        } catch (RuntimeException e) {
            ApiResponseDto<UsuarioResponseDto> apiResponseDto = new ApiResponseDto<>(null, e.getMessage());
            return ResponseEntity.badRequest().body(apiResponseDto);
        }
    }
}

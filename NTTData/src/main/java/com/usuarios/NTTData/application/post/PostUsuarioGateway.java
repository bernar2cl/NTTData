package com.usuarios.NTTData.application.post;

import com.usuarios.NTTData.Infrastructure.dto.UsuarioRequestDto;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUsuarioGateway {
    private final PostUsuarioService postUsuarioService;

    public UsuarioResponseDto crearUsuario(UsuarioRequestDto request) {
        return postUsuarioService.crearUsuario(request);
    }
}

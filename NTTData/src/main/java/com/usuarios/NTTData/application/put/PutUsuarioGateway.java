package com.usuarios.NTTData.application.put;

import com.usuarios.NTTData.Infrastructure.db.entity.Usuario;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioRequestDto;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PutUsuarioGateway {
    private final PutUsuarioService putUsuarioService;

    public UsuarioResponseDto actualizarUsuario(UUID id, UsuarioRequestDto request) {
        return  putUsuarioService.actualizarUsuario(id, request);
    }

    public UsuarioResponseDto actualizarToken(Usuario usuario) {
        return putUsuarioService.actualizarToken(usuario);
    }
}

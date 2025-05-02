package com.usuarios.NTTData.application.patch;

import com.usuarios.NTTData.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatchUsuarioGateway {

    private final PatchUsuarioService patchUsuarioService;

    public UsuarioResponseDto activarUsuario(UUID id){
        return patchUsuarioService.activarUsuario(id);
    }

    public UsuarioResponseDto desactivarUsuario(UUID id){
        return patchUsuarioService.desactivarUsuario(id);
    }

}

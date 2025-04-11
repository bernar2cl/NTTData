package com.usuarios.NTTData.application.del;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DelUsuarioGateway {
    private final DelUsuarioService delUsuarioService;

    public void eliminarUsuario(UUID id) {
        delUsuarioService.eliminarUsuario(id);
    }
}

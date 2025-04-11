package com.usuarios.NTTData.application.get;

import com.usuarios.NTTData.Infrastructure.db.entity.Usuario;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUsuarioGateway {

    private final GetUsuarioService getUsuarioService;

    public Optional<UsuarioResponseListDto> obtenerUsuario(UUID id) {
        return getUsuarioService.obtenerUsuario(id);
    }

    public List<UsuarioResponseListDto> listarUsuarios() {
        return getUsuarioService.listarUsuarios();
    }

    public Optional<Usuario> obtenerCorreo(String correo){
        return getUsuarioService.obtenerCorreo(correo);
    }
}

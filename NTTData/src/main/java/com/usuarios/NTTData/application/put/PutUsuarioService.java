package com.usuarios.NTTData.application.put;

import com.usuarios.NTTData.Infrastructure.configuration.EmailProperties;
import com.usuarios.NTTData.Infrastructure.configuration.PasswordProperties;
import com.usuarios.NTTData.Infrastructure.db.entity.Telefono;
import com.usuarios.NTTData.Infrastructure.db.entity.Usuario;
import com.usuarios.NTTData.Infrastructure.db.repository.UsuarioRepository;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioRequestDto;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioResponseDto;
import com.usuarios.NTTData.application.put.Mapper.PutUsuarioMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PutUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordProperties passwordProperties;
    private final EmailProperties emailProperties;
    private final PutUsuarioMapper putUsuarioMapper;

    @Transactional
    public UsuarioResponseDto actualizarUsuario(UUID id, UsuarioRequestDto request) {
        if (!request.getContraseña().matches(passwordProperties.getRegex())) {
            throw new IllegalArgumentException("Contraseña no válida");
        }

        if (!request.getCorreo().matches(emailProperties.getRegex())) {
            throw new IllegalArgumentException("Email no válido");
        }

        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        putUsuarioMapper.actualizarUsuarioDesdeRequest(usuario, request);
        usuario.setModificado(LocalDateTime.now());

        usuarioRepository.save(usuario);
        return putUsuarioMapper.toResponse(usuario);
    }
    @Transactional
    public UsuarioResponseDto actualizarToken(Usuario usuario) {
        usuarioRepository.save(usuario);
        return putUsuarioMapper.toResponse(usuario);
    }
}

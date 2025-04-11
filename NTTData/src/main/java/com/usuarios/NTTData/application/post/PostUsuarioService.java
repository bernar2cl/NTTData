package com.usuarios.NTTData.application.post;

import com.usuarios.NTTData.Infrastructure.configuration.EmailProperties;
import com.usuarios.NTTData.Infrastructure.configuration.PasswordProperties;
import com.usuarios.NTTData.Infrastructure.security.JwtUtil;
import com.usuarios.NTTData.Infrastructure.db.entity.Telefono;
import com.usuarios.NTTData.Infrastructure.db.entity.Usuario;
import com.usuarios.NTTData.Infrastructure.db.repository.UsuarioRepository;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioRequestDto;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioResponseDto;
import com.usuarios.NTTData.application.post.mapper.PostUsuarioMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordProperties passwordProperties;
    private final EmailProperties emailProperties;
    private final PostUsuarioMapper postUsuarioMapper;

    @Transactional
    public UsuarioResponseDto crearUsuario(UsuarioRequestDto request) {
        usuarioRepository.findByCorreo(request.getCorreo()).ifPresent(u -> {
            throw new IllegalArgumentException("El correo ya está registrado");
        });

        if (!request.getContraseña().matches(passwordProperties.getRegex())) {
            throw new IllegalArgumentException("Contraseña no válida");
        }

        if (!request.getCorreo().matches(emailProperties.getRegex())) {
            throw new IllegalArgumentException("Email no válida");
        }

        Usuario usuario = postUsuarioMapper.toEntity(request, LocalDateTime.now());
        Usuario finalUsuario = usuario;
        usuario.getTelefonos().forEach(telefono -> telefono.setUsuario(finalUsuario));

        usuarioRepository.save(usuario);

        usuario.setToken(JwtUtil.generarToken(usuario.getId()));
        usuario = usuarioRepository.save(usuario);

        return postUsuarioMapper.toResponse(usuario);
    }
}

package com.usuarios.NTTData.application.del;

import com.usuarios.NTTData.Infrastructure.db.entity.Usuario;
import com.usuarios.NTTData.Infrastructure.db.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DelUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void eliminarUsuario(UUID id) {
        long totalAdmins = usuarioRepository.findAll()
                .stream()
                .filter(f -> f.getRole().equalsIgnoreCase("ADMIN"))
                .count();

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        if (totalAdmins > 1) {
            usuarioRepository.delete(usuario);
        } else {
            throw new IllegalArgumentException("No se puede eliminar el rol, ya que existe un solo ADMIN");
        }
    }
}

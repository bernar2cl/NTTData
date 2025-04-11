package com.usuarios.NTTData.Infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.usuarios.NTTData.application.get.GetUsuarioGateway;
import com.usuarios.NTTData.Infrastructure.dto.UsuarioResponseListDto;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


public class JwtAuthenticationFilter implements Filter {

    private static final String SECRET_KEY = "secreto";  // La clave secreta para la firma del token

    private final GetUsuarioGateway getUsuarioGateway;

    public JwtAuthenticationFilter(GetUsuarioGateway getUsuarioGateway) {
        this.getUsuarioGateway = getUsuarioGateway;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = extractToken(httpRequest);

        if (token != null && validateToken(token)) {
            UUID username = UUID.fromString(extractUsernameFromToken(token));
            String role = "USER";
            if(username!=null){
                Optional<UsuarioResponseListDto> existUser = getUsuarioGateway.obtenerUsuario(username);
                if(existUser.isPresent()){
                    role = existUser.get().getRole().toUpperCase();
                }
            }
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Usuario autenticado: " + username);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extrae el token sin el prefijo "Bearer "
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);  // Verifica el token
            return true;
        } catch (JWTVerificationException e) {
            return false;  // Si la verificación falla, el token es inválido
        }
    }

    private String extractUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token).getSubject();  // Extrae el usuario del token
    }

}

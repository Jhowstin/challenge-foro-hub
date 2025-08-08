package com.alurachallenge.forohub.infra.security;

import com.alurachallenge.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aquí le decimos a Spring Security dónde buscar al usuario.
        // El método findByCorreoElectronico es crucial y debes crearlo.
        return usuarioRepository.findByCorreoElectronico(username);
    }
}

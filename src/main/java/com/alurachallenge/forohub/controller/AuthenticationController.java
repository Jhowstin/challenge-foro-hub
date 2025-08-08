package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.domain.usuario.Usuario;
import com.alurachallenge.forohub.infra.security.DatosAutenticacionUsuario;
import com.alurachallenge.forohub.infra.security.DatosTokenJWT;
import com.alurachallenge.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        // Crea un objeto de autenticación con las credenciales del usuario
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.correoElectronico(),
                datosAutenticacionUsuario.contrasena()
        );

        // Intenta autenticar al usuario usando el AuthenticationManager
        var usuarioAutenticado = authenticationManager.authenticate(authenticationToken);

        // Si la autenticación es exitosa, genera un token JWT para el usuario autenticado
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        // Devuelve el token en la respuesta
        return ResponseEntity.ok(new DatosTokenJWT(jwtToken));
    }
}

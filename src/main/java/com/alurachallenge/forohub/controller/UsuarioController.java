package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.domain.perfil.Perfil;
import com.alurachallenge.forohub.domain.perfil.PerfilRepository;
import com.alurachallenge.forohub.domain.usuario.DatosRegistroUsuario;
import com.alurachallenge.forohub.domain.usuario.DatosRespuestaUsuario;
import com.alurachallenge.forohub.domain.usuario.Usuario;
import com.alurachallenge.forohub.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección de dependencia para encriptar contraseñas

    @PostMapping
    @Transactional
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datos,
                                           UriComponentsBuilder uriComponentsBuilder) {

        // 1. Busca el perfil "USUARIO" que ya debe existir en la base de datos.
        Perfil perfilUsuario = perfilRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new IllegalStateException("El perfil 'USUARIO' no existe en la base de datos."));

        // 2. Encripta la contraseña antes de crear el objeto Usuario
        String contrasenaEncriptada = passwordEncoder.encode(datos.contrasena());

        // 3. Crea el objeto Usuario a partir de los datos del DTO y la contraseña encriptada
        Usuario usuario = new Usuario(datos.nombre(), datos.correoElectronico(), contrasenaEncriptada);

        // 4. Asigna el perfil encontrado a la lista de perfiles del usuario de forma directa.
        //    No es necesario un metodo aparte si la lista ya está inicializada en la entidad.
        usuario.getPerfiles().add(perfilUsuario);

        // 5. Guarda el usuario en la base de datos.
        usuarioRepository.save(usuario);

        // 6. Retorna la respuesta con el ID del nuevo usuario.
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(url).body(new DatosRespuestaUsuario(usuario));
    }
}


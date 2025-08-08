package com.alurachallenge.forohub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    // Metodo para buscar un usuario por su correo electrónico.
    // Es utilizado por el servicio de autenticación.
    UserDetails findByCorreoElectronico(String correoElectronico);


}

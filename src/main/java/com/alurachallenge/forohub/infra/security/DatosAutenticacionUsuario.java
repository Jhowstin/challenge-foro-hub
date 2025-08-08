package com.alurachallenge.forohub.infra.security;

public record DatosAutenticacionUsuario(
        String correoElectronico,
        String contrasena
) {
}

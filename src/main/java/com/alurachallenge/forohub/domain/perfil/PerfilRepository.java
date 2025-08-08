package com.alurachallenge.forohub.domain.perfil;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    /**
     * Busca un perfil por su nombre.
     * Este método es crucial para encontrar el perfil "USUARIO" por defecto.
     *
     * @param nombre El nombre del perfil a buscar.
     * @return Un Optional que contiene el perfil si se encuentra, o un Optional vacío si no.
     */
    Optional<Perfil> findByNombre(String nombre);

}
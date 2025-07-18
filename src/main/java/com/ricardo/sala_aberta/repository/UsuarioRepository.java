package com.ricardo.sala_aberta.repository;

import com.ricardo.sala_aberta.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Override
    Optional<Usuario> findById(Long aLong);

    Optional<Usuario> findByApelido(String apelido);
}

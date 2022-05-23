package com.nebula.spring.repositorios;

import com.nebula.spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findFirstByEmail(String email);

}

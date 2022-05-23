package com.nebula.spring.repositorios;

import com.nebula.spring.model.Compra;
import com.nebula.spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByPropietario(Usuario propietario);

}

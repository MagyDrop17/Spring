package com.nebula.spring.repositorios;

import com.nebula.spring.model.Compra;
import com.nebula.spring.model.Producto;
import com.nebula.spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByPropietario(Usuario propietario);

    List<Producto> findByCompra(Compra compra);

    List<Producto> findByCompraIsNull();

    List<Producto> findByNombreContainsIgnoreCaseAndCompraIsNull(String nombre);

    List<Producto> findByNombreContainsIgnoreCaseAndPropietario(String nombre, Usuario propietario);

}

package com.nebula.spring.servicios;

import com.nebula.spring.model.Compra;
import com.nebula.spring.model.Producto;
import com.nebula.spring.model.Usuario;
import com.nebula.spring.repositorios.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraServicio {

    @Autowired
    CompraRepository repositorio;

    @Autowired
    ProductoServicio productoServicio;

    public Compra insertar(Compra c, Usuario u) {
        c.setPropietario(u);
        return repositorio.save(c);
    }

    public Compra insertar(Compra c) {
        return repositorio.save(c);
    }

    public Producto addProductoCompra(Producto p, Compra c) {
        p.setCompra(c);
        return productoServicio.editar(p);
    }

    public Compra buscarPorId(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public List<Compra> todas() {
        return repositorio.findAll();
    }

    public List<Compra> porPropietario(Usuario u) {
        return repositorio.findByPropietario(u);
    }

}

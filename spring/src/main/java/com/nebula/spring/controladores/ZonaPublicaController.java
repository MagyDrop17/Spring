package com.nebula.spring.controladores;

import com.nebula.spring.model.Producto;
import com.nebula.spring.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/public")
public class ZonaPublicaController {

    @Autowired
    ProductoServicio productoServicio;


    @ModelAttribute("productos")
    public List<Producto> productosNoVendidos() {
        return productoServicio.productosSinVender();
    }


    @GetMapping({"/", "/index"})
    public String index(Model model, @RequestParam(name="q", required=false) String query) {
        // search
        if (query != null) {

            model.addAttribute("productos", productoServicio.buscar(query));

        }
        return "index";
    }

    @GetMapping("/producto/{id}")
    public String showProduct(Model model, @PathVariable Long id) {
        Producto result = productoServicio.findById(id);
        if (result != null) {
            model.addAttribute("producto", result);
            return "producto";
        }
        return "redirect:/public";
    }


}

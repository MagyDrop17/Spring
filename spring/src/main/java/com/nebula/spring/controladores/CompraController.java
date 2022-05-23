package com.nebula.spring.controladores;

import com.nebula.spring.model.Compra;
import com.nebula.spring.model.Producto;
import com.nebula.spring.model.Usuario;
import com.nebula.spring.servicios.CompraServicio;
import com.nebula.spring.servicios.ProductoServicio;
import com.nebula.spring.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/app")
public class CompraController {

    @Autowired
    CompraServicio compraServicio;

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    HttpSession session;

    private Usuario usuario;

    @ModelAttribute("carrito")
    public List<Producto> productosCarrito() {
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        return (contenido == null) ? null : productoServicio.variosPorId(contenido);
    }

    @ModelAttribute("total_carrito")
    public Double totalCarrito() {
        List<Producto> productosCarrito = productosCarrito();
        if (productosCarrito != null) {
            return productosCarrito.stream().mapToDouble(p -> p.getPrecio()).sum();
        }
        return 0.0;
    }

    @ModelAttribute("mis_compras")
    public List<Compra> misCompras() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        usuario = usuarioServicio.buscarPorEmail(email);
        return compraServicio.porPropietario(usuario);
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        return "app/compra/carrito";
    }

    @GetMapping("/carrito/add/{id}")
    public String agregarCarrito(Model model, @PathVariable Long id) {
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        if (contenido == null) {
            contenido = new java.util.ArrayList<>();
        }
        if (!contenido.contains(id)) {
            contenido.add(id);
        }
        session.setAttribute("carrito", contenido);
        return "redirect:/app/carrito";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String borrarDeCarrito(Model model, @PathVariable Long id) {
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        if (contenido == null) {
            return "redirect:/public";
        }
        contenido.remove(id);
        if (contenido.isEmpty()) {
            session.removeAttribute("carrito");
        } else {
            session.setAttribute("carrito", contenido);
        }
        return "redirect:/app/carrito";
    }

    @GetMapping("/carrito/finalizar")
    public String checkout() {
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        if (contenido == null) {
            return "redirect:/public";
        }
        List<Producto> productos = productosCarrito();
        Compra c = compraServicio.insertar(new Compra(), usuario);

        productos.forEach(p -> compraServicio.addProductoCompra(p, c));

        session.removeAttribute("carrito");

        return "redirect:/app/compra/factura/" + c.getId();

    }

    @GetMapping("/compra/factura/{id}")
    public String factura(Model model, @PathVariable Long id) {
        Compra c = compraServicio.buscarPorId(id);
        List<Producto> productos = productoServicio.productosDeUnaCompra(c);
        model.addAttribute("productos", productos);
        model.addAttribute("compra", c);
        model.addAttribute("total_compra", productos.stream().mapToDouble(p -> p.getPrecio()).sum());
        return "app/compra/factura";
    }

    @GetMapping("/miscompras")
    public String vermisCompras(Model model) {
        return "app/compra/listado";
    }

}

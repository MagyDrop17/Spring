package com.nebula.spring;

import com.nebula.spring.model.Producto;
import com.nebula.spring.model.Usuario;
import com.nebula.spring.repositorios.ProductoRepository;
import com.nebula.spring.repositorios.UsuarioRepository;
import com.nebula.spring.servicios.ProductoServicio;
import com.nebula.spring.servicios.UsuarioServicio;
import com.nebula.spring.upload.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner initData(UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
        return args -> {

            Usuario usuario = new Usuario("Dani", "Valencia Marin", null , "dvalencia@nebula.cat", "dani");
            usuario = usuarioServicio.registrar(usuario);

            Usuario usuario2 = new Usuario("Gabi", "Bobo", null , "gfranco@nebula.cat", "gabi");
            usuario2 = usuarioServicio.registrar(usuario2);

            List<Producto> listado= Arrays.asList(
                    new Producto("Bicicleta de montaña", 100.0f,
                            "https://www.buhobike.com/c/3001-category_cover/rigidas-aluminio.jpg", usuario),
                    new Producto("Golf GTI Serie 2", 2500.0f,
                            "https://www.minicar.es/large/Volkswagen-Golf-GTi-G60-Serie-II-%281990%29-Norev-1%3A18-i22889.jpg",
                            usuario),
                    new Producto("Raqueta de tenis", 10.5f, "https://www.tennis-point.es/dw/image/v2/BBDP_PRD/on/demandware.static/-/Sites-master-catalog/default/dw620b8389/images/007/046/03423000_000.jpg?q=80&sw=243", usuario),
                    new Producto("Xbox One X", 425.0f, "https://www.nme.com/wp-content/uploads/2020/07/xbox-one-x-one-s-credit-microsoft@2000x1270-1.jpg", usuario),
                    new Producto("Trípode flexible", 10.0f, "https://www.mytrendyphone.es/images/Telesin-TE-TRP-001-Flexible-Tripod-Stand-for-Smartphone-and-Action-Camera-16042021-01-p.jpg", usuario),
                    new Producto("Iphone 7 128 GB", 350.0f, "https://m.media-amazon.com/images/I/41PWRPqW1dL._AC_SX679_.jpg", usuario)
            );

            listado.forEach(productoServicio::insertar);

        };
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };

    }

}

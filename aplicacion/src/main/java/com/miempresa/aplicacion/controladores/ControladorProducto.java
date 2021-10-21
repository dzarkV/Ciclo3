package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.modelos.Login;
import com.miempresa.aplicacion.modelos.Producto;
import com.miempresa.aplicacion.modelos.RepositorioLogin;
import com.miempresa.aplicacion.modelos.RepositorioProducto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor(onConstructor = @__(
        @Autowired))
public class ControladorProducto {

    private final RepositorioProducto repositorioProducto;
    private final RepositorioLogin repositorioLogin;

    /**
     * Metodo para encontrar producto por Id
     */
    @GetMapping("/productos/{codigoProducto}") //path del controlador
    public String getProductoById(@PathVariable String codigoProducto, Model model) {
        List<String> listaProducto = new ArrayList<>();
        listaProducto.add(codigoProducto);
        Iterable<Producto> productos = repositorioProducto.findAllById(listaProducto);
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        ArrayList<String> nom = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
            nom.add(i.getNombreUser());
        }
        Object[] codigo = cod.toArray();
        Object[] nombre = nom.toArray();
        model.addAttribute("nombre", nombre[nombre.length - 1]);
        model.addAttribute("rol", codigo[codigo.length - 1]);
        model.addAttribute("productos", productos);
        return "vistaProducto";
    }

    @GetMapping("/volverP")
    public RedirectView volver(Model model) {
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
        }
        Object[] codigo = cod.toArray();
        if (codigo[codigo.length - 1].equals("VENDEDOR")) {
            return new RedirectView("/productos", true);
        }
        return new RedirectView("/productosA");
    }

    /**
     * Método para crear producto administrador
     *
     */
    @GetMapping("/productosA") //path del controlador
    public String crearProductoAdmin(Model model) {
        Iterable<Producto> productos = repositorioProducto.findAll();
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        ArrayList<String> nom = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
            nom.add(i.getNombreUser());
        }
        Object[] codigo = cod.toArray();
        Object[] nombre = nom.toArray();
        model.addAttribute("nombre", nombre[nombre.length - 1]);
        model.addAttribute("rol", codigo[codigo.length - 1]);
        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto());
        return "vistaCrearProductoAdmin";
    }

    /**
     * Método para crear producto vendedor
     *
     */
    @GetMapping("/productos") //path del controlador
    public String crearProducto(Model model) {
        Iterable<Producto> productos = repositorioProducto.findAll();
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        ArrayList<String> nom = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
            nom.add(i.getNombreUser());
        }
        Object[] codigo = cod.toArray();
        Object[] nombre = nom.toArray();
        model.addAttribute("nombre", nombre[nombre.length - 1]);
        model.addAttribute("rol", codigo[codigo.length - 1]);
        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto());
        return "vistaCrearProducto";
    }

    /**
     * Metodo para procesar producto
     *
     */
    @PostMapping("/productos")
    public RedirectView procesarProducto(@ModelAttribute Producto producto) {
        Producto productoGuardado = repositorioProducto.save(producto);
        if (productoGuardado == null) {
            return new RedirectView("/productos", true);
        }
        return new RedirectView("/productos/" + productoGuardado.getCodProducto(), true);
    }

    /**
     * Método para actualizar producto
     *
     */
    @GetMapping("/productos/editar/{codProducto}")
    public String actualizarProducto(Producto producto, Model modelo) {
        Producto productoElegido = repositorioProducto.findByCodProducto(producto.getCodProducto());
        modelo.addAttribute("producto", productoElegido);
        return "vistaEditarProducto";
    }

    /**
     * Método para confirmar la eliminación de un producto
     *
     */
    @GetMapping("/productos/confirmar/eliminar")
    public String confirmarEliminarProducto(Producto producto, Model modelo) {
        Producto productoElegido = repositorioProducto.findByCodProducto(producto.getCodProducto());
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        ArrayList<String> nom = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
            nom.add(i.getNombreUser());
        }
        Object[] codigo = cod.toArray();
        Object[] nombre = nom.toArray();
        modelo.addAttribute("nombre", nombre[nombre.length - 1]);
        modelo.addAttribute("rol", codigo[codigo.length - 1]);
        modelo.addAttribute("producto", productoElegido);
        return "vistaConfirmarEliminarProducto";
    }

    /**
     * Método para eliminar producto
     */
    @GetMapping("/productos/eliminar")
    public String eliminarProducto(Producto producto, Model modelo) {
        try {
            repositorioProducto.delete(producto);
            return "redirect:/productosA";
        } catch (Exception e) {
            String messageRequest = e.getMessage();
            if (messageRequest.contains("could not execute statement; SQL")) {
                String respuesta = "Este producto no se puede borrar porque esta asociado a una factura";
                modelo.addAttribute("respuesta", respuesta);
                Iterable<Login> login = repositorioLogin.findAll();
                ArrayList<String> cod = new ArrayList();
                ArrayList<String> nom = new ArrayList();
                for (Login i : login) {
                    cod.add(i.getRolUser());
                    nom.add(i.getNombreUser());
                }
                Object[] codigo = cod.toArray();
                Object[] nombre = nom.toArray();
                modelo.addAttribute("nombre", nombre[nombre.length - 1]);
                modelo.addAttribute("rol", codigo[codigo.length - 1]);
                return "vistaConfirmarEliminarProducto";
            } else {
                String respuesta = "Elemento no borrado";
                modelo.addAttribute("respuesta", respuesta);
                Iterable<Login> login = repositorioLogin.findAll();
                ArrayList<String> cod = new ArrayList();
                ArrayList<String> nom = new ArrayList();
                for (Login i : login) {
                    cod.add(i.getRolUser());
                    nom.add(i.getNombreUser());
                }
                Object[] codigo = cod.toArray();
                Object[] nombre = nom.toArray();
                modelo.addAttribute("nombre", nombre[nombre.length - 1]);
                modelo.addAttribute("rol", codigo[codigo.length - 1]);
                return "vistaConfirmarEliminarProducto";
            }

        }

    }
}

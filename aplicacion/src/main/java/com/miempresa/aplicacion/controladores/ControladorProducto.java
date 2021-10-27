package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.modelos.Producto;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControladorProducto {
    
    private final RepositorioProducto repositorioProducto;
    
    @GetMapping("/productos/{codigoProducto}") //path del controlador
    public String getProductoById(@PathVariable String codigoProducto, Model model){
        List<String> listaProducto = new ArrayList<>();
        listaProducto.add(codigoProducto);
        Iterable<Producto> productos = repositorioProducto.findAllById(listaProducto);
        model.addAttribute("productos",productos);
        return "vistaProducto";
    }
 
    @GetMapping("/productos") //path del controlador
    public String crearProducto(Model model){
        Iterable<Producto> productos = repositorioProducto.findAll();
        model.addAttribute("productos",productos);
        model.addAttribute("producto",new Producto());
        return "vistaCrearProducto";
    }   
    
    @PostMapping("/productos")
    public RedirectView procesarProducto(@ModelAttribute Producto producto){
       Producto productoGuardado = repositorioProducto.save(producto);
       if (productoGuardado == null) {
            return new RedirectView("/productos", true);
        }
       return new RedirectView("/productos/"+productoGuardado.getCodProducto(),true);
    } 
    
    @GetMapping("productos/editar/{codProducto}")
    public String actualizarProducto(Producto producto, Model modelo) {
        Producto productoElegido = repositorioProducto.findByCodProducto(producto.getCodProducto());
        modelo.addAttribute("producto", productoElegido);
        return "vistaEditarProducto";
    }
    
    @GetMapping("productos/eliminar")
    public String eliminarProducto(Producto producto) {
        repositorioProducto.delete(producto);
        return "redirect:/productos";
    }

    
}

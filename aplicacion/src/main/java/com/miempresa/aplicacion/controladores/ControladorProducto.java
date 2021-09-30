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
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControladorProducto {
    
    private final RepositorioProducto repositorioProducto;
    
    @GetMapping("/productos") //path del controlador
    public String getTodosLosProductos(Model model){
        Iterable<Producto> productos = repositorioProducto.findAll();
        model.addAttribute("productos",productos);
        return "vistaProducto";
    }    
    
    @GetMapping("/productos/{codigoProducto}") //path del controlador
    public String getProductoById(@PathVariable String codigoProducto, Model model){
        List<String> listaProducto = new ArrayList<>();
        listaProducto.add(codigoProducto);
        Iterable<Producto> productos = repositorioProducto.findAllById(listaProducto);
        model.addAttribute("productos",productos);
        return "vistaProducto";
    }
    
    
}

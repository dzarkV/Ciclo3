package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControladorVendedor {
    
    private final RepositorioVendedor repositorioVendedor;
    
    @GetMapping("/vendedores") //path del controlador
    public String getTodosLosVendedores(Model model){
        List<Vendedor> vendedores = repositorioVendedor.findAll();
        model.addAttribute("vendedores",vendedores);
        return "vistaVendedor";
    }    
    
    @GetMapping("/vendedores/{codigoVendedor}") //path del controlador
    public String getVendedorById(@PathVariable String codigoVendedor, Model model){
        Vendedor vendedores = repositorioVendedor.findByCodVendedor(codigoVendedor);
        model.addAttribute("vendedores",vendedores);
        return "vistaVendedor";
    }
    
    
}

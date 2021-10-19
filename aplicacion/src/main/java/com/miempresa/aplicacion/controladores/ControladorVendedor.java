package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.modelos.Login;
import com.miempresa.aplicacion.modelos.RepositorioLogin;
import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor(onConstructor = @__(
        @Autowired))
public class ControladorVendedor {

    private final RepositorioVendedor repositorioVendedor;
    private final RepositorioLogin repositorioLogin;

    @GetMapping("/vendedores/{codigoVendedor}") //path del controlador
    public String getVendedorById(@PathVariable String codigoVendedor, Model model) {
        Vendedor vendedores = repositorioVendedor.findByCodVendedor(codigoVendedor);
        model.addAttribute("vendedores", vendedores);
        return "vistaVendedor";
    }

    @GetMapping("/vendedores") //path del controlador
    public String crearVendedor(Model model) {
        Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
        Iterable<Login> login=repositorioLogin.findAll();
        ArrayList<String> cod=new ArrayList();
        ArrayList<String> nom=new ArrayList();
        for(Login i:login){cod.add(i.getRolUser());nom.add(i.getNombreUser());}
        Object[] codigo= cod.toArray();
        Object[] nombre= nom.toArray();
        model.addAttribute("nombre", nombre[nombre.length-1]);
        model.addAttribute("rol", codigo[codigo.length-1]);
        model.addAttribute("vendedores", vendedores);
        model.addAttribute("vendedor", new Vendedor());
        return "vistaCrearVendedor";
    }


    @PostMapping("/vendedores")
    public RedirectView procesarVendedor(@ModelAttribute Vendedor vendedor) {
        
        Vendedor vendedorGuardado = repositorioVendedor.save(vendedor);
        if (vendedorGuardado == null) {
            return new RedirectView("/vendedores", true);
        }
        return new RedirectView("/vendedores/"+vendedorGuardado.getCodVendedor());
    }

}

package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.modelos.Login;
import com.miempresa.aplicacion.modelos.RepositorioLogin;
import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import java.util.ArrayList;
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
public class ControladorVendedor {

    private final RepositorioVendedor repositorioVendedor;
    private final RepositorioLogin repositorioLogin;
    
    public void mostrarVendedor(Model model){
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
    }

    @GetMapping("/volverV")
    public RedirectView volver(Model model) {
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
        }
        Object[] codigo = cod.toArray();
        if (codigo[codigo.length - 1].equals("VENDEDOR")) {
            return new RedirectView("/vendedores", true);
        }
        return new RedirectView("/vendedoresA");
    }

    @GetMapping("/vendedoresA")
    public String vendedorAdmin(Model model) {
        Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
        mostrarVendedor(model);
        model.addAttribute("vendedores", vendedores);
        model.addAttribute("vendedor", new Vendedor());
        return "vistaVendedorAdmin";
    }

    @GetMapping("/vendedores/{codigoVendedor}") //path del controlador
    public String getVendedorById(@PathVariable String codigoVendedor, Model model) {
        Vendedor vendedores = repositorioVendedor.findByCodVendedor(codigoVendedor);
        mostrarVendedor(model);
        model.addAttribute("vendedores", vendedores);
        return "vistaVendedor";
    }

    @GetMapping("/vendedores") //path del controlador
    public String crearVendedor(Model model) {
        Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
        mostrarVendedor(model);
        model.addAttribute("vendedores", vendedores);
        model.addAttribute("vendedor", new Vendedor());
        return "vistaCrearVendedor";
    }

    @GetMapping("/vendedores/editar/{codVendedor}")
    public String actualizarProducto(Vendedor vendedor, Model modelo) {
        Vendedor vend = repositorioVendedor.findByCodVendedor(vendedor.getCodVendedor());
        mostrarVendedor(modelo);
        modelo.addAttribute("vendedor", vend);
        return "vistaEditarVendedor";
    }

    @PostMapping("/vendedores")
    public RedirectView procesarVendedor(@ModelAttribute Vendedor vend) {
        Vendedor vendedorGuardado = repositorioVendedor.save(vend);
        if (vendedorGuardado == null) {
            return new RedirectView("/vendedores", true);
        }
        return new RedirectView("/vendedores/" + vendedorGuardado.getCodVendedor(), true);
    }

    @GetMapping("/vendedores/confirmar/eliminar")
    public String confirmarEliminarVendedor(Vendedor vendedor, Model modelo) {
        Vendedor productoElegido = repositorioVendedor.findByCodVendedor(vendedor.getCodVendedor());
        mostrarVendedor(modelo);
        modelo.addAttribute("vendedor", productoElegido);
        return "vistaConfirmarEliminarVendedor";
    }

    @GetMapping("/vendedores/eliminar")
    public RedirectView eliminarVendedor(Vendedor vendedor, Model modelo) {
        mostrarVendedor(modelo);
        repositorioVendedor.delete(vendedor);
        return new RedirectView("/vendedoresA", true);
    }
}

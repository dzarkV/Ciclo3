package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.dtos.LoginDto;
import com.miempresa.aplicacion.modelos.Login;
import com.miempresa.aplicacion.modelos.RepositorioLogin;
import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor(onConstructor = @__(
        @Autowired))
public class ControladorBienvenido {

    private final RepositorioVendedor repositorioVendedor;
    private final RepositorioLogin repositorioLogin;

    @GetMapping("/") //path del controlador
    public String login(Model model) {
        model.addAttribute("usuario", new LoginDto());
        return "vistaLogin";
    }

    @PostMapping("/")
    public String procesarBienvenido(@ModelAttribute LoginDto loginDto) {
        String user = loginDto.getCodVendedor();
        String password = loginDto.getPassVendedor();
        Vendedor vendedor = repositorioVendedor.findByCodVendedor(user);
        if(vendedor==null){return "vistaErrorLog";}
        String rol = vendedor.getRolVendedor();
        String nombre=vendedor.getNombreVendedor();
        Login login=new Login();
        login.setCodUser(user);
        login.setNombreUser(nombre);
        login.setPassUser(password);
        login.setRolUser(rol);
        Login save = repositorioLogin.save(login);
        if(save==null){
            System.out.println("No guardó");
        }

        if (password.equals(vendedor.getPassVendedor())) {
            System.out.println("Contraseña Correcta");
            if (rol.equals("ADMIN")) {
                return "vistaBienvenidoAdmin";
            }
        } else {
            System.out.println("Contraseña INCorrecta");
            return "vistaErrorLog";
        }
        System.out.println("Rol No Admin");
        return "vistaBienvenido";
    }
    
    @GetMapping("/salir")
    public RedirectView salirLogin(Model model){
        System.out.println("Iniciando Salida");
        repositorioLogin.deleteAll();
        System.out.println("Salida completada....Redirigiendo....");
        return new RedirectView("/",true);
    }
    
}

/*

Iterable<Login> login=repositorioLogin.findAll();
        ArrayList<String> cod=new ArrayList();
        ArrayList<String> nom=new ArrayList();
        for(Login i:login){cod.add(i.getRolUser());nom.add(i.getNombreUser());}
        Object[] codigo= cod.toArray();
        Object[] nombre= nom.toArray();
        model.addAttribute("nombre", nombre[nombre.length-1]);
        model.addAttribute("rol", codigo[codigo.length-1]);
*/

package com.miempresa.aplicacion.controladores;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControladorBienvenido {
    
    
    @GetMapping("/") //path del controlador
    public String bienvenido(){
        return "vistaBienvenido";
    }
    
}

package com.miempresa.aplicacion.modelos;

import java.util.List;
import org.springframework.data.repository.Repository;

public interface RepositorioVendedor extends Repository<Vendedor,String> {
    
    List<Vendedor> findAll();
    Vendedor findByCodVendedor(String codVendedor);
}

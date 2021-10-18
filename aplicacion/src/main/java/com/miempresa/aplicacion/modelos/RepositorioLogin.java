package com.miempresa.aplicacion.modelos;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioLogin extends CrudRepository<Login, Long>{
    Login findByCodUser(String codUser);
}

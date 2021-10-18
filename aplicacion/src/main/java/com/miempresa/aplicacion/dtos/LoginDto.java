package com.miempresa.aplicacion.dtos;

import lombok.Data;


@Data
public class LoginDto {
    private String codVendedor;
    private String passVendedor;
    private String nombreVendedor;
    private String rolVendedor;
}

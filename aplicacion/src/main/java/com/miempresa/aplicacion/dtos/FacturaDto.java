package com.miempresa.aplicacion.dtos;

import lombok.Data;


@Data
public class FacturaDto {
    private String numeroFactura;
    private String codigoProducto;
    private String fechaVenta;
    private String codigoVendedor;
    private Double valorFactura;
    
}

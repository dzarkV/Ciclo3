package com.miempresa.aplicacion.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity 
@Table(name="t_vendedores")
public class Vendedor {
    @Getter
    @Setter @Id @Column(name = "cod_vendedor") 
    private String codVendedor;
    @Getter 
    @Setter @Column(name = "nombre_vendedor")  
    private String nombreVendedor;
    @Getter 
    @Setter @Column(name = "rol_vendedor")  
    private String rolVendedor;
    @Getter 
    @Setter @Column(name = "password_vendedor")  
    private String passVendedor;

    public boolean contains(String ven) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

package com.miempresa.aplicacion.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="t_ventas")
public class Factura {
    
    @Getter
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;
    
    @Getter
    @Setter 
    @Column(name ="numero_factura")
    private String numeroFactura;
    
    @Getter    
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "cod_producto")
    private Producto producto;
    
    @Getter 
    @Setter 
    @Column(name = "fecha_venta")  
    private String fechaVenta; 
    
    @Getter
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "cod_vendedor")
    private Vendedor vendedor;
    
    @Getter
    @Setter 
    @Column(name = "valor_factura") 
    private Double valorFactura;
}

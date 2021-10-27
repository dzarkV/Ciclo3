package com.miempresa.aplicacion.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "t_login")
public class Login {
    
    @Getter 
    @Setter @Id@Column(name = "cod_vendedor")
    private String codUser;
    
    @Getter
    @Setter
    @Column(name = "nombre_vendedor")
    private String nombreUser;
    
    @Getter
    @Setter
    @Column(name = "rol_vendedor")
    private String rolUser ;
    
    @Getter
    @Setter
    @Column(name = "pass_vendedor")
    private String passUser ;
}


package com.pruebatecnica.supermercado_app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class SucursalDTO {
    
    private Long id;
    private String nombre;
    private String direccion;

    public SucursalDTO(Long id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }
   
    
    public SucursalDTO() {
    }
    
    
}

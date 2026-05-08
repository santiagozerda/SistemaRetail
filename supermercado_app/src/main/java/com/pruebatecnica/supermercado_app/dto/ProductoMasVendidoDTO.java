
package com.pruebatecnica.supermercado_app.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ProductoMasVendidoDTO {
    
    private Long idProducto;
    private String nombreProducto;
    private Long cantidadVendida;
    private LocalDate fecha;

    public ProductoMasVendidoDTO(Long idProducto, String nombreProducto, Long cantidadVendida, LocalDate fecha) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.fecha = fecha;
    }

    public ProductoMasVendidoDTO() {
    }
    
    
}

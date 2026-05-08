
package com.pruebatecnica.supermercado_app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TicketDetalleDTO {
    
    private Long idProducto;
    private String producto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double decuentoAplicado;
    private Double subTotal;

    public TicketDetalleDTO(Long idProducto, String producto, Integer cantidad, Double precioUnitario, Double decuentoAplicado, Double subTotal) {
        this.idProducto = idProducto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.decuentoAplicado = decuentoAplicado;
        this.subTotal = subTotal;
    }

   
    public TicketDetalleDTO() {
    }
    
    
}

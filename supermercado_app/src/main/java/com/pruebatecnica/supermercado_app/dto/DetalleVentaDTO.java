
package com.pruebatecnica.supermercado_app.dto;


import com.pruebatecnica.supermercado_app.model.TipoPromocion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DetalleVentaDTO {
    
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subTotal;
    
    private TipoPromocion promo;
    private Double descuAplicado;

    public DetalleVentaDTO(Long idProducto, String nombreProducto, Integer cantidad, Double precioUnitario, Double subTotal, TipoPromocion promo, Double descuAplicado) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
        this.promo = promo;
        this.descuAplicado = descuAplicado;
    }

    public DetalleVentaDTO() {
        
    }
    
}

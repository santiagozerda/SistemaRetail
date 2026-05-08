
package com.pruebatecnica.supermercado_app.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ProductoDTO {
    
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private int cantidad;
    private PromocionDTO promo;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, String nombre, String categoria, Double precio, int cantidad, PromocionDTO promo) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.promo = promo;
    }

   
    
    
}

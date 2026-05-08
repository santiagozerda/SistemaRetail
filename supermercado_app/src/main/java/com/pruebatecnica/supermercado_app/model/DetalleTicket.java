
package com.pruebatecnica.supermercado_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@Entity
public class DetalleTicket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    private Long idProducto;
    private String productoNombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subTotal;
    private Double precioFinal;
    
    private String promoAplicada;
    private Double descuAplicado;
    
    
    @ManyToOne
    @JoinColumn(name="idTicket")
    private Ticket ticket;

    public DetalleTicket(Long id, Long idProducto, String productoNombre, Integer cantidad, Double precioUnitario, Double subTotal, Double precioFinal, String promoAplicada, Double descuAplicado, Ticket ticket) {
        this.id = id;
        this.idProducto = idProducto;
        this.productoNombre = productoNombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
        this.precioFinal = precioFinal;
        this.promoAplicada = promoAplicada;
        this.descuAplicado = descuAplicado;
        this.ticket = ticket;
    }

   
    public DetalleTicket() {
    }
    
    
}

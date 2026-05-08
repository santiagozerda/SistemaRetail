package com.pruebatecnica.supermercado_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class DetalleVentas {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long idDetalle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idVenta")
    private Venta venta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idProducto")
    private Producto productos;
    private Integer cantidad;
    private double precioUnitario;
    private Double subTotal;
    private Double precioFinal;
    
    @Enumerated(EnumType.STRING)
    private TipoPromocion promo;
    private Double descuentoAplicado;

    public DetalleVentas() {
    }

    public DetalleVentas(Long idDetalle, Venta venta, Producto productos, Integer cantidad, double precioUnitario, Double subTotal, Double precioFinal, TipoPromocion promo, Double descuentoAplicado) {
        this.idDetalle = idDetalle;
        this.venta = venta;
        this.productos = productos;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
        this.precioFinal = precioFinal;
        this.promo = promo;
        this.descuentoAplicado = descuentoAplicado;
    }

}

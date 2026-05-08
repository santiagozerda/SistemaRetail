
package com.pruebatecnica.supermercado_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Builder
@Entity
public class Pago {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long idPago;
    private Double monto;
    private Double vuelto;
    private LocalDate fechaPago;
    
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;
    
    @Enumerated(EnumType.STRING)
    private EstadoPago estado;

    @ManyToOne
    @JoinColumn(name="idVenta")
    private Venta venta;

    public Pago(Long idPago, Double monto, Double vuelto, LocalDate fechaPago, MetodoPago metodoPago, EstadoPago estado, Venta venta) {
        this.idPago = idPago;
        this.monto = monto;
        this.vuelto = vuelto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.venta = venta;
    }

   
    public Pago() {
    }
    
    
}

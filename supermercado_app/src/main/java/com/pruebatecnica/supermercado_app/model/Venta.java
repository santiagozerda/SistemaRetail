package com.pruebatecnica.supermercado_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Builder
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idVenta;
    private LocalDate fechaVenta;
    private Double total;
    
    @ManyToOne
    @JoinColumn(name = "idSucursal")
    private Sucursal sucursal;
                                   //Edita todo             No deja nada sin asignar      Forza traer los detalles
    @OneToMany(mappedBy ="venta", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleVentas>detalles;

    @OneToOne(mappedBy="venta", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Ticket ticket;
    
    @OneToMany(mappedBy="venta", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Pago> pago;
    
    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;
    
    private Double totalDescuento;

    public Venta(Long idVenta, LocalDate fechaVenta, Double total, Sucursal sucursal, List<DetalleVentas> detalles, Ticket ticket, List<Pago> pago, EstadoVenta estado, Double totalDescuento) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.sucursal = sucursal;
        this.detalles = detalles;
        this.ticket = ticket;
        this.pago = pago;
        this.estado = estado;
        this.totalDescuento = totalDescuento;
    }

    
    public Venta() {
    }

}

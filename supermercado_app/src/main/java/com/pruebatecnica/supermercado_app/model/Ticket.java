
package com.pruebatecnica.supermercado_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Ticket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long idTicket;
    private String numeroTicket;
    private LocalDate fechaEmision;
    private Double total;
    private String metodoPago;
    
    @OneToOne
    @JoinColumn(name="idVenta")
    private Venta venta;
    
    @OneToMany(mappedBy="ticket", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleTicket> detalles;

    public Ticket(Long idTicket, String numeroTicket, LocalDate fechaEmision, Double total, String metodoPago, Venta venta, List<DetalleTicket> detalles) {
        this.idTicket = idTicket;
        this.numeroTicket = numeroTicket;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.metodoPago = metodoPago;
        this.venta = venta;
        this.detalles = detalles;
    }

    public Ticket() {
    }
    
    
}

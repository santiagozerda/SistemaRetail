
package com.pruebatecnica.supermercado_app.dto;



import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Builder
public class TicketDTO {
    
    private Long idTicket;
    private String numeroTicket;
    private LocalDate fecha;
    private String sucursal;
    private Double total;
    private Long idVenta;
    private List<TicketDetalleDTO> listDetalle;

    public TicketDTO(Long idTicket, String numeroTicket, LocalDate fecha, String sucursal, Double total, Long idVenta, List<TicketDetalleDTO> listDetalle) {
        this.idTicket = idTicket;
        this.numeroTicket = numeroTicket;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.total = total;
        this.idVenta = idVenta;
        this.listDetalle = listDetalle;
    }

    
    public TicketDTO() {
    }
    
    
}

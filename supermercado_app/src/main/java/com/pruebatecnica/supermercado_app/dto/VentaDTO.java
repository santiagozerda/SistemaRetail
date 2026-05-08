
package com.pruebatecnica.supermercado_app.dto;

import com.pruebatecnica.supermercado_app.model.EstadoVenta;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class VentaDTO {
    
    private Long id;
    private LocalDate fecha;
    private Long idSucursal;
    private EstadoVenta estadoVenta;
    private List<DetalleVentaDTO> listaDetalle;
    private Double total;

    public VentaDTO(Long id, LocalDate fecha, Long idSucursal, EstadoVenta estadoVenta, List<DetalleVentaDTO> listaDetalle, Double total) {
        this.id = id;
        this.fecha = fecha;
        this.idSucursal = idSucursal;
        this.estadoVenta = estadoVenta;
        this.listaDetalle = listaDetalle;
        this.total = total;
    }

    

    public VentaDTO() {
    }
    
    
    
}

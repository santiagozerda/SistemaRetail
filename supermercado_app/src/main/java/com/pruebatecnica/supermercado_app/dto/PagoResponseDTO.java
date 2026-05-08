
package com.pruebatecnica.supermercado_app.dto;

import com.pruebatecnica.supermercado_app.model.EstadoPago;
import com.pruebatecnica.supermercado_app.model.MetodoPago;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PagoResponseDTO {
    
    private Long idPago;
    private Long idVenta;
    private EstadoPago estado;
    private Double monto;
    private MetodoPago metodoPago;
    private Double vuelto;
    private LocalDate fecha;

    public PagoResponseDTO(Long idPago, Long idVenta, EstadoPago estado, Double monto, MetodoPago metodoPago, Double vuelto, LocalDate fecha) {
        this.idPago = idPago;
        this.idVenta = idVenta;
        this.estado = estado;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.vuelto = vuelto;
        this.fecha = fecha;
    }

    
    public PagoResponseDTO() {
    }
    
    
}

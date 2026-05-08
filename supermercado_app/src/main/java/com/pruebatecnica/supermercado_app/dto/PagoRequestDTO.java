
package com.pruebatecnica.supermercado_app.dto;

import com.pruebatecnica.supermercado_app.model.MetodoPago;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PagoRequestDTO {
    
    private Long ventaId;
    private MetodoPago metodoPago;
    private Double totalPagar;
    private Double montoEntregado;
    //Metodo Pago Debito
    private Double saldoDisponible;
    //Metodo Pago Credito
    private Double limiteCredito;

    public PagoRequestDTO(Long ventaId, MetodoPago metodoPago, Double totalPagar, Double montoEntregado, Double saldoDisponible, Double limiteCredito) {
        this.ventaId = ventaId;
        this.metodoPago = metodoPago;
        this.totalPagar = totalPagar;
        this.montoEntregado = montoEntregado;
        this.saldoDisponible = saldoDisponible;
        this.limiteCredito = limiteCredito;
    }

    
    public PagoRequestDTO() {
    }
    
    
}

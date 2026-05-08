
package com.pruebatecnica.supermercado_app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AsignarPromocionDTO {
    
    private Long productoId;
    private Long promocionId;

    public AsignarPromocionDTO(Long productoId, Long promocionId) {
        this.productoId = productoId;
        this.promocionId = promocionId;
    }

    public AsignarPromocionDTO() {
    }
    
    
}

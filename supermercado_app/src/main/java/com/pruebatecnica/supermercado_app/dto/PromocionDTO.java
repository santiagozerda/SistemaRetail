
package com.pruebatecnica.supermercado_app.dto;

import com.pruebatecnica.supermercado_app.model.TipoPromocion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PromocionDTO {
    
    private Long idPromocion;
    private String descripcion;
    private TipoPromocion tipo;
    private boolean activa;
    /*
    Se puede implementar a futuro
     private Double Valor
    */

    public PromocionDTO(Long idPromocion, String descripcion, TipoPromocion tipo, boolean activa) {
        this.idPromocion = idPromocion;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.activa = activa;
    }

    

    public PromocionDTO() {
    }
    
    
}


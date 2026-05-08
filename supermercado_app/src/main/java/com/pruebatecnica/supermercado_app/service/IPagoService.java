
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.PagoRequestDTO;
import com.pruebatecnica.supermercado_app.dto.PagoResponseDTO;
import java.util.List;


public interface IPagoService {
    
    public PagoResponseDTO procesarPago(PagoRequestDTO pago);
    
    public List<PagoResponseDTO> getPagos(Long ventaID);
}


package com.pruebatecnica.supermercado_app.controller;


import com.pruebatecnica.supermercado_app.dto.PagoRequestDTO;
import com.pruebatecnica.supermercado_app.dto.PagoResponseDTO;
import com.pruebatecnica.supermercado_app.model.EstadoPago;
import com.pruebatecnica.supermercado_app.service.IPagoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/pagos")
public class PagoController {
    
    @Autowired
    private IPagoService service;
    
    
    @PostMapping
    public ResponseEntity<PagoResponseDTO> registrarPago(@RequestBody PagoRequestDTO pagoDTO){
        PagoResponseDTO pago = service.procesarPago(pagoDTO);
        
        if(pago.getEstado() == EstadoPago.APROBADO){
            return ResponseEntity.ok(pago);
        }else{
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(pago);
        }
    }
    
    @GetMapping("/{ventaId}")
    public ResponseEntity<List<PagoResponseDTO>> getPagosDeVenta(@PathVariable Long ventaId){
        return ResponseEntity.ok(service.getPagos(ventaId));
    }
}

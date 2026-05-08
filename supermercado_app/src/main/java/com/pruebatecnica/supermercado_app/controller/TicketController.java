
package com.pruebatecnica.supermercado_app.controller;

import com.pruebatecnica.supermercado_app.dto.TicketDTO;
import com.pruebatecnica.supermercado_app.service.ITicketService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app/ticket")
public class TicketController {
    
    @Autowired
    private ITicketService service;
    
    @PostMapping("/venta/{ventaID}")
    public ResponseEntity<TicketDTO> createdTicket(@PathVariable Long ventaID){
        TicketDTO crear = service.createTicket(ventaID);
        return ResponseEntity.created(URI.create("/app/ticket/" + crear.getIdTicket())).body(crear);
    }
    
    @GetMapping("/{ventaID}")
    public ResponseEntity<TicketDTO> findTicketVentaId(@PathVariable Long ventaID){
        return ResponseEntity.ok(service.findTicketVenta(ventaID));
    }
}

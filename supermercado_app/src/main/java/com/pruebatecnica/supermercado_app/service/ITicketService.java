
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.TicketDTO;



public interface ITicketService {
    
    public TicketDTO createTicket(Long ventaId);
    
    public TicketDTO findTicket(Long ticketId);
    
    public TicketDTO findTicketVenta(Long ventdaId);
    
}

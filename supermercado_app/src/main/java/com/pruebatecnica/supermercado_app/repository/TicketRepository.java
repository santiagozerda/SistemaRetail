
package com.pruebatecnica.supermercado_app.repository;

import com.pruebatecnica.supermercado_app.model.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    
    //Buscamos un tick por su numero
    Optional<Ticket> findByNumeroTicket(String numeroTicket);
    
    //Busqueda de tick por venta realizada
    Optional<Ticket> findByVentaIdVenta(Long idVenta);
}

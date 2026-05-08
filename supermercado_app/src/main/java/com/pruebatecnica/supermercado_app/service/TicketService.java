
package com.pruebatecnica.supermercado_app.service;


import com.pruebatecnica.supermercado_app.dto.TicketDTO;
import com.pruebatecnica.supermercado_app.exceptions.NotFoundException;
import com.pruebatecnica.supermercado_app.mapper.Mapper;
import com.pruebatecnica.supermercado_app.model.DetalleTicket;
import com.pruebatecnica.supermercado_app.model.DetalleVentas;
import com.pruebatecnica.supermercado_app.model.EstadoVenta;
import com.pruebatecnica.supermercado_app.model.Ticket;
import com.pruebatecnica.supermercado_app.model.Venta;
import com.pruebatecnica.supermercado_app.repository.TicketRepository;
import com.pruebatecnica.supermercado_app.repository.VentaRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService implements ITicketService{

    @Autowired
    private TicketRepository ticketRepo;
    
    @Autowired
    private VentaRepository ventaRepo;
    
    @Override
    public TicketDTO createTicket(Long ventaId) {
        
        //Buscamos la venta
        Venta v = ventaRepo.findById(ventaId).orElseThrow(()-> new NotFoundException("Venta no encontrada"));
        
        //Validamos si la venta fue pagada
        if(v.getEstado() != EstadoVenta.APROBADA ) throw new RuntimeException("La venta no fue pagada");
        
        Optional<Ticket> existe = ticketRepo.findByVentaIdVenta(ventaId);
        if(existe.isPresent()){
            return Mapper.toDTO(existe.get());
        }
        
        Ticket ticket = Ticket.builder()
                .numeroTicket(generarNumeroTicket(v))
                .fechaEmision(LocalDate.now())
                .total(v.getTotal())
                .venta(v)
                .build();
        
        List<DetalleTicket> detalle = new ArrayList<>();
        
        
        for(DetalleVentas detVenta : v.getDetalles()){
            
            DetalleTicket detTicket = new DetalleTicket();
            detTicket.setTicket(ticket);
            detTicket.setIdProducto(detVenta.getProductos().getIdProductos());
            detTicket.setProductoNombre(detVenta.getProductos().getNombre());
            detTicket.setCantidad(detVenta.getCantidad());
            //snapshot de precios
            detTicket.setPrecioUnitario(detVenta.getPrecioUnitario());
            detTicket.setDescuAplicado(detVenta.getDescuentoAplicado());
            detTicket.setPrecioFinal(detVenta.getPrecioFinal());
   
            detTicket.setSubTotal(detVenta.getSubTotal());
            
            //sanpshot de promocion
            if(detVenta.getPromo()!=null){
                detTicket.setPromoAplicada(detVenta.getPromo().name());
            }
            
            detalle.add(detTicket);
            
        }
        
        ticket.setDetalles(detalle);

        ticket = ticketRepo.save(ticket);
        
        TicketDTO ticketGuardado = Mapper.toDTO(ticket);
        
        return ticketGuardado;
              
    }

    @Override
    public TicketDTO findTicket(Long ticketId) {
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(()-> new RuntimeException("Error, el ticket no existe"));
        return Mapper.toDTO(ticket);
    }

    @Override
    public TicketDTO findTicketVenta(Long ventdaId) {
        Ticket ticket = ticketRepo.findByVentaIdVenta(ventdaId)
                .orElseThrow(()-> new RuntimeException("Error, el ticket de esta venta no existe"));
        return Mapper.toDTO(ticket);
    }
    
    
    private String generarNumeroTicket(Venta venta){
        return "TCK-" + venta.getSucursal().getIdSucursal() + "-" + System.currentTimeMillis();
    }
}

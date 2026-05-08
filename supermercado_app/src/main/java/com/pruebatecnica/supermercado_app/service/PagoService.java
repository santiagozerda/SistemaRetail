
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.PagoRequestDTO;
import com.pruebatecnica.supermercado_app.dto.PagoResponseDTO;
import com.pruebatecnica.supermercado_app.mapper.Mapper;
import com.pruebatecnica.supermercado_app.model.EstadoPago;
import com.pruebatecnica.supermercado_app.model.EstadoVenta;
import com.pruebatecnica.supermercado_app.model.MetodoPago;
import com.pruebatecnica.supermercado_app.model.Pago;
import com.pruebatecnica.supermercado_app.model.Venta;
import com.pruebatecnica.supermercado_app.repository.PagoRepository;
import com.pruebatecnica.supermercado_app.repository.VentaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PagoService implements IPagoService {

    @Autowired
    private PagoRepository pagoRepo;
    
    @Autowired
    private VentaRepository ventaRepo;
    
    @Autowired
    private TicketService ticketService;
    
    @Override
    public PagoResponseDTO procesarPago(PagoRequestDTO pago) {
       
        //Validamos el pago
        if (pago == null) 
            throw new RuntimeException("El pago no puede ser nulo");
        
        if(pago.getVentaId() == null) 
            throw new RuntimeException("Debe indicar la Venta");
        
        if(pago.getTotalPagar() == null || pago.getTotalPagar() <= 0)
            throw new RuntimeException("No se puede pagar una Venta Negativa");
        
        if(pago.getMetodoPago() == MetodoPago.EFECTIVO)
            if(pago.getMontoEntregado() == null || pago.getMontoEntregado() <= 0)
                throw new RuntimeException("Ingrese el monto del cliente");
        
        //Buscamos la venta
        Venta v = ventaRepo.findById(pago.getVentaId())
                .orElseThrow(()-> new RuntimeException("Error, la venta no existe"));
  
        //Validamos el estado de la venta
        if(v.getEstado()==EstadoVenta.APROBADA)
            throw new RuntimeException("La venta ya fue pagada");
        
        if(v.getEstado() == EstadoVenta.CANCELADA)
            throw new RuntimeException("La venta esta cancelada");
        
        Pago p = Mapper.toEntity(pago, v);
        
        //Procesamos el pago
        metodoPago(p, pago);
        
        //Guardamos el pago
        p = pagoRepo.save(p);
        
        if(p.getEstado() == EstadoPago.APROBADO){
                 
            //Verificamos si la venta se pago
            verificacionVenta(v);
        }

        
        //Devolvemos los datos
        return Mapper.toDTO(p);
    }
    
    private void metodoPago(Pago pago, PagoRequestDTO request){
        
        switch(pago.getMetodoPago()){
        
            case EFECTIVO :
                if(request.getMontoEntregado() == null ||
                        request.getMontoEntregado() < pago.getMonto()){
                    pago.setEstado(EstadoPago.RECHAZADO);
                    pago.setVuelto(0.0);
                }else{
                    pago.setEstado(EstadoPago.APROBADO);
                    pago.setVuelto(request.getMontoEntregado() - request.getTotalPagar());
                }
                break;
                
            case DEBITO:
                if (request.getSaldoDisponible() == null || 
                request.getSaldoDisponible() < pago.getMonto()) {
                pago.setEstado(EstadoPago.RECHAZADO);
                }else{
                    pago.setEstado(EstadoPago.APROBADO);
                }
                break;
                
            case CREDITO:
                if(request.getLimiteCredito()== null || 
                        request.getLimiteCredito() < pago.getMonto()){
                    pago.setEstado(EstadoPago.RECHAZADO);
                
                }else{
                    pago.setEstado(EstadoPago.APROBADO);
                }
                break;
                
            default:
                pago.setEstado(EstadoPago.RECHAZADO);
        }
    
    }
 
    private void verificacionVenta(Venta venta){
        
        List<Pago> pagos = pagoRepo.findByVentaIdVenta(venta.getIdVenta());
        Double totalPagado = pagos.stream()
                .filter(p -> p.getEstado() == EstadoPago.APROBADO)
                .map(Pago::getMonto)
                .reduce(0.0, Double::sum);
        
        if(totalPagado >= venta.getTotal()){
            venta.setEstado(EstadoVenta.APROBADA);
            
            ventaRepo.save(venta);
            
            ticketService.createTicket(venta.getIdVenta());
        }
    }

    @Override
    public List<PagoResponseDTO> getPagos(Long ventaId) {
        
        if(!ventaRepo.existsById(ventaId))
                throw new RuntimeException("La venta no existe");
        
        return pagoRepo.findByVentaIdVenta(ventaId)
                .stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());
        
    }
    
}

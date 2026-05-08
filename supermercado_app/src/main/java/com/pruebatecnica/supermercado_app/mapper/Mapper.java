
package com.pruebatecnica.supermercado_app.mapper;

import com.pruebatecnica.supermercado_app.dto.DetalleVentaDTO;
import com.pruebatecnica.supermercado_app.dto.PagoRequestDTO;
import com.pruebatecnica.supermercado_app.dto.PagoResponseDTO;
import com.pruebatecnica.supermercado_app.dto.ProductoDTO;
import com.pruebatecnica.supermercado_app.dto.PromocionDTO;
import com.pruebatecnica.supermercado_app.dto.SucursalDTO;
import com.pruebatecnica.supermercado_app.dto.TicketDTO;
import com.pruebatecnica.supermercado_app.dto.TicketDetalleDTO;
import com.pruebatecnica.supermercado_app.dto.VentaDTO;
import com.pruebatecnica.supermercado_app.model.DetalleTicket;
import com.pruebatecnica.supermercado_app.model.EstadoPago;
import com.pruebatecnica.supermercado_app.model.Pago;
import com.pruebatecnica.supermercado_app.model.Producto;
import com.pruebatecnica.supermercado_app.model.Promocion;
import com.pruebatecnica.supermercado_app.model.Sucursal;
import com.pruebatecnica.supermercado_app.model.Ticket;
import com.pruebatecnica.supermercado_app.model.Venta;
import java.time.LocalDate;
import java.util.stream.Collectors;


public class Mapper {
    
    
    //Mapeo de Producto a ProductoDTO
    
    public static ProductoDTO toDTO(Producto p){
        
        if(p == null) return null;
        
        return ProductoDTO.builder()
                .id(p.getIdProductos())
                .nombre(p.getNombre())
                .categoria(p.getCategoria())
                .precio(p.getPrecio())
                .cantidad(p.getCantidad())
                .promo(toDTO(p.getPromo()))
                .build();
        
    
    }
    
    //Mapeo Promocion a DTO
    public static PromocionDTO toDTO(Promocion promo){
         if(promo == null) return null;
         
         return PromocionDTO.builder()
                .idPromocion(promo.getIdPromo())
                .descripcion(promo.getDescripcion())
                .activa(promo.isActiva())
                .tipo(promo.getTipo())
                .build();
    }
    
    //Mapeo de Sucursal a SucursalDTO
    
    public static SucursalDTO toDTO(Sucursal s){
    
        if(s == null) return null;
        
        return SucursalDTO.builder()
                .id(s.getIdSucursal())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .build();
    }
    
    
    //Mapeo de Ventas a VentasDTO
    
    public static VentaDTO toDTO(Venta v){
    
        if(v == null) return null;
        
        //Convertimos la lista DetalleVentas a DetallesVentasDTO
        var detalles = v.getDetalles().stream().map(det ->
                DetalleVentaDTO.builder()
                .idProducto(det.getProductos().getIdProductos())
                .nombreProducto(det.getProductos().getNombre())
                .cantidad(det.getCantidad())
                .precioUnitario(det.getPrecioUnitario())
                .subTotal(det.getSubTotal())
                .descuAplicado(det.getDescuentoAplicado())
                .promo(det.getPromo())
                .build()
        ).collect(Collectors.toList());
        
        return VentaDTO.builder()
                .id(v.getIdVenta())
                .fecha(v.getFechaVenta())
                .idSucursal(v.getSucursal().getIdSucursal())
                .estadoVenta(v.getEstado())
                .listaDetalle(detalles)
                .total(v.getTotal())
                .build();
                
    
    }
    
    
    public static PagoResponseDTO toDTO (Pago p){
        
        if(p == null) return null;
        
        return PagoResponseDTO.builder()
                .idPago(p.getIdPago())
                .idVenta(p.getVenta().getIdVenta())
                .metodoPago(p.getMetodoPago())
                .fecha(p.getFechaPago())
                .estado(p.getEstado())
                .monto(p.getMonto())
                .vuelto(p.getVuelto() != null ? p.getVuelto() : 0.0)
                .build();
        
    }
    
    
    public static Pago toEntity(PagoRequestDTO dto, Venta venta){
        
        if(dto == null) return null;
        
        return Pago.builder()
                .venta(venta)
                .metodoPago(dto.getMetodoPago())
                .monto(dto.getTotalPagar())
                .estado(EstadoPago.PENDIENTE)
                .fechaPago(LocalDate.now())
                .build();
    }
    
    
    public static TicketDetalleDTO detTicketToDTO(DetalleTicket dto){
        
        if(dto==null) return null;
        
        return TicketDetalleDTO.builder()
                .idProducto(dto.getIdProducto())
                .producto(dto.getProductoNombre())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .decuentoAplicado(dto.getDescuAplicado())
                .subTotal(dto.getSubTotal())
                .build();
    }
    
    
    public static TicketDTO toDTO(Ticket t){
        
        if(t==null) return null;
        
        return TicketDTO.builder()
                .idTicket(t.getIdTicket())
                .numeroTicket(t.getNumeroTicket())
                .sucursal(t.getVenta().getSucursal().getNombre())
                .fecha(t.getFechaEmision())
                .total(t.getTotal())
                .idVenta(t.getVenta().getIdVenta())
                .listDetalle(t.getDetalles().stream().map(Mapper::detTicketToDTO).collect(Collectors.toList()))
                .build();
                       
    }
}

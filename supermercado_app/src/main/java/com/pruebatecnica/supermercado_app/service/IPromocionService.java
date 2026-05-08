
package com.pruebatecnica.supermercado_app.service;


import com.pruebatecnica.supermercado_app.dto.PromocionDTO;
import com.pruebatecnica.supermercado_app.model.Producto;
import java.util.List;



public interface IPromocionService {
    
    public Double calcularTotal(Producto producto, int cantidad);
    
    public PromocionDTO createdPromocion(PromocionDTO promo);
    
    public List<PromocionDTO> getPromociones();
    
    public PromocionDTO findPromocion(Long id);
    
    public PromocionDTO editPromo(Long id, PromocionDTO promo);
    
    public void deletePromo(Long id);
}

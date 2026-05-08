
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.PromocionDTO;
import com.pruebatecnica.supermercado_app.exceptions.NotFoundException;
import com.pruebatecnica.supermercado_app.mapper.Mapper;
import com.pruebatecnica.supermercado_app.model.Producto;
import com.pruebatecnica.supermercado_app.model.Promocion;
import com.pruebatecnica.supermercado_app.repository.PromocionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PromocionService  implements IPromocionService{

    @Autowired
    private PromocionRepository promoRepo;
    
    
    @Override
    public Double calcularTotal(Producto producto, int cantidad) {
        
        if(cantidad <= 0)
            throw new NotFoundException("La cantidad debe ser mayor a 0");
        
        if(producto.getPromo() == null || !producto.getPromo().isActiva()){
            return producto.getPrecio() * cantidad;
        }
        
        /*
        Obtenemos el tipo de promociones diponibles y
        elegimos que promocion se aplicara
        */
        switch(producto.getPromo().getTipo()){
            case TRES_POR_DOS:
                return calcular3x2(producto , cantidad);
                
            case SEGUNDA_UNIDAD_50:
                return calcularSegundaUnidad(producto, cantidad);
                
            default:
                return producto.getPrecio() * cantidad;
        }
        
    }
    
    
    private double calcularSegundaUnidad(Producto producto, int cantidad) {

        double precio = producto.getPrecio();

        if(cantidad % 2 != 0){
            return cantidad * precio;
        }
        
        int pares = cantidad / 2;
        return pares * (precio + precio * 0.5);

    }
    
    
    private double calcular3x2(Producto producto, int cantidad){
    
        // Promociones 3x2
        double precio = producto.getPrecio();
        
        if(cantidad % 3 != 0){
            return cantidad * precio;
        }
        
        int promo = cantidad / 3;
        
        return promo * 2 * precio;
    }


    @Override
    public PromocionDTO createdPromocion(PromocionDTO promo) {
        
        //Validamos que la promocion sea obligatoria
        if(promo.getTipo() == null) throw new RuntimeException("Tiene que ser una promocion existente");
        
        Promocion promocion = Promocion.builder()
                .tipo(promo.getTipo())
                .descripcion(promo.getDescripcion())
                .activa(promo.isActiva())
                .build();
                
        return Mapper.toDTO(promoRepo.save(promocion));
    }

    @Override
    public List<PromocionDTO> getPromociones() {
        return promoRepo.findAll().stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public PromocionDTO findPromocion(Long id) {
        Promocion promo = promoRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No existe esta Promocion"));
        return Mapper.toDTO(promo);
    }

    @Override
    public PromocionDTO editPromo(Long id, PromocionDTO promo) {
        
        Promocion promocion = promoRepo.findById(id)
                .orElseThrow(()-> new RuntimeException ("Promocion no encontrada"));
        
        promocion.setTipo(promo.getTipo());
        promocion.setDescripcion(promo.getDescripcion());
        promocion.setActiva(promo.isActiva());
        
        
        return Mapper.toDTO(promoRepo.save(promocion));
    }

    @Override
    public void deletePromo(Long id) {
        
        if(!promoRepo.existsById(id)){
            throw new NotFoundException("No se puede borrar una Promocion que no existe");
        }
        
        promoRepo.deleteById(id);
    }
}

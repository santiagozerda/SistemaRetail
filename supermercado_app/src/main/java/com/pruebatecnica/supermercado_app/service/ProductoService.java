
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.AsignarPromocionDTO;
import com.pruebatecnica.supermercado_app.dto.ProductoDTO;
import com.pruebatecnica.supermercado_app.mapper.Mapper;
import com.pruebatecnica.supermercado_app.model.Producto;
import com.pruebatecnica.supermercado_app.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pruebatecnica.supermercado_app.exceptions.NotFoundException;
import com.pruebatecnica.supermercado_app.model.Promocion;
import com.pruebatecnica.supermercado_app.repository.PromocionRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository producRepo;
    
    @Autowired
    private PromocionRepository promoRepo;
    
    @Override
    public List<ProductoDTO> getProductos() {
        return producRepo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO saveProducto(ProductoDTO producDTO) {
        
        //Buscamos y validamos las promociones disponibles
        Promocion promo = producDTO.getPromo() != null
                ? obtenerPromo(producDTO.getPromo().getIdPromocion())
                : null;
        
        //Con este metedo convertimos los productosDTO a producto y asignamos una promocion
        Producto produc = Producto.builder()
                .nombre(producDTO.getNombre())
                .precio(producDTO.getPrecio())
                .categoria(producDTO.getCategoria())
                .cantidad(producDTO.getCantidad())
                .promo(promo)
                .build();
        
        //mostramos el producto que guardamos
        return Mapper.toDTO(producRepo.save(produc));
    }

    @Override
    public void deleteProducto(Long id) {
        //Validamos si existe el id en cuestion
        if(!producRepo.existsById(id)){
            throw new NotFoundException("No se puede elimnar porque no existe el Producto");
        }
        producRepo.deleteById(id); 
    }

    @Override
    public ProductoDTO editProducto(Long id, ProductoDTO producDTO) {
        
        //Buscamos por el id si existe el producto
        Producto produc = producRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        
        //Editamos un producto seleccionado y mostramos el resultado de como quedo
        produc.setNombre(producDTO.getNombre());
        produc.setCategoria(producDTO.getCategoria());
        produc.setCantidad(producDTO.getCantidad());
        produc.setPrecio(producDTO.getPrecio());
        
        if(producDTO.getPromo() != null){
           produc.setPromo(obtenerPromo(producDTO.getPromo().getIdPromocion()));
        }else{
            produc.setPromo(null);
        }
        
        return Mapper.toDTO(producRepo.save(produc));
    }

    
    //Podemos asignar una promocion a un producto a futuro
    @Override
    public ProductoDTO asignarPromocion(AsignarPromocionDTO promoDTO) {
        
        Producto producto = producRepo.findById(promoDTO.getProductoId())
                .orElseThrow(()-> new NotFoundException("Producto no encontrado"));
        
        producto.setPromo(obtenerPromo(promoDTO.getPromocionId()));
        
        return Mapper.toDTO(producRepo.save(producto));
    }
    
    
    private Promocion obtenerPromo(Long idPromo){
       return promoRepo.findById(idPromo)
                .orElseThrow(() -> new NotFoundException("Promocion no encontrada"));

    }
}

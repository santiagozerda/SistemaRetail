
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.AsignarPromocionDTO;
import com.pruebatecnica.supermercado_app.dto.ProductoDTO;
import java.util.List;


public interface IProductoService {
    
    public List<ProductoDTO> getProductos();
    
    public ProductoDTO saveProducto(ProductoDTO producDTO);
    
    public void deleteProducto(Long id);
    
    public ProductoDTO editProducto(Long id, ProductoDTO producDTO);
    
    public ProductoDTO asignarPromocion(AsignarPromocionDTO promoDTO);
}

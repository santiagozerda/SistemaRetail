
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.ProductoMasVendidoDTO;
import com.pruebatecnica.supermercado_app.dto.VentaDTO;
import java.util.List;


public interface IVentaService {
    
    public List<VentaDTO> getVentas();
    
    public VentaDTO saveVenta(VentaDTO ventaDTO);
    
    public void deleteVenta(Long id);
    
    public VentaDTO editVenta(Long id, VentaDTO ventaDTO);
    
    public ProductoMasVendidoDTO productoMasVendido();
    
}

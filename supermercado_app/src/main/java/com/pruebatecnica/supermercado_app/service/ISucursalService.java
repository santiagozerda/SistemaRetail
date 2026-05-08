
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.SucursalDTO;
import java.util.List;

public interface ISucursalService {
    
    public List<SucursalDTO> getSucursales();
    
    public SucursalDTO saveSucursal(SucursalDTO sucuDTO);
    
    public void deleteSucursal(Long id);
    
    public SucursalDTO editSucursal(Long id, SucursalDTO sucuDTO);
}

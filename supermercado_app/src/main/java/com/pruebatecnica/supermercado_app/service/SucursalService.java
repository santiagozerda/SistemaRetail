
package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.SucursalDTO;
import com.pruebatecnica.supermercado_app.exceptions.NotFoundException;
import com.pruebatecnica.supermercado_app.mapper.Mapper;
import com.pruebatecnica.supermercado_app.model.Sucursal;
import com.pruebatecnica.supermercado_app.repository.SucursalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SucursalService implements ISucursalService{

    @Autowired
    private SucursalRepository sucuRepo;
    
    @Override
    public List<SucursalDTO> getSucursales() {
        return sucuRepo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public SucursalDTO saveSucursal(SucursalDTO sucuDTO) {

       //Creamos una sucursal
        Sucursal sucu = Sucursal.builder()
                .nombre(sucuDTO.getNombre())
                .direccion(sucuDTO.getDireccion())
                .build();
        //Devolvemos y mostramos los datos de la sucursal creada
        return Mapper.toDTO(sucuRepo.save(sucu));
        
    }

    @Override
    public void deleteSucursal(Long id) {
        //Validamos si existe el id
        if(!sucuRepo.existsById(id)){
            throw new NotFoundException("No se puede eliminar porque no existe la Sucursal");
        }
        sucuRepo.deleteById(id);
    }

    @Override
    public SucursalDTO editSucursal(Long id, SucursalDTO sucuDTO) {
        //Vemos si el id existe o no
        Sucursal sucu = sucuRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
        
        //Seteamos los datos de la sucursal
        sucu.setNombre(sucuDTO.getNombre());
        sucu.setDireccion(sucuDTO.getDireccion());
        
        //Devolvemos los datos que actualizamos
        return Mapper.toDTO(sucuRepo.save(sucu));
    }
    
}

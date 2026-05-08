package com.pruebatecnica.supermercado_app.controller;

import com.pruebatecnica.supermercado_app.dto.SucursalDTO;
import com.pruebatecnica.supermercado_app.service.ISucursalService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/sucursal")
public class SucursalController {
    
    @Autowired
    private ISucursalService service;
    
    @GetMapping
    public ResponseEntity<List<SucursalDTO>> getSucursales(){
        return ResponseEntity.ok(service.getSucursales());
    }
    
    @PostMapping
    public ResponseEntity<SucursalDTO> createSucursal(@RequestBody SucursalDTO sucuDTO){
        SucursalDTO crear = service.saveSucursal(sucuDTO);
        return ResponseEntity.created(URI.create("/app/sucursal" + crear.getId())).body(crear);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> editSucursal(@PathVariable Long id, @RequestBody SucursalDTO sucuDTO){
        return ResponseEntity.ok(service.editSucursal(id, sucuDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id){
        service.deleteSucursal(id);
        return ResponseEntity.noContent().build();
    }
}

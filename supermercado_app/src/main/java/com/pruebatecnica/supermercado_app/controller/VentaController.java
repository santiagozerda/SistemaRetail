
package com.pruebatecnica.supermercado_app.controller;


import com.pruebatecnica.supermercado_app.dto.ProductoMasVendidoDTO;
import com.pruebatecnica.supermercado_app.dto.VentaDTO;
import com.pruebatecnica.supermercado_app.service.IVentaService;
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
@RequestMapping("/app/venta")
public class VentaController {
    
    @Autowired
    private IVentaService service;
    
    @GetMapping
    public ResponseEntity<List<VentaDTO>> getVentas(){
        return ResponseEntity.ok(service.getVentas());
    }
    
    @PostMapping
    public ResponseEntity<VentaDTO> createdVenta(@RequestBody VentaDTO venDTO){
        VentaDTO crear = service.saveVenta(venDTO);
        return ResponseEntity.created(URI.create("/app/venta/"+ crear.getId())).body(crear);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> editVenta(@PathVariable Long id, @RequestBody VentaDTO venDTO){
        return ResponseEntity.ok(service.editVenta(id, venDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id){
        service.deleteVenta(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/estadistica/producto-mas-vendido")
    public ResponseEntity<ProductoMasVendidoDTO> productoMasVendido(){
        ProductoMasVendidoDTO estadistica = service.productoMasVendido();
        return ResponseEntity.ok(estadistica);
    }

}

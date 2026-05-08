
package com.pruebatecnica.supermercado_app.controller;

import com.pruebatecnica.supermercado_app.dto.AsignarPromocionDTO;
import com.pruebatecnica.supermercado_app.dto.ProductoDTO;

import com.pruebatecnica.supermercado_app.service.IProductoService;
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
@RequestMapping("/app/productos")
public class ProductoController {
    
    @Autowired
    private IProductoService service;
    
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getProductos(){
        return ResponseEntity.ok(service.getProductos());
    }
    
    @PostMapping
    public ResponseEntity<ProductoDTO> createdProducto(@RequestBody ProductoDTO prodDTO){
        ProductoDTO crear = service.saveProducto(prodDTO);
        return ResponseEntity.created(URI.create("app/productos/" + crear.getId())).body(crear);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> editProducto(@PathVariable Long id, @RequestBody ProductoDTO prodDTO){
        return ResponseEntity.ok(service.editProducto(id, prodDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id){
        service.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/productos/{productoId}/promo/{promoId}")
    public ResponseEntity<ProductoDTO> asignarPromocion(@PathVariable Long productoId, @PathVariable Long promocionId){
        ProductoDTO actualizar = service.asignarPromocion(new AsignarPromocionDTO(productoId, promocionId));
        return ResponseEntity.ok(actualizar);
    }
}

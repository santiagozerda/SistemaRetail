
package com.pruebatecnica.supermercado_app.repository;

import com.pruebatecnica.supermercado_app.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long>{
    
}


package com.pruebatecnica.supermercado_app.repository;


import com.pruebatecnica.supermercado_app.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}


package com.pruebatecnica.supermercado_app.repository;

import com.pruebatecnica.supermercado_app.model.Producto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.promo WHERE p.id = :id")
    Optional<Producto> findByIdWithPromo(@Param("id") Long id);
}


package com.pruebatecnica.supermercado_app.repository;

import com.pruebatecnica.supermercado_app.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    
}

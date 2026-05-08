
package com.pruebatecnica.supermercado_app.repository;

import com.pruebatecnica.supermercado_app.model.Pago;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{
    
    //Buscar el Id de la venta
    List<Pago> findByVentaIdVenta(Long idVenta);
    
}

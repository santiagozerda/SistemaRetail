
package com.pruebatecnica.supermercado_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Builder
@Entity
public class Promocion {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long idPromo;
    private String descripcion;
    
    /*
    Esto se puede utilizar a futuro para hacer una promocion de descuentos a productos, de ser asi tendriamos que 
    poner una nueva opcion en el ENUM de TipoPromocion
    
    private Double valor;
    */

    private boolean activa;

    //Utilizamos Enumerated para facilitar la lectura en las tablas
    @Enumerated(EnumType.STRING)
    private TipoPromocion tipo;

    @OneToMany(mappedBy="promo", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Producto> productos;

    public Promocion(Long idPromo, String descripcion, boolean activa, TipoPromocion tipo, List<Producto> productos) {
        this.idPromo = idPromo;
        this.descripcion = descripcion;
        this.activa = activa;
        this.tipo = tipo;
        this.productos = productos;
    }

    
    public Promocion() {
    }
    
    
}

package com.pruebatecnica.supermercado_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter @Builder
@Entity
public class Sucursal {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idSucursal;
    private String nombre;
    private String direccion;
    
    @OneToMany(mappedBy="sucursal", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Venta> ventas;

    public Sucursal() {
    }

    public Sucursal(Long idSucursal, String nombre, String direccion, List<Venta> ventas) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ventas = ventas;
    }

}

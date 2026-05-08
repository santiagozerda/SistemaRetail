package com.pruebatecnica.supermercado_app.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Builder
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idProductos;
    private String nombre;
    private Double precio;
    private String categoria;
    private int cantidad;
    
    @ManyToOne
    @JoinColumn(name="idPromo")
    private Promocion promo;
    
    @OneToMany(mappedBy="productos", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleVentas> detalles;

    public Producto() {
    }

    public Producto(Long idProductos, String nombre, Double precio, String categoria, int cantidad, Promocion promo, List<DetalleVentas> detalles) {
        this.idProductos = idProductos;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.promo = promo;
        this.detalles = detalles;
    }

   
}

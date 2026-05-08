
package com.pruebatecnica.supermercado_app.exceptions;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException (String msje){
    
        //Devuelve la clase que contiene el mjs
        super(msje);
    }
    
}

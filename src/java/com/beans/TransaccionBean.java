/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.controllers.TransaccionesJpaController;
import com.entities.Transacciones;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author juandiego
 */
@ManagedBean
@RequestScoped
public class TransaccionBean {
        
    private static List<Transacciones> transacciones;
    
    private TransaccionesJpaController controlador;
    
    /**
     * Creates a new instance of VehiculoBean
     */
    public TransaccionBean() {
        transacciones= new ArrayList<>();
        controlador = new TransaccionesJpaController();
        List<Transacciones> lista = controlador.findTransaccionesEntities();
        for(Transacciones t: lista){
            transacciones.add(t);
        }
    }
}

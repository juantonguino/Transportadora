/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.controllers.VehiculoJpaController;
import com.controllers.exceptions.IllegalOrphanException;
import com.controllers.exceptions.NonexistentEntityException;
import com.entities.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author juandiego
 */
@ManagedBean
@RequestScoped
public class VehiculoBean {

    private Vehiculo vehiculoModificar;
    
    private Vehiculo vehiculoAgregar;
    
    private static List<Vehiculo> vehiculos;
    
    private VehiculoJpaController controlador;
    
    /**
     * Creates a new instance of VehiculoBean
     */
    public VehiculoBean() {
        vehiculoModificar= new Vehiculo("", "", "", 0, "", 0);
        vehiculoAgregar= new Vehiculo("", "", "", 0, "", 0);
        vehiculos= new ArrayList<>();
        controlador = new VehiculoJpaController();
        List<Vehiculo> lista = controlador.findVehiculoEntities();
        for(Vehiculo v: lista){
            vehiculos.add(v);
        }
    }

    public Vehiculo getVehiculoModificar() {
        return vehiculoModificar;
    }

    public void setVehiculoModificar(Vehiculo vehiculoModificar) {
        this.vehiculoModificar = vehiculoModificar;
    }

    public Vehiculo getVehiculoAgregar() {
        return vehiculoAgregar;
    }

    public void setVehiculoAgregar(Vehiculo vehiculoAgregar) {
        this.vehiculoAgregar = vehiculoAgregar;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        VehiculoBean.vehiculos = vehiculos;
    }
    
    public void verServicios(Vehiculo v){
        String retorno= "servicio.xhtml?placa="+v.getPlaca();
        try{
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect(retorno);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminarVehiculo(Vehiculo v) throws IllegalOrphanException, NonexistentEntityException{
        controlador.destroy(v.getPlaca());
        try{
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("vehiculo.xhtml");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

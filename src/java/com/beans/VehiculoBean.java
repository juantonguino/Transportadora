/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.controllers.ServicioJpaController;
import com.controllers.SucursalJpaController;
import com.controllers.TransaccionesJpaController;
import com.controllers.VehiculoJpaController;
import com.controllers.exceptions.IllegalOrphanException;
import com.controllers.exceptions.NonexistentEntityException;
import com.entities.Sucursal;
import com.entities.Transacciones;
import com.entities.Vehiculo;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
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
    
    private Vehiculo vehiculoTransferir;
    
    private Sucursal sucursalSeleccionada;
    
    private static List<Vehiculo> vehiculos;
    
    private static List<Sucursal> sucursales;
    
    private VehiculoJpaController controlador;
    
    /**
     * Creates a new instance of VehiculoBean
     */
    public VehiculoBean() {
        vehiculoModificar= new Vehiculo("", "", "", 0, "", 0);
        vehiculoAgregar= new Vehiculo("", "", "", 0, "", 0);
        vehiculoTransferir= new Vehiculo("", "", "", 0, "", 0);
        sucursalSeleccionada=new Sucursal(0, "");
        vehiculos= new ArrayList<>();
        sucursales=new ArrayList<>();
        controlador = new VehiculoJpaController();
        List<Vehiculo> lista = controlador.findVehiculoEntities();
        SucursalJpaController sucursalC= new SucursalJpaController();
        List<Sucursal> sucursalesSeleccion=sucursalC.findSucursalEntities();
        for(Vehiculo v: lista){
            if(v.getSucursalId().getId()==SucursalBean.ID_SUCURSAL){
                vehiculos.add(v);
            }
        }
        for(Sucursal s:sucursalesSeleccion){
            sucursales.add(s);
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
        this.vehiculos = vehiculos;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public Sucursal getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    public void setSucursalSeleccionada(Sucursal sucursalSeleccionada) {
        this.sucursalSeleccionada = sucursalSeleccionada;
    }

    public Vehiculo getVehiculoTransferir() {
        return vehiculoTransferir;
    }

    public void setVehiculoTransferir(Vehiculo vehiculoTransferir) {
        this.vehiculoTransferir = vehiculoTransferir;
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
    
    public void eliminarVehiculo(Vehiculo v){
        try{
            controlador.destroy(v.getPlaca());
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("vehiculo.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede eliminar Vehiculo"));
        }
    }
    
    public void agregar(){
        try{
            SucursalJpaController sucursalControl = new SucursalJpaController();
            Sucursal s=sucursalControl.findSucursal(SucursalBean.ID_SUCURSAL);
            vehiculoAgregar.setSucursalId(s);
            controlador.create(vehiculoAgregar);
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("vehiculo.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede agregar Vehiculo"));
        }
    }
    
    public void modificar(){
        try{
            SucursalJpaController sucursalControl = new SucursalJpaController();
            Sucursal s=sucursalControl.findSucursal(SucursalBean.ID_SUCURSAL);
            vehiculoModificar.setSucursalId(s);
            vehiculoModificar.setTransaccionesList(new ArrayList<>());
            vehiculoModificar.setServicioList(new ArrayList<>());
            controlador.edit(vehiculoModificar);
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("vehiculo.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede modificar Vehiculo"));
        }
    }
    
    public void transferir(){
        vehiculoTransferir.setSucursalId(sucursalSeleccionada);
        try{
            SucursalJpaController sucursalControl = new SucursalJpaController();
            Sucursal s=sucursalControl.findSucursal(sucursalSeleccionada.getId());
            Vehiculo v = controlador.findVehiculo(vehiculoTransferir.getPlaca());
            v.setSucursalId(s);
            controlador.edit(v);
            
            Transacciones transaccionRealizada= new Transacciones(0, new Date());
            transaccionRealizada.setSucursalId(s);
            transaccionRealizada.setVehiculoPlaca(v);
            TransaccionesJpaController controlTansacciones = new TransaccionesJpaController();
            controlTansacciones.create(transaccionRealizada);
            
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("vehiculo.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede transferir Vehiculo"));
        }
    }
}
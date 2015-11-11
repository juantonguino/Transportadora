/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.controllers.ServicioJpaController;
import com.controllers.VehiculoJpaController;
import com.entities.Servicio;
import com.entities.Vehiculo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author juandiego
 */
@ManagedBean
@RequestScoped
public class ServicioBean {
    
    private Servicio servicioModificar;
    
    private Servicio servicioAgregar;
    
    private static List<Servicio> servicios;
    
    private ServicioJpaController controlador;
    
    private static String placa;
    
    /**
     * Creates a new instance of VehiculoBean
     */
    public ServicioBean() {
        servicioModificar= new Servicio(0, "", new Date(), new Date(), new Date(), new Date(), "");
        servicioAgregar= new Servicio(0, "", new Date(), new Date(), new Date(), new Date(), "");
        String temp = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("placa");
        if(temp!=null){
            placa= temp;
        }
        servicios= new ArrayList<>();
        controlador = new ServicioJpaController();
        List<Servicio> lista = controlador.findServicioEntities();
        for(Servicio s: lista){
            if(s.getVehiculoPlaca().getPlaca().equals(placa)){
                servicios.add(s);
            }
        }
    }

    public Servicio getServicioModificar() {
        return servicioModificar;
    }

    public void setServicioModificar(Servicio servicioModificar) {
        this.servicioModificar = servicioModificar;
    }

    public Servicio getServicioAgregar() {
        return servicioAgregar;
    }

    public void setServicioAgregar(Servicio servicioAgregar) {
        this.servicioAgregar = servicioAgregar;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        ServicioBean.servicios = servicios;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public void agregarServicio(){
        try{
            VehiculoJpaController controladorvehiculo = new VehiculoJpaController();
            Vehiculo v = controladorvehiculo.findVehiculo(placa);
            servicioAgregar.setVehiculoPlaca(v);
            controlador.create(servicioAgregar);
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("servicio.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede agregar Servicio"));
        }
    }
    
    public void elminarServicio(Servicio servicio){
        try{
            controlador.destroy(servicio.getId());
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("servicio.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede eliminar Servicio"));
        }
    }
    
    public void modificarServicios(){
        try{
            VehiculoJpaController controladorvehiculo = new VehiculoJpaController();
            Vehiculo v = controladorvehiculo.findVehiculo(placa);
            servicioModificar.setVehiculoPlaca(v);
            controlador.edit(servicioModificar);
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("servicio.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede Modificar Servicio"));
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.controllers.ServicioJpaController;
import com.entities.Servicio;
import java.util.ArrayList;
import java.util.List;
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
    
    private String placa;
    
    /**
     * Creates a new instance of VehiculoBean
     */
    public ServicioBean() {
        servicioModificar= new Servicio(0, "", null, null, null, null, "");
        servicioAgregar= new Servicio(0, "", null, null, null, null, "");
        placa=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("placa");
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
        
    }
    
    public void elminarServicio(Servicio servicio){
        
    }
    
    public void modificarServicios(){
        
    }
}
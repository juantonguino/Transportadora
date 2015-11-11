/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.controllers.ClienteJpaController;
import com.controllers.SucursalJpaController;
import com.entities.Cliente;
import com.entities.Sucursal;
import java.util.ArrayList;
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
public class ClienteBean {
    
    private Cliente clienteModificar;
    
    private Cliente clienteAgregar;
    
    private static List<Cliente> clientes;
    
    private ClienteJpaController controlador;
    
    /**
     * Creates a new instance of VehiculoBean
     */
    public ClienteBean() {
        clienteModificar= new Cliente(0, "", "", "", 0);
        clienteAgregar= new Cliente(0, "", "", "", 0);
        clientes= new ArrayList<>();
        controlador = new ClienteJpaController();
        List<Cliente> lista = controlador.findClienteEntities();
        for(Cliente c: lista){
            if(c.getSucursalId().getId()==SucursalBean.ID_SUCURSAL){
                clientes.add(c);
            }
        }
    }

    public Cliente getClienteModificar() {
        return clienteModificar;
    }

    public void setClienteModificar(Cliente clienteModificar) {
        this.clienteModificar = clienteModificar;
    }

    public Cliente getClienteAgregar() {
        return clienteAgregar;
    }

    public void setClienteAgregar(Cliente clienteAgregar) {
        this.clienteAgregar = clienteAgregar;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        ClienteBean.clientes = clientes;
    }
    
    public void agregarCliente(){
        try{
            SucursalJpaController controladorSucursal = new SucursalJpaController();
            Sucursal sucursal= controladorSucursal.findSucursal(SucursalBean.ID_SUCURSAL);
            clienteAgregar.setSucursalId(sucursal);
            controlador.create(clienteAgregar);
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("cliente.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede eliminar Vehiculo"));
        }
    }
    
    public void modificarCliente(){
        try{
            if(clienteModificar.getSucursalId()==null){
                SucursalJpaController controlasorSucursal = new SucursalJpaController();
                Sucursal sucursal = controlasorSucursal.findSucursal(SucursalBean.ID_SUCURSAL);
                clienteModificar.setSucursalId(sucursal);
            }
            controlador.edit(clienteModificar);
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("cliente.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede eliminar Vehiculo"));
        }
    }
    
    public void eliminarCliente(Cliente cliente){
         try{
            controlador.destroy(cliente.getIdentificacion());
            FacesContext contex= FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("cliente.xhtml");
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No se puede eliminar Evento"));
        }
    }
}

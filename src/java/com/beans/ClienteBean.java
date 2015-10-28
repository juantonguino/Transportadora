/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.controllers.ClienteJpaController;
import com.entities.Cliente;
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
            clientes.add(c);
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
    
}

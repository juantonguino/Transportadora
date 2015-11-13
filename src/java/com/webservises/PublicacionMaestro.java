/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservises;

import com.JDBC.GestorReplicacion;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author juandiego
 */
@WebService(serviceName = "PublicacionMaestro")
public class PublicacionMaestro {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "showMasterStatus")
    public String showMasterStatus() {
        //TODO write your implementation code here:
        GestorReplicacion gestorReplicacion = new GestorReplicacion();
        return gestorReplicacion.showMasterStatus();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservises;

import com.JDBC.FachadaDB;
import com.JDBC.GestorReplicacion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        FachadaDB fachada= new FachadaDB();
        try {
            Connection con = fachada.crearConexion();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("show master status;");
            String file="";
            int position=0;
            while (res.next()) {                
                file = res.getString("File");
                position = res.getInt("Position");   
            }
            st.close();
            fachada.cerrarConexion(con);
            return file+"/"+position;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}

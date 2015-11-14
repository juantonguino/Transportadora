/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juandiego
 */
public class GestorReplicacion {
    
    private FachadaDB fachadaDB;
    
    private Connection connection;

    public GestorReplicacion() {
        fachadaDB= new FachadaDB();
        connection= fachadaDB.crearConexion();
    }
    
    public String showMasterStatus(){
        try {
            ResultSet resultado = this.fachadaDB.seleccionar("show master status;");
            String file="";
            int position=0;
            while (resultado.next()) {                
                file = resultado.getString("File");
                position = resultado.getInt("Position");   
            }
            return file+"/"+position;
        } catch (SQLException ex) {
            Logger.getLogger(GestorReplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public void changeMasterTo1(String masterLogFile, String Pos){
        String changeMasterTo="CHANGE MASTER TO MASTER_HOST = '192.168.0.202', MASTER_USER = 'user12', MASTER_PASSWORD = 'admin', MASTER_LOG_FILE = '"+masterLogFile+"', MASTER_LOG_POS = "+Pos+";";
    }

    private static String showMasterStatusResultCallService() {
        com.clientwebservises.PublicacionMaestro_Service service = new com.clientwebservises.PublicacionMaestro_Service();
        com.clientwebservises.PublicacionMaestro port = service.getPublicacionMaestroPort();
        return port.showMasterStatus();
    }
}

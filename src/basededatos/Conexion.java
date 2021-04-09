/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author moraima
 */
public class Conexion {
     public static Connection conection=null;
    public static Connection getConnection(){
       try {
            String urlDatabase = "jdbc:mysql://localhost:3306/trabajo2";
            Class.forName("com.mysql.jdbc.Driver"); //cargar el driver manualmente
            conection = (Connection) DriverManager.getConnection(urlDatabase,  "root", "");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Fallo al recibir base de datos"+ex.getMessage());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No hay resultado"+ex.getMessage());
        }finally{
            System.out.println("|*CONECTADO A BD*|");
            return conection;
        }
    } 
}

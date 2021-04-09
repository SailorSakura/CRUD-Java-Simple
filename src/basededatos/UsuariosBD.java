/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import basededatos.usuarios1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.digest.DigestUtils;
import trabajo2.USUARIOS;



/**
 *
 * @author moraima
 */
public class UsuariosBD {
    private static final Conexion con = new Conexion();
    private static final Connection conexion = Conexion.getConnection();
    private static PreparedStatement ps = null;
    
    
    public static boolean GuardarUsuario(usuarios1 usu){
    String sql= "INSERT INTO usuarios (idusuario, usuario,  pass) VALUES (?,?,?)"; 
    try {
            ps = conexion.prepareStatement(sql); 
            ps.setInt(1, usu.getIdusuario());
            ps.setString(2, usu.getUsuario());
            ps.setString(3, usu.getPass());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosBD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error GuardarUsuarios" + ex.getMessage());
            return false;
        }
    }
    
     public static int extraerIDusuariosMax(){
           String sql ="SELECT MAX(idusuario) FROM usuarios" ;
           int id = 0;  
                try {
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                        if(rs.next()){
                            id= rs.getInt(1);
                        }
                        if(id==0){
                            id=1;
                        }else{
                            id= id+1;
                        }
                        return id;
                } catch (SQLException ex) {
                        return 1;
                }
         }
    
     
     public static void ListarUsuario(){
         DefaultTableModel model= (DefaultTableModel)trabajo2.USUARIOS.tablausuario.getModel();
         while (model.getRowCount() > 0){
             model.removeRow(0);
         }
         String sql = "SELECT * FROM usuarios ORDER BY idusuario ASC";
         
         try {
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql);
             String[] filas = new String [2];
             while (rs.next()){
                 filas[0] = rs.getString("idusuario");
                 filas[1] = rs.getString("usuario");
                 model.addRow(filas);
             }
         trabajo2.USUARIOS.tablausuario.setModel(model);
         } catch (Exception e) {
             System.out.println("Error:"+ e.getMessage());
         }
     }
     
     public static boolean borrarUsuario(String idusuario){
         String sql = "DELETE FROM usuarios WHERE idusuario= '"+idusuario+"'";
        try {
            ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Se eliminó correctamente");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosBD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error. No se pudo borrar el registro " + ex.getMessage());
            return false;
        }
     }
     
     public static void ObtenerUsuario(String idusuario){
      
         String sql = "SELECT * FROM usuarios WHERE idusuario= '"+idusuario+"'";
         
         try {
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql);
             String[] filas = new String [2];
             while (rs.next()){
                 USUARIOS.txtusuario.setText(rs.getString("usuario"));
                 USUARIOS.txtcontraseña.setText(rs.getString("pass"));
               
             }

         } catch (Exception e) {
             System.out.println("Error:"+ e.getMessage());
         }
     }
     
     public static boolean ActualizarUsuario(usuarios1 usu){
    String sql= "UPDATE usuarios SET usuario=?, pass=? WHERE idusuario=?"; //SENTENCIA SQL
        try {
            ps = conexion.prepareStatement(sql); //se crea la conexion
            ps.setString(1, usu.getUsuario());
            ps.setString(2, usu.getPass());
            ps.setInt(3, usu.getIdusuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosBD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error, no se pudo actualizar" + ex.getMessage());
            return false;
        }
    }
 
     public static void BuscarUsuarios(String buscar){
         DefaultTableModel model= (DefaultTableModel)trabajo2.USUARIOS.tablausuario.getModel();
         while(model.getRowCount() > 0){
             model.removeRow(0);
        }
       
        String sql = "";
        if (buscar.equals("")){
            sql="SELECT * FROM usuarios";
        } else { 
            sql="SELECT * FROM usuarios WHERE (usuario LIKE '"+buscar+"%')";
        }
        try {
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql);
             String[] filas = new String [2];
             while (rs.next()){
                 filas[0] = rs.getString("idusuario");
                 filas[1] = rs.getString("usuario");
                 model.addRow(filas);
             }
         trabajo2.USUARIOS.tablausuario.setModel(model);
         } catch (Exception e) {
             System.out.println("Error:"+ e.getMessage());
         }
     }
   
}

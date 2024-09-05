/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    conexion cn = new conexion();
    public login log(String correo, String pass){
        login l= new login();
        String sql= "SELECT * FROM usuarios WHERE correo = ? AND pass = ?";
        try{
           con = cn.getConnetion();
           ps = con.prepareStatement(sql);
           ps.setString(1,correo);
           ps.setString(2, pass);
           rs = ps.executeQuery();
           if(rs.next()){
             l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));
           }
           
           
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return l;
    } 
    
       public boolean Registrar ( login reg ) {
           String sql="INSERT INTO usuarios( nombre,correo,pass,rol) VALUES (?,?,?,?)";
           try{
               con= cn .getConnetion();
               ps= con.prepareStatement(sql);
               ps.setString(1, reg.getNombre());
               ps.setString(2, reg.getCorreo());
               ps.setString(3, reg.getPass());
               ps.setString(4, reg.getRol());
               ps.execute();
               return true;
           }catch ( SQLException e){
               System.out.println(e.toString());
               return false;
               
           }
       }    
}

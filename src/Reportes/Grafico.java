/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import modelo.conexion;
import  java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
public class Grafico {
    public static void Graficar( String fecha){
        Connection con;
        conexion cn = new conexion();
        PreparedStatement ps;
        ResultSet rs;
        try{
            
            String sql = "SELECT total FROM ventas WHERE fecha= ?";
            
            con= cn.getConnetion();
            ps= con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs= ps.executeQuery();
            DefaultPieDataset dateset= new DefaultPieDataset();
            while( rs.next()){
                dateset.setValue(rs.getString("total"),rs.getDouble("total"));
            }
            JFreeChart jf= ChartFactory.createPieChart("Reporte de venta",dateset);
            ChartFrame f = new ChartFrame("total de ventas por dia",jf);
            
            f.setSize(1000,500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            
            
        }catch( SQLException e){
            System.out.println(e.toString());
        }
        
    }
}

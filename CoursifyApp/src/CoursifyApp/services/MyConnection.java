/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;




import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author PC
 */
public class MyConnection {
    String url ="jdbc:mysql://localhost:3306/" + "coursifydb";
    String login ="root";
    String password="";
    
    private Connection cnx;
    private static MyConnection mycnx;
    
    private MyConnection(){
        try {
            cnx=DriverManager.getConnection(url, login , password);
            System.out.println("Conncetion to DB !");
        } catch (SQLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Connection getCnx(){
        return cnx;
    }
    public static MyConnection getMyCnx(){
        if(mycnx == null)
        mycnx = new MyConnection();
        return mycnx;
    }
}

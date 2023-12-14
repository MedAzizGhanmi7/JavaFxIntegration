/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;

import java.sql.Date;
import java.util.HashMap;

/**
 *
 * @author ghanm
 */
public class Admin extends User{

   // private HashMap <Integer,Boolean>reclamations ;

    public Admin() {
        this.setUserType("admin");
        this.setIsActive(true);
    }

    public Admin(String username, String password, String email, String firstName, String lastName, Date dateOfBirth, String gender, String address, String phoneNumber, String profilePicture, String userType, Date registrationDate, Boolean isActive) {
        super(username, password, email, firstName, lastName, dateOfBirth, gender, address, phoneNumber, profilePicture, userType, registrationDate, isActive);
    }

    public Admin(int id, String username, String password, String email, String firstName, String lastName, Date dateOfBirth, String gender, String address, String phoneNumber, String profilePicture, String userType, Date registrationDate, Boolean isActive) {
        super(id, username, password, email, firstName, lastName, dateOfBirth, gender, address, phoneNumber, profilePicture, userType, registrationDate, isActive);
    }

   

   /* public HashMap<Integer, Boolean> getReclamations() {
        return reclamations;
    }

    public void setReclamations(HashMap<Integer, Boolean> reclamations) {
        this.reclamations = reclamations;
    }*/

    @Override
    public String toString() {
        return "Admin{"
               /* + "reclamations=" + reclamations */ + super.toString()+
                '}';
    }
}

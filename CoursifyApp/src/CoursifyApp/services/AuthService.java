/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;

import CoursifyApp.utilities.Myconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ghanm
 */
public class AuthService {

    private Connection cnx;

    public AuthService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    public int authenticate(String email, String password, String usertype) {
        PreparedStatement pst;
        int id = -1;

        try {
            pst = cnx.prepareStatement("SELECT `id` FROM `" + usertype + "` WHERE `email`=? AND `password`=?");
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id"); // Get the user's ID if a match is found
            }

            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return id;
    }

}

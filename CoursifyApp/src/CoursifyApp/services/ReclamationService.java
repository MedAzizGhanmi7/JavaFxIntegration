/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;


import CoursifyApp.entities.Reclamation;
import CoursifyApp.utilities.Myconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class ReclamationService implements CrudInterface<Reclamation> {

    private Connection cnx;
    private Statement st;
    private ResultSet rs;

    public ReclamationService() {
        cnx = Myconnection.getInstance().getCnx();
    }

   @Override
public void create(Reclamation entity) {
    PreparedStatement pst = null;
    int generatedId = -1;

    try {
        pst = cnx.prepareStatement("INSERT INTO reclamation (numero_mobile, description, objet,  nom, prenom, email) VALUES (?, ?,  ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, entity.getNumero_mobile());
      
        pst.setString(2, entity.getDescription());
        pst.setString(3, entity.getObjet());
      
        pst.setString(4, entity.getNom());
        pst.setString(5, entity.getPrenom());
        pst.setString(6, entity.getEmail());

        int n = pst.executeUpdate();
        if (n > 0) {
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1); // Get the generated ID
            }
        }
        entity.setId(generatedId);
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
        // Handle the exception as needed for your application
    } finally {
        try {
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle closing error if necessary
        }
    }
}

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return cnx.prepareStatement(sql);
    }

    @Override
    public void update(Reclamation entity) {
        PreparedStatement pst;
        int n = 0;

        try {
           pst = cnx.prepareStatement("INSERT INTO reclamation ( numero_mobile,  , description,  objet,    nom,  prenom,  email) VALUES (?, ?,  ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            //pst.setInt(1, entity.getId());
            pst.setString(2, entity.getNumero_mobile());
           
             
            pst.setString(3, entity.getDescription());
            pst.setString(4, entity.getObjet());
          
          
            pst.setString(5, entity.getNom());
            pst.setString(6, entity.getPrenom());
             pst.setString(6, entity.getEmail());


            n = pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(int id) {

        int n = 0;
        PreparedStatement st;
        try {

            st = cnx.prepareStatement("DELETE FROM `reclamation` WHERE `id`=?");

            st.setInt(1, id);

            n = st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Reclamation getById(int id) {

        PreparedStatement st;
       Reclamation  reclamation;
        reclamation = new Reclamation();
        try {

            st = cnx.prepareStatement("SELECT * FROM `reclamation` WHERE `id`=?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {

               reclamation.setId(rs.getInt("id"));
                reclamation.setNumero_mobile(rs.getString("numero_mobile"));
              
                reclamation.setDescription(rs.getString("description"));
                reclamation.setObjet(rs.getString("objet"));
              
                reclamation.setNom(rs.getString("nom"));
                reclamation.setPrenom(rs.getString("prenom"));
                reclamation.setEmail(rs.getString("email"));
               

            }
            cnx.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return reclamation;
    }

    @Override
public List<Reclamation> getAll() {
    List<Reclamation> reclamationList = new ArrayList<>();
    PreparedStatement st;

    try {
        st = cnx.prepareStatement("SELECT * FROM reclamation");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Reclamation reclamation = new Reclamation();
            reclamation.setId(rs.getInt("id")); // Include the ID field
            reclamation.setNumero_mobile(rs.getString("numero_mobile"));
            
            reclamation.setDescription(rs.getString("description"));
            reclamation.setObjet(rs.getString("objet"));
        
            reclamation.setNom(rs.getString("nom"));
            reclamation.setPrenom(rs.getString("prenom"));
            reclamation.setEmail(rs.getString("email"));

            reclamationList.add(reclamation);
        }

        st.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }

    return reclamationList;
}

}
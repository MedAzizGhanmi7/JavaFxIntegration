/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;

import entities.DON;
import CoursifyApp.utilities.Myconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonationService implements CrudInterface<DON> {

    private Connection cnx;

    public DonationService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void create(DON entity) {
        PreparedStatement pst = null;
        int generatedId = -1;

        try {
            pst = cnx.prepareStatement("INSERT INTO donation (montant, numero_mobile, address, code_postal, nom, prenom, email) VALUES (?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setString(1, entity.getMontant());
            pst.setString(2, entity.getNumero_mobile());
            pst.setString(3, entity.getAddress());
            pst.setString(4, entity.getCode_postal());
            pst.setString(5, entity.getNom());
            pst.setString(6, entity.getPrenom());
            pst.setString(7, entity.getEmail());

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

    @Override
    public void update(DON entity) {
        PreparedStatement pst;
        int n = 0;

        try {
            pst = cnx.prepareStatement("UPDATE donation SET montant=?, numero_mobile=?, address=?, code_postal=?, nom=?, prenom=?, email=? WHERE id=?");

            pst.setString(1, entity.getMontant());
            pst.setString(2, entity.getNumero_mobile());
            pst.setString(3, entity.getAddress());
            pst.setString(4, entity.getCode_postal());
            pst.setString(5, entity.getNom());
            pst.setString(6, entity.getPrenom());
            pst.setString(7, entity.getEmail());
            pst.setInt(8, entity.getId());

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
            st = cnx.prepareStatement("DELETE FROM donation WHERE id=?");
            st.setInt(1, id);
            n = st.executeUpdate();
            st.close();
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public DON getById(int id) {
        PreparedStatement st;
        DON donation = new DON();

        try {
            st = cnx.prepareStatement("SELECT * FROM donation WHERE id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                donation.setId(rs.getInt("id"));
                donation.setMontant(rs.getString("montant"));
                donation.setNumero_mobile(rs.getString("numero_mobile"));
                donation.setAddress(rs.getString("address"));
                donation.setCode_postal(rs.getString("code_postal"));
                donation.setNom(rs.getString("nom"));
                donation.setPrenom(rs.getString("prenom"));
                donation.setEmail(rs.getString("email"));
            }
            st.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return donation;
    }

    @Override
    public List<DON> getAll() {
        List<DON> donationList = new ArrayList<>();
        PreparedStatement st;

        try {
            st = cnx.prepareStatement("SELECT * FROM donation");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                DON donation = new DON();
                donation.setId(rs.getInt("id"));
                donation.setMontant(rs.getString("montant"));
                donation.setNumero_mobile(rs.getString("numero_mobile"));
                donation.setAddress(rs.getString("address"));
                donation.setCode_postal(rs.getString("code_postal"));
                donation.setNom(rs.getString("nom"));
                donation.setPrenom(rs.getString("prenom"));
                donation.setEmail(rs.getString("email"));
                donationList.add(donation);
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return donationList;
    }
}


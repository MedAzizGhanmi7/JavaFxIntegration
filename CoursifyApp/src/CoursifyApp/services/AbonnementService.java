package CoursifyApp.services;

import CoursifyApp.entities.Abonnement;
import CoursifyApp.utilities.Myconnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements CrudInterface<Abonnement> {


    private Connection cnx;
    private List<Abonnement> abonnements;

    public AbonnementService() {
        this.abonnements = new ArrayList<>();
        cnx = Myconnection.getInstance().getCnx();
    }

@Override
public void create(Abonnement abonnement) {
    if (abonnement == null) {
        System.err.println("Abonnement object is null.");
        return;
    }

    if (cnx == null) {
        System.err.println("Database connection is not established.");
        return;
    }

    String query = "INSERT INTO abonnement (user, cours, typeAbonnement,price, dateDebut, dateExpiration) VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        
        pst.setString(1, abonnement.getUser());
        pst.setString(2, abonnement.getCours());
        pst.setString(3, abonnement.getTypeAbonnement());
        pst.setFloat(4, abonnement.getPrice());
        pst.setDate(5, new Date(abonnement.getDateDebut().getTime()));
        pst.setDate(6, new Date(abonnement.getDateExpiration().getTime()));

        pst.executeUpdate();
        System.out.println("Abonnement added successfully");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}


public void update(Abonnement abonnement) {
    if (abonnement == null) {
        System.err.println("Abonnement object is null.");
        return;
    }

    if (cnx == null) {
        System.err.println("Database connection is not established.");
        return;
    }

    // Vérifier si l'utilisateur existe avant la mise à jour
    String checkQuery = "SELECT * FROM abonnement WHERE user=?";
    try (PreparedStatement checkStatement = cnx.prepareStatement(checkQuery)) {
        checkStatement.setString(1, abonnement.getUser());
        try (ResultSet resultSet = checkStatement.executeQuery()) {
            if (!resultSet.next()) {
                System.err.println("User does not exist in the database.");
                return;
            }
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
        return;
    }

    // Effectuer la mise à jour si l'utilisateur existe
    String updateQuery = "UPDATE abonnement SET cours=?, typeAbonnement=?, price=?, dateDebut=?, dateExpiration=? WHERE user=?";
    try (PreparedStatement pst = cnx.prepareStatement(updateQuery)) {
        pst.setString(1, abonnement.getCours());
        pst.setString(2, abonnement.getTypeAbonnement());
        pst.setFloat(3, abonnement.getPrice());
        pst.setDate(4, new Date(abonnement.getDateDebut().getTime()));
        pst.setDate(5, new Date(abonnement.getDateExpiration().getTime()));
        pst.setString(6, abonnement.getUser());

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Abonnement updated successfully");
        } else {
            System.err.println("Failed to update abonnement.");
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}



    

//     public void delete(String userName) {
//        String query = "DELETE FROM abonnement WHERE user=?";
//        try (PreparedStatement pst = cnx.prepareStatement(query)) {
//            pst.setString(1, userName);
//            pst.executeUpdate();
//            System.out.println("Abonnement deleted successfully");
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }


 
 public Abonnement getByName(String userName) {
        String query = "SELECT * FROM abonnement WHERE user=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, userName);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Abonnement abonnement = new Abonnement(
                            rs.getString("user"),
                            rs.getString("cours"),
                            rs.getString("typeAbonnement"),
                            rs.getFloat("price"),
                            rs.getDate("dateDebut"),
                            rs.getDate("dateExpiration")
                    );
                           
                    
                    return abonnement;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }



public List<Abonnement> getAll() {
    List<Abonnement> abonnements = new ArrayList<>();
    String query = "SELECT * FROM abonnement";
    try (PreparedStatement pst = cnx.prepareStatement(query);
         ResultSet rs = pst.executeQuery()) {
        while (rs.next()) {
            Abonnement abonnement = new Abonnement(
            
                rs.getString("user"),
                rs.getString("cours"),
                rs.getString("typeAbonnement"),
                rs.getFloat("price"),
                rs.getDate("dateDebut"),
                rs.getDate("dateExpiration")
            );
            abonnements.add(abonnement);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return abonnements;
}









    
    @Override
    public Abonnement getById(int id) {
        String query = "SELECT * FROM abonnement WHERE idAbonnement=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Abonnement abonnement = new Abonnement(

                            rs.getString("user"),
                            rs.getString("cours"),
                            
                            rs.getString("typeAbonnement"),
                            rs.getFloat("price"),
                            rs.getDate("dateDebut"),
                            rs.getDate("dateExpiration")
                    );
                      
                    
                    return abonnement;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
   public void delete(int idAbonnement) {
    if (cnx == null) {
        System.err.println("Database connection is not established.");
        return;
    }
    
    String query = "DELETE FROM abonnement WHERE idAbonnement=?";
    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        pst.setInt(1, idAbonnement);
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Abonnement deleted successfully");
        } else {
            System.err.println("Failed to delete abonnement.");
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}
    
public Abonnement findLongestAbonnement() {
    String query = "SELECT * FROM abonnement ORDER BY DATEDIFF(dateExpiration, dateDebut) DESC LIMIT 1";
    try (PreparedStatement pst = cnx.prepareStatement(query);
         ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            Abonnement abonnement = new Abonnement(
                rs.getString("user"),
                rs.getString("cours"),
                rs.getString("typeAbonnement"),
                rs.getFloat("price"),
                rs.getDate("dateDebut"),
                rs.getDate("dateExpiration")
            );
            return abonnement;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null; // Return null if no Abonnement found
}





    
    
}
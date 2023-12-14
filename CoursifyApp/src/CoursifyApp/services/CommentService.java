/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;

import CoursifyApp.entities.*;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
/**
 *
 * @author PC
 */
public class CommentService {

    public void add (Comment c){
        Connection connection = MyConnection.getMyCnx().getCnx();
        int n = 0;
        int generatedId = -1;
        String query = "INSERT INTO comment (EmailCommentateur,ContenuCommentaire) VALUES (?, ?)";
            try (PreparedStatement pst = MyConnection.getMyCnx().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            
            pst.setString(1, c.getEmailCommentateur());
            pst.setString(2, c.getContenuCommentaire());        
            pst.executeUpdate();
            
            if (n > 0) {
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
            generatedId = generatedKeys.getInt(1); // Get the generated ID
                }
            }
            System.out.println("Comment added successfully to the database");
            System.out.println("L'id de votre commentaire est" + generatedId);
            pst.close();
            c.setId(generatedId);
        } catch (SQLException ex) {
            System.err.println("Error adding comment to the database: "+ex.getMessage());
        }
    }

    public void update(Comment cmnt) {
    
    
    int n = 0;
    String query= "UPDATE comment SET ContenuCommentaire=? WHERE NomCours=?";
    try (Connection connection = MyConnection.getMyCnx().getCnx();
         PreparedStatement pst = connection.prepareStatement(query)){
        

        pst.setString(1, cmnt.getContenuCommentaire());
        
        n = pst.executeUpdate();
        pst.close();
        
        if (n > 0) {
            System.out.println("Comment updated successfully");
        } else {
            System.out.println("No comment found with the specified name.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
}
    public void delete(int id) {
        int n = 0;
        
    String query= "DELETE FROM comment WHERE id=?";
    try (PreparedStatement pst = MyConnection.getMyCnx().getCnx().prepareStatement(query)){
            pst.setInt(1, id);
            n = pst.executeUpdate();
            pst.close();
          if (n > 0) {
            System.out.println("comment deleted successfully");
        } else {
            System.out.println("No comment found with the specified ID.");
        }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
public Comment getById(int id) {
    Comment comment = null;
    String query = "SELECT * FROM comment WHERE id=?";
    try (PreparedStatement pst = MyConnection.getMyCnx().getCnx().prepareStatement(query)) {
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            comment = new Comment(); // Creez un nouvel objet Message
            
            comment.setEmailCommentateur(rs.getString("EmailCommentateur"));
            comment.setContenuCommentaire(rs.getString("ContenuCommentaire"));
            comment.setDate(rs.getDate("Date"));
            // Vous pouvez egalement definir d'autres attributs ici si necessaire
            System.out.println("*****************************************");
            System.out.println("Email commenter: " + comment.getEmailCommentateur());
            System.out.println("Contain comment: " + comment.getContenuCommentaire());
            System.out.println("Date du commentaire: " + comment.getDate());
            
        } else {
            System.out.println("No Comment is found with this specified id.");
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    return comment;
}

  
public List<Comment> getAll() {
    List<Comment> commentList = new ArrayList<>();
    String query = "SELECT * FROM comment";
    try (PreparedStatement pst = MyConnection.getMyCnx().getCnx().prepareStatement(query)) {
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setContenuCommentaire(rs.getString("ContenuCommentaire"));
            comment.setDate(rs.getDate("Date"));
            comment.setEmailCommentateur(rs.getString("EmailCommentateur"));
            commentList.add(comment);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
    return commentList;
}
}
    




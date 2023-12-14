/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;


import CoursifyApp.entities.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 *
 * @author PC
 */
public class MessageService {
public void add (Message m) {
    Connection connection = MyConnection.getMyCnx().getCnx();
    int n = 0;
    int generatedId = -1;
    String query = "INSERT INTO messages (ObjetMessage, ContenuMessage, EmailDestinataire, EmailEmetteur) VALUES (?, ?, ?, ?)";
    try (PreparedStatement pst = MyConnection.getMyCnx().getCnx().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        pst.setString(1, m.getObjetMessage());
        pst.setString(2, m.getContenuMessage());
        pst.setString(3, m.getEmailDestinataire());
        pst.setString(4, m.getEmailEmetteur());

        n = pst.executeUpdate();
        if (n > 0) {
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1); // Obtenez l'ID généré
            }
            System.out.println("Message ajouté avec succès à la base de données");
            System.out.println("L'id de votre message est :" + generatedId);
            // Pas besoin d'afficher la date ici si la colonne est auto-générée
            m.setId(generatedId); // Affectez l'ID généré à l'objet Message
        }
        pst.close();
    } catch (SQLException ex) {
        System.err.println("Erreur lors de l'ajout du message à la base de données: " + ex.getMessage());
    }
}



    

public void update(Message mesg) {
    
    
    int n = 0;
    String query= "UPDATE messages SET ContenuMessage=?, ObjetMessage=? ";
    try (Connection connection = MyConnection.getMyCnx().getCnx();
        PreparedStatement pst = connection.prepareStatement(query)) {
        pst.setString(1, mesg.getContenuMessage());
        pst.setString(2, mesg.getObjetMessage());
        

        n = pst.executeUpdate();
        pst.close();
        
        if (n > 0) {
            System.out.println("Message updated successfully");
                    // Ajouter l'envoi de notifications par e-mail et SMS
        sendEmail(mesg.getEmailDestinataire(), "Mise à jour de message", "Le message a été mis à jour.");
        sendSMS("+21694156292", "Le message a été mis à jour.");
        } else {
            System.out.println("No message found with the specified id.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
}
public void delete(int id) {
    int n = 0;     
    String query= "DELETE FROM messages WHERE id=?";
    try (PreparedStatement pst = MyConnection.getMyCnx().getCnx().prepareStatement(query)){ 
        pst.setInt(1, id); // Spécifiez l'ID à supprimer
            
        n = pst.executeUpdate();
        pst.close();
          
        if (n > 0) {
            System.out.println("Message deleted successfully");
        } else {
            System.out.println("No message found with the specified ID.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
}

public Message getById(int id) {
    Message message = null;
    String query = "SELECT * FROM messages WHERE id=?";
    try (PreparedStatement pst = MyConnection.getMyCnx().getCnx().prepareStatement(query)) {
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            message = new Message(); // Creez un nouvel objet Message
            message.setId(rs.getInt("id"));
            message.setObjetMessage(rs.getString("ObjetMessage"));
            message.setContenuMessage(rs.getString("ContenuMessage"));
            message.setEmailDestinataire(rs.getString("EmailDestinataire"));
            message.setDateEnvoi(rs.getDate("DateEnvoi"));
            // Vous pouvez egalement definir d'autres attributs ici si necessaire
        
            System.out.println("*****************************************");
            System.out.println("Message id: " + message.getId());
            System.out.println("Message Objet: " + message.getObjetMessage());
            System.out.println("Contenu du message: " + message.getContenuMessage());
            System.out.println("Email Destinataire: " + message.getEmailDestinataire());
            System.out.println("Date d'envoi du message: " + message.getDateEnvoi());
        } else {
            System.out.println("Aucun message trouvee avec l'objet specifier.");
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    return message;
}

  
public List<Message> getAll() {
    List<Message> messageList = new ArrayList<>();
    String query = "SELECT * FROM messages";
    try (PreparedStatement pst = MyConnection.getMyCnx().getCnx().prepareStatement(query)) {
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setObjetMessage(rs.getString("ObjetMessage"));
            message.setContenuMessage(rs.getString("ContenuMessage"));
            message.setDateEnvoi(rs.getDate("DateEnvoi"));
            message.setEmailDestinataire(rs.getString("EmailDestinataire"));
            message.setEmailEmetteur(rs.getString("EmailEmetteur"));
            messageList.add(message);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
    return messageList;
}
    public void sendEmail(String email, String subject, String content) {
        // Utilisez l'API email pour envoyer un e-mail au destinataire spécifié
        // Assurez-vous d'inclure les informations d'authentification et de configuration nécessaires
        // Utilisez la bibliothèque ou l'API que vous avez importée pour envoyer l'e-mail
        System.out.println("Envoi d'un e-mail à : " + email);
        System.out.println("Sujet : " + subject);
        System.out.println("Contenu : " + content);
        System.out.println("E-mail envoyé avec succès.");
    }

    public void sendSMS(String phoneNumber, String content) {
        // Utilisez l'API SMS pour envoyer un SMS au numéro de téléphone spécifié
        // Assurez-vous d'inclure les informations d'authentification et de configuration nécessaires
        // Utilisez la bibliothèque ou l'API que vous avez importée pour envoyer le SMS
        System.out.println("Envoi d'un SMS au numéro : " + phoneNumber);
        System.out.println("Contenu : " + content);
        System.out.println("SMS envoyé avec succès.");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Reclamation;
import javax.mail.Authenticator;

import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author ZARAMI
 */
public class RepondrereclamtionController implements Initializable {

    @FXML
    private TextArea TAdescription;
    @FXML
    private TextField TFnom;
    @FXML
    private TextField TFprenom;
    @FXML
    private TextField TFtelephone;
    @FXML
    private TextField TFmail;
    @FXML
    private Label nomErrorLabel;
    @FXML
    private Label prenomErrorLabel;
    @FXML
    private Label telephoneErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label objetErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    @FXML
   
   private SplitMenuButton SPobjet;
    
    public void setReclamation(Reclamation reclamation) {
        if (reclamation != null) {
              TFtelephone.setText(reclamation. getNumero_mobile());
            TAdescription.setText(reclamation.getDescription());
             SPobjet.setText(reclamation.getObjet());
            TFnom.setText(reclamation.getNom());
            TFprenom.setText(reclamation.getPrenom());
             TFmail.setText(reclamation.getEmail());
            
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         TFnom.setEditable(false);
    TFprenom.setEditable(false);
    TFtelephone.setEditable(false);
    TFmail.setEditable(false);
    SPobjet.setDisable(true); // Désactivez le MenuButton
    TAdescription.setEditable(false);
        // TODO
    }    

 
    

    @FXML
    private void ferr(ActionEvent event) {
    }

    @FXML
    private void repp(ActionEvent event) {
     

   final String username = "cursify872@gmail.com";
    final String password = "flap uytp ivep lqxw";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new Authenticator() {
        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(username, password);
        }
    });

      try {
        String recipientEmail = TFmail.getText(); // Récupérer l'adresse e-mail du champ de texte
        if (recipientEmail.isEmpty()) {
            // Gérez le cas où le champ e-mail est vide
            emailErrorLabel.setText("L'adresse e-mail du destinataire est requise");
            return;
        }

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Utilisez l'adresse du destinataire
        message.setSubject("Réclamation reçue");
        message.setText("Votre réclamation a été bien reçue et sera résolue très prochainement.");

        Transport.send(message);

        System.out.println("E-mail envoyé avec succès.");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    } catch (MessagingException e) {
        e.printStackTrace();
    }
}











        
    

    @FXML
    private void handleOption1(ActionEvent event) {
    }

    @FXML
    private void handleOption2(ActionEvent event) {
    }

    @FXML
    private void handleOption3(ActionEvent event) {
    }

}

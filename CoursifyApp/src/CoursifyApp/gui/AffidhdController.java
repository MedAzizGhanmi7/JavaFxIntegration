/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import entities.DON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import CoursifyApp.services.DonationService;
import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AffidhdController implements Initializable {

    @FXML
    private JFXListView<DON> listd;
    @FXML
    private JFXButton supd;

    private ObservableList<DON> donations;
    private DonationService donationService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        donationService = new DonationService();
        donations = FXCollections.observableArrayList(donationService.getAll());
        listd.setItems(donations);

    supd.setOnAction(event -> {
    DON selectedDonation = listd.getSelectionModel().getSelectedItem();
    if (selectedDonation != null) {
        // Demandez confirmation à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer la donation ?");
        alert.setContentText("Voulez-vous vraiment supprimer cette donation ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
               int donationId = selectedDonation.getId();
            donationService.delete(donationId);

            // Supprimez la donation de la liste
            donations.remove(selectedDonation);
        }
    }
});
    }







       
    

    @FXML
    private void repd(ActionEvent event) {

    // Récupérez l'élément sélectionné dans le TreeView
    DON selectedDonation = listd.getSelectionModel().getSelectedItem();

    if (selectedDonation != null) {
        final String username = "cursify872@gmail.com";
        final String password = "bopn wgjz suyx vuwm";

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
            String recipientEmail = selectedDonation.getEmail(); // Utilisez l'adresse e-mail de l'élément sélectionné
            if (recipientEmail.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("L'adresse e-mail du destinataire est requise.");
                alert.showAndWait();
                return;
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Utilisez l'adresse du destinataire
            message.setSubject("Donation reçue");
            message.setText("Nous tenons à exprimer notre profonde gratitude pour votre généreuse contribution. Votre soutien est d'une importance capitale et nous apprécions grandement votre engagement envers notre cause. Grâce à votre don, nous sommes en mesure de poursuivre notre mission et de faire une différence positive. Votre acte de générosité fait une réelle différence. Merci encore pour votre soutien continu");

            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}


    
    

    @FXML
    private void ferd(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboardFXML.fxml"));
    Parent instructorDashboardRoot = loader.load();
    
    // Créer une nouvelle scène avec le fichier FXML chargé
    Scene instructorDashboardScene = new Scene(instructorDashboardRoot);

    // Récupérer la scène actuelle à partir du bouton "Retour"
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Changer la scène pour afficher la page InstructorDashboardFXML.fxml
    currentStage.setScene(instructorDashboardScene);
    currentStage.setTitle("Instructor Dashboard"); // Mettre à jour le titre si nécessaire
    currentStage.show();

    }
}

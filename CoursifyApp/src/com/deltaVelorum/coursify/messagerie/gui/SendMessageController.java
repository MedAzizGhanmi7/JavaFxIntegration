/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltaVelorum.coursify.messagerie.gui;

import CoursifyApp.services.MessageService;
import CoursifyApp.entities.Message;
import CoursifyApp.gui.InstructorDashboardFXMLController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SendMessageController implements Initializable {

    @FXML
    private TextField EmailEmetteur;
    @FXML
    private TextField EmailDestinataire;
    @FXML
    private TextArea ContenuMessage;
    @FXML
    private TextField ObjetMessage;
    private Button allButton;
    @FXML
    private Button getAllButtond;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

   @FXML
private void getAll(ActionEvent event) throws IOException {
   try {
            // Load the AdminDashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructorDashboardFXML.fxml"));
            Parent instructorDashboardRoot = loader.load();

            // Create a new scene
            Scene instructorDashboardScene = new Scene(instructorDashboardRoot);

            // Get the controller of the dashboard
            InstructorDashboardFXMLController dashboardController = loader.getController();

            // Pass the user ID and role back to the dashboard
            

            // Set the new scene on the current stage
            Stage currentStage = (Stage) getAllButtond.getScene().getWindow();
            currentStage.setScene(instructorDashboardScene);
            currentStage.setTitle("Instructor Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
        
}


    private void showSuccessMessageDialog(Message message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Message ajouté avec succès à la base de données.");
        alert.setContentText("L'id de votre message est : " + message.getId());
        alert.showAndWait();
    }

    @FXML
    private void getById(ActionEvent event) {
    }

    @FXML
    private void Return(ActionEvent event) {
    }

    @FXML
    private void Send(ActionEvent event) throws IOException {

        String emetteur = EmailEmetteur.getText();
        String destinataire = EmailDestinataire.getText();
        String subject = ObjetMessage.getText();
        String contenu = ContenuMessage.getText();

        MessageService messageService = new MessageService();

        if (validateEmail(emetteur) && validateEmail(destinataire)) {
            Message newMessage = new Message();
            newMessage.setObjetMessage(subject);
            newMessage.setEmailEmetteur(emetteur);
            newMessage.setEmailDestinataire(destinataire);
            newMessage.setContenuMessage(contenu);

            messageService.add(newMessage);

            showSuccessMessageDialog(newMessage);

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) EmailEmetteur.getScene().getWindow();
            
        }
        else {
            showErrorAlert("Veuillez saisir des adresses e-mail valides.");
        }
    }

    private boolean validateEmail(String email) {
        return email.matches("^(.+)@(.+)$");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}

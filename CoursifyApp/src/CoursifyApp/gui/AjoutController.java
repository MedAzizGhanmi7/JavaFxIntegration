/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Reclamation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import CoursifyApp.services.ReclamationService;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AjoutController implements Initializable {
    @FXML
    private TextField TFnom;
    @FXML
    private TextField TFprenom;
    @FXML
    private TextField TFmail;
    @FXML
    private TextField TFtelephone;
    @FXML
    private MenuButton SPobjet;
    @FXML
    private TextArea TAdescription;
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
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Vous pouvez initialiser des éléments de l'interface utilisateur ici si nécessaire.
    }

    @FXML
    private void ajoutrec(ActionEvent event) {
        ReclamationService rec = new ReclamationService();
        String telephone = TFtelephone.getText();
        String description = TAdescription.getText();
        String objet = SPobjet.getText();
        String nom = TFnom.getText();
        String prenom = TFprenom.getText();
        String email = TFmail.getText();

        // Effacez d'abord tous les messages d'erreur.
        telephoneErrorLabel.setText("");
        descriptionErrorLabel.setText("");
        objetErrorLabel.setText("");
        nomErrorLabel.setText("");
        prenomErrorLabel.setText("");
        emailErrorLabel.setText("");

        if (telephone.isEmpty() || description.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            // Affichez un message d'erreur général indiquant que tous les champs sont obligatoires.
            // Vous pouvez également définir un message d'erreur pour chaque champ vide si vous préférez.
            if (telephone.isEmpty()) {
                telephoneErrorLabel.setText("Le numéro de téléphone est requis");
            }
            if (description.isEmpty()) {
                descriptionErrorLabel.setText("La description est requise");
            }
            if (objet.isEmpty()) {
                objetErrorLabel.setText("L'objet est requis");
            }
            if (nom.isEmpty()) {
                nomErrorLabel.setText("Le nom est requis");
            }
            if (prenom.isEmpty()) {
                prenomErrorLabel.setText("Le prénom est requis");
            }
            if (email.isEmpty()) {
                emailErrorLabel.setText("L'adresse e-mail est requise");
            }
        } else {
            if (!telephone.matches("\\d{8}")) {
                telephoneErrorLabel.setText("Le numéro de téléphone doit contenir 8 chiffres");
            }
            if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
                emailErrorLabel.setText("L'adresse e-mail n'est pas valide");
            }
            if (!nom.matches("[A-Za-z]+")) {
    nomErrorLabel.setText("Le nom n'est pas valide");
}
              if (!prenom.matches("[A-Za-z]+")) {
    prenomErrorLabel.setText("Le prénom n'est pas valide");
}





            if (telephoneErrorLabel.getText().isEmpty() && descriptionErrorLabel.getText().isEmpty() &&
                    objetErrorLabel.getText().isEmpty() && nomErrorLabel.getText().isEmpty() &&
                    prenomErrorLabel.getText().isEmpty() && emailErrorLabel.getText().isEmpty()) {
                // Toutes les validations sont réussies, continuez avec l'enregistrement.
                Reclamation reclamation = new Reclamation(telephone, description, objet, nom, prenom, email);
                rec.create(reclamation);
                 showSuccessMessage("Réclamation envoyée avec succès!");
                // Affichez un message de succès ou redirigez l'utilisateur vers une autre page.
            }
        }
    }
    private void showSuccessMessage(String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Succès");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    }

    @FXML
    private void handleOption1(ActionEvent event) {
        SPobjet.setText("inscription");
    }

    @FXML
    private void handleOption2(ActionEvent event) {
        SPobjet.setText("cours");
    }

    @FXML
    private void handleOption3(ActionEvent event) {
        SPobjet.setText("autre");
    }

    @FXML
    private void ferr(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructorDashboardFXML.fxml"));
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
    








 
    
    
    


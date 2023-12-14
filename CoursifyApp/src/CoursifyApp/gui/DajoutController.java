package CoursifyApp.gui;

import com.jfoenix.controls.JFXTextField;
import entities.DON;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import CoursifyApp.services.DonationService;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class DajoutController implements Initializable {

    @FXML
    private TextField tfdnom;
    @FXML
    private TextField tfdprenom;
    @FXML
    private TextField tfdmail;
    @FXML
    private TextField tfdtelephone;
    @FXML
    private TextField tfdadress;
    @FXML
    private TextField tfdcode;

    @FXML
    private Label nomdErrorLabel;
    @FXML
    private Label prenomdErrorLabel;
    @FXML
    private Label telephonedErrorLabel;
    @FXML
    private Label maildErrorLabel;
    @FXML
    private Label adressErrorLabel;
    @FXML
    private Label codeErrorLabel;
    @FXML
    private Label montantErrorLabel;
    @FXML
    private JFXTextField tfmontant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Vous pouvez initialiser des composants ici si nécessaire.
    }

    @FXML
    private void donate(ActionEvent event) {
        DonationService donationService = new DonationService();

        // Réinitialisez les messages d'erreur à chaque appel de la méthode donate.
        montantErrorLabel.setText("");
        nomdErrorLabel.setText("");
        telephonedErrorLabel.setText("");
        maildErrorLabel.setText("");
        adressErrorLabel.setText("");
        codeErrorLabel.setText("");

        // Validez l'entrée de l'utilisateur
        String nom = tfdnom.getText();
        String prenom = tfdprenom.getText();
        String mail = tfdmail.getText();
        String telephone = tfdtelephone.getText();
        String address = tfdadress.getText();
        String code = tfdcode.getText();
        String montantText = tfmontant.getText();

        if (nom.isEmpty() || !nom.matches("^[a-zA-Z]*$")) {
            nomdErrorLabel.setText("Le nom est requis et doit contenir uniquement des caractères alphabétiques");
        }
        if (prenom.isEmpty() || !prenom.matches("^[a-zA-Z]*$")) {
            prenomdErrorLabel.setText("Le prénom est requis et doit contenir uniquement des caractères alphabétiques");
        }
        if (telephone.isEmpty() || !telephone.matches("\\d{8}")) {
            telephonedErrorLabel.setText("Le numéro de téléphone doit contenir 8 chiffres");
        }
        if (mail.isEmpty() || !mail.matches("[A-Za-z0-9._%+-]+@[A-Za-z0.9.-]+\\.[A-Za-z]{2,4}")) {
            maildErrorLabel.setText("L'adresse e-mail n'est pas valide");
        }
        if (address.isEmpty()) {
            adressErrorLabel.setText("L'adresse est requise");
        }
        if (code.isEmpty()) {
            codeErrorLabel.setText("Le code est requis");
        }

        if (montantText.isEmpty() || !montantText.matches("^\\d+$")) {
            montantErrorLabel.setText("Le montant doit être un entier positif");
        }

        if (nomdErrorLabel.getText().isEmpty() && prenomdErrorLabel.getText().isEmpty() &&
            telephonedErrorLabel.getText().isEmpty() && maildErrorLabel.getText().isEmpty() &&
            adressErrorLabel.getText().isEmpty() && codeErrorLabel.getText().isEmpty() &&
            montantErrorLabel.getText().isEmpty()) {
            // Toutes les validations ont réussi, créez l'objet DON et appelez le service.
            DON donation = new DON(montantText, telephone, address, code, nom, prenom, mail);
            donationService.create(donation);
            showSuccessMessage("Donation envoyée avec succès!");
        }
    }

  private void showSuccessMessage(String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Succès");
    alert.setHeaderText(null);
    alert.setContentText(message);
    
    // Ajoutez un bouton "OK" à la boîte de dialogue
    ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.OK_DONE);
    alert.getButtonTypes().setAll(buttonTypeOK);

    // Affichez la boîte de dialogue et attendez la réponse de l'utilisateur
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == buttonTypeOK && message.contains("Donation envoyée avec succès")) {
        // Charger le nouveau fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paiment1.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la scène actuelle
        Stage stage = (Stage) tfdnom.getScene().getWindow();

        // Afficher la nouvelle scène
        stage.setScene(scene);
    }
}


    @FXML
    private void fermer(ActionEvent event) throws IOException {
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

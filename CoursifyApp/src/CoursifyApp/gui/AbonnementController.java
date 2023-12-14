/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import CoursifyApp.entities.Abonnement;
import CoursifyApp.services.AbonnementService;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AbonnementController implements Initializable {

    @FXML
    private TextField nomuser;
    @FXML
    private TextField course;
    @FXML
    private TextField courseprice;
    @FXML
    private DatePicker datedebutdabonnement;
    @FXML
    private DatePicker dateexpirationdabnnement;
    @FXML
    private ComboBox<String> abonnementtype;
    @FXML
    private Button annuleAbon;
    @FXML
    private Button validerAbon;
    @FXML
    private Button btngh;
    @FXML
    private Pane pnlstatus;
    @FXML
    private Label lblstatusMini;
    @FXML
    private Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // You can set up the ComboBox (abonnementtype) with subscription types here
        // For example:
        abonnementtype.getItems().addAll("Type 1", "Type 2", "Type 3");
    }    

    @FXML
    private void valider(ActionEvent event) throws IOException {
    String userName = nomuser.getText();
    String selectedCourse = course.getText();
    String coursePriceText = courseprice.getText();
    String selectedAbonnementType = abonnementtype.getValue();
    LocalDate dateDebut = datedebutdabonnement.getValue();
    LocalDate dateExpiration = dateexpirationdabnnement.getValue();

    if (userName.isEmpty() || selectedCourse.isEmpty() || coursePriceText.isEmpty() || selectedAbonnementType == null || dateDebut == null || dateExpiration == null) {
        // Display an error notification for empty fields
        Notifications.create()
            .title("Input Error")
            .text("Please fill in all the required fields.")
            .darkStyle()
            .position(Pos.TOP_RIGHT)
            .hideAfter(Duration.seconds(5))
            .showError();
        return; // Exit the method without adding the Abonnement if any field is missing
    }

    try {
        float price = Float.parseFloat(coursePriceText);

        // Create the Abonnement object
        Abonnement newAbonnement = new Abonnement( userName, selectedCourse, selectedAbonnementType, price,
            Date.from(dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant()), 
            Date.from(dateExpiration.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // Add the Abonnement to the database
        AbonnementService abonnementService = new AbonnementService();
        abonnementService.create(newAbonnement);

        // Display a success notification
        Notifications.create()
            .title("Abonnement Added")
            .text("Abonnement added successfully")
            .darkStyle()
            .position(Pos.TOP_RIGHT)
            .hideAfter(Duration.seconds(5))
            .showInformation();

        // Clear input fields
        nomuser.clear();
        course.clear();
        courseprice.clear();
        datedebutdabonnement.getEditor().clear();
        dateexpirationdabnnement.getEditor().clear();
        abonnementtype.getSelectionModel().clearSelection();

        System.out.println("Abonnement added successfully");
    } catch (NumberFormatException e) {
        // Handle the case where courseprice cannot be parsed as a float (invalid input)
        // Display an error notification for invalid input
        Notifications.create()
            .title("Input Error")
            .text("Please enter a valid course price (e.g., 100.00).")
            .darkStyle()
            .position(Pos.TOP_RIGHT)
            .hideAfter(Duration.seconds(5))
            .showError();
    }
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    




    @FXML
    private void annuleAbon(ActionEvent event) {
        // Implement the action to cancel the Abonnement creation or clear the input fields
        // For example, you can clear the input fields:
        nomuser.clear();
        course.clear();
        courseprice.clear();
        datedebutdabonnement.getEditor().clear();
        dateexpirationdabnnement.getEditor().clear();
        abonnementtype.getSelectionModel().clearSelection();
    }

    @FXML
private void gohomefromabonnement(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();

        // Create a new stage for the home page
        Stage mainPageStage = new Stage();
        mainPageStage.setScene(new Scene(root));

        // Set the title of the home page window
        mainPageStage.setTitle("Dashboard");

        // Close the current stage (Abonnement page)
        Stage currentStage = (Stage) btngh.getScene().getWindow();
        currentStage.close();

        // Show the home page window
        mainPageStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}

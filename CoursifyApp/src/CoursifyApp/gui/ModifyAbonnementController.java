/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import java.net.URL;
import java.util.ResourceBundle;

import CoursifyApp.entities.Abonnement;
import CoursifyApp.services.AbonnementService;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author hassa
 */
public class ModifyAbonnementController implements Initializable {

    @FXML
    private TextField nomusermodif;
    @FXML
    private TextField coursemodif;
    @FXML
    private TextField coursepricem;
    @FXML
    private DatePicker datedebutdabonnementm;
    @FXML
    private DatePicker dateexpirationdabnnementm;
    @FXML
    private ComboBox<String> abonnementtypem;
    @FXML
    private Button modificationButton;
    @FXML
    private Button annuleAbon;
    private Abonnement currentAbonnement; // Store the current Abonnement


    public void initData(Abonnement abonnement) {
        currentAbonnement = abonnement; // Store the current Abonnement
        // Set UI components with Abonnement data
        nomusermodif.setText(abonnement.getUser());
        coursemodif.setText(abonnement.getCours());
        coursepricem.setText(String.valueOf(abonnement.getPrice()));
        datedebutdabonnementm.setValue(convertToLocalDate(abonnement.getDateDebut()));
        dateexpirationdabnnementm.setValue(convertToLocalDate(abonnement.getDateExpiration()));
        abonnementtypem.setValue(abonnement.getTypeAbonnement());
    }
    /**
     * Initializes the controller class.
     */
     
       private LocalDate convertToLocalDate(java.util.Date dateToConvert) {
    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void modification(ActionEvent event) {
    // Gather the updated data from the UI components
    String userName = nomusermodif.getText();
    String selectedCourse = coursemodif.getText();
    double price = Double.parseDouble(coursepricem.getText());
    LocalDate dateDebut = datedebutdabonnementm.getValue();
    LocalDate dateExpiration = dateexpirationdabnnementm.getValue();
    String typeAbonnement = abonnementtypem.getValue().toString();

    // Update the currentAbonnement object
    currentAbonnement.setUser(userName);
    currentAbonnement.setCours(selectedCourse);
    currentAbonnement.setPrice((float) price);
    currentAbonnement.setDateDebut(Date.valueOf(dateDebut));
    currentAbonnement.setDateExpiration(Date.valueOf(dateExpiration));
    currentAbonnement.setTypeAbonnement(typeAbonnement);

    // Update the Abonnement object in the database
    AbonnementService abonnementService = new AbonnementService();
    abonnementService.update(currentAbonnement);

    // Show a notification or message to confirm the update.
    showNotification("Abonnement updated successfully.");
}

    @FXML
    private void annuleAbon(ActionEvent event) {
    }

    private void showNotification(String message) {
        Notifications.create()
                .title("Notification")
                .text(message)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5))
                .showInformation();
    }
    
}

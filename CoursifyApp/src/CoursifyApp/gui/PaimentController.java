
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Payment;
import CoursifyApp.services.PaymentService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class PaimentController implements Initializable {

    @FXML
    private Label datlabel;
    @FXML
    private Label cardlabel;
    @FXML
    private Label expirationlabel;
    @FXML
    private TextField numcarte;
    @FXML
    private TextField EightCard;
    @FXML
    private DatePicker dateExp;
    @FXML
    private Button addpayment;
    @FXML
    private Button cancelpayment;

    /**
     * Initializes the controller class.
     */
@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addPayment(ActionEvent event) {
        clearErrorLabels();
        String cardNumber = numcarte.getText();
        LocalDate localDate = dateExp.getValue();
        String eightCard = EightCard.getText();

        boolean validInput = true;

        if (cardNumber.isEmpty()) {
            setErrorMessage(cardlabel, "Card Number is required");
            validInput = false;
        } else if (!isValidCardNumber(cardNumber)) {
            setErrorMessage(cardlabel, "Invalid Card Number");
            validInput = false;
        }

        if (localDate == null) {
            setErrorMessage(expirationlabel, "Expiration Date is required");
            validInput = false;
        } else {
            Date expirationDate = java.sql.Date.valueOf(localDate);
            // Effectuer des vérifications similaires pour les autres champs

            if (validInput) {
                try {
                    Payment payment = new Payment(cardNumber, expirationDate, eightCard);
                    PaymentService paymentService = new PaymentService();
                    paymentService.create(payment);
                    clearInputFields();
                } catch (NumberFormatException e) {
                    System.err.println("Unexpected error while creating the payment: " + e.getMessage());
                }
            }
        }
    }
    
    private void clearInputFields() {
    numcarte.clear();
    dateExp.getEditor().clear();
    EightCard.clear();
}
    
    private void clearErrorLabels() {
        datlabel.setText("");
        cardlabel.setText("");
        expirationlabel.setText("");
        // Clear labels for other fields as well
    }
    
    private void setErrorMessage(Label label, String message) {
        label.setText(message);
        label.setStyle("-fx-text-fill: red;");
    }
    
    private boolean isValidCardNumber(String cardNumber) {
        // Add your validation logic for card numbers here
        return cardNumber.matches("^[0-9]{16}$");
    }


        
    

       @FXML
    private void cancelPayment(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Abonnement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) numcarte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading the Abonnement interface: " + e.getMessage());
        }
    }
}
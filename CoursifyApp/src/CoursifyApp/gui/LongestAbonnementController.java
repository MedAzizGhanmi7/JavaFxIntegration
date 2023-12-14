/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Abonnement;
import CoursifyApp.services.AbonnementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LongestAbonnementController {
    @FXML
    private Label userLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label dateDebutLabel;
    @FXML
    private Label dateExpirationLabel;

    @FXML
    private void findLongestAbonnement(ActionEvent event) {
        AbonnementService abonnementService = new AbonnementService();
        Abonnement longestAbonnement = abonnementService.findLongestAbonnement();

        if (longestAbonnement != null) {
            userLabel.setText("User: " + longestAbonnement.getUser());
            courseLabel.setText("Course: " + longestAbonnement.getCours());
            typeLabel.setText("Type: " + longestAbonnement.getTypeAbonnement());
            priceLabel.setText("Price: " + longestAbonnement.getPrice());
            dateDebutLabel.setText("Date Debut: " + longestAbonnement.getDateDebut());
            dateExpirationLabel.setText("Date Expiration: " + longestAbonnement.getDateExpiration());
        }
    }
}


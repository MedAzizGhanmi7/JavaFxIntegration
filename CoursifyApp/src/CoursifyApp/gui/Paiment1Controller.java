/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Paiment1Controller implements Initializable {

    @FXML
    private JFXButton payy;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Gérer l'événement ici
        payy.setOnAction(event -> {
            Stage interfaceCourante = (Stage) payy.getScene().getWindow();
            interfaceCourante.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("paiment2.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage nouvelleFenetre = new Stage();
                nouvelleFenetre.setScene(scene);
                nouvelleFenetre.setTitle("Paiement 2");
                nouvelleFenetre.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}







    
    


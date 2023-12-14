/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class paiment2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paiment2.fxml")); // Remplacez "votre_fichier_fxml.fxml" par le chemin de votre fichier FXML.
        Parent root = loader.load();
        Paiment2Controller controller = loader.getController();

        // Remplissez les champs de texte (date, cvv, tele, rib) si nécessaire :
      

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

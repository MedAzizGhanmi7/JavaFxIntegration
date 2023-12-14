/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hassa
 */
public class DashboardControler implements Initializable {

    @FXML
    private AnchorPane content_areaFront;
    @FXML
    private AnchorPane course_form;
    @FXML
    private ScrollPane course_scrollPane;
    @FXML
    private GridPane course_gridPane;
    @FXML
    private Button sedeconnecter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void open_listeCourse(MouseEvent event) throws  IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("ListeCourse.fxml"));
        course_gridPane.getChildren().removeAll();
        course_gridPane.getChildren().setAll(fxml);
    }

    @FXML
    private void open_listeReview(MouseEvent event) throws  IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("#"));
        course_gridPane.getChildren().removeAll();
        course_gridPane.getChildren().setAll(fxml);
    }

    @FXML
    private void disconect(ActionEvent event) {
        
            try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Create a new scene for the home page
        Stage mainPageStage = new Stage();
        mainPageStage.setScene(new Scene(root));

        // Set the title of the home page window
        mainPageStage.setTitle("Home Page");

        // Close the current scene (ReviewHomePage)
        Stage currentStage = (Stage) sedeconnecter.getScene().getWindow();
        currentStage.close();

        // Show the home page window
        mainPageStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
}

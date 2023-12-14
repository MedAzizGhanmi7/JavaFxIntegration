/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author hassa
 */
public class CourseMainPage extends Application {



    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the CourseMainPage FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseMainPage.fxml"));
        Parent root = loader.load();

        // Get the controller and set the stage
        CourseMainPageController controller = loader.getController();
        controller.setStage(primaryStage);

        // Set the stage for the main page
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Course Main Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
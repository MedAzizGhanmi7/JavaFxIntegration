
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import CoursifyApp.entities.TwilioSMS;
import com.twilio.Twilio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author radhw
 */



/***********************************************/
public class CourseMain extends Application {
    public static final String ACCOUNT_SID = "ACc3a71326ea77cad937017da841c1a175";
    public static final String AUTH_TOKEN = "864564c1c893f9c39cbc67bc45405818";

    private static String SENDER_PHONE_NUMBER = "12315257545"; // Replace with your Twilio phone number

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("Course.fxml"));
            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CourseMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        TwilioSMS.initialize(ACCOUNT_SID, AUTH_TOKEN, SENDER_PHONE_NUMBER);

        launch(args);
    }
}
    

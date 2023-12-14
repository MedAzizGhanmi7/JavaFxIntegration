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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghanm
 */
public class InstructorDashboardFXMLController implements Initializable {

    @FXML
    private MenuBar adminNavBar;
    @FXML
    private Menu profile;
    @FXML
    private MenuItem gotoprofile;
    @FXML
    private Menu courses;
    @FXML
    private MenuItem viewCourses;
    @FXML
    private Menu claim;
    @FXML
    private MenuItem makeClaim;
    @FXML
    private Menu notifications;
    @FXML
    private MenuItem gotonotifications;
    @FXML
    private Button signOutButton;
    private int userId;
    private String userRole;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Menu message;
    @FXML
    private MenuItem sendMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            String cssFile = getClass().getResource("login.css").toExternalForm();
            anchorPane.getStylesheets().add(cssFile);
        // TODO
    }    
    
    public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
    }
    @FXML
    private void goToInstructorProfile(ActionEvent event) {
        System.out.println("goInstructorprofile called");
        try {

            // Load the AdminProfile.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructorProfile.fxml"));
            Parent InstructorProfileRoot = loader.load();

            // Pass the user ID and role to the AdminProfileController if needed
            InstructorProfileController studentProfileController = loader.getController();
            studentProfileController.initData(userId, userRole);

            // Create a new scene
            Scene instructorProfileScene = new Scene(InstructorProfileRoot);

            // Get the current stage
            Stage currentStage = (Stage) adminNavBar.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(instructorProfileScene);
            currentStage.setTitle("Instructor Profile");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    @FXML
    private void findMyCourses(ActionEvent event) {
            System.out.println("go to courses");
        try {

            // Load the AdminProfile.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseMainPage.fxml"));
            Parent InstructorProfileRoot = loader.load();

            // Pass the user ID and role to the AdminProfileController if needed
            CourseMainPageController studentProfileController = loader.getController();
            studentProfileController.initData(userId, userRole);

            // Create a new scene
            Scene instructorProfileScene = new Scene(InstructorProfileRoot);

            // Get the current stage
            Stage currentStage = (Stage) adminNavBar.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(instructorProfileScene);
            currentStage.setTitle("Instructor Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
        
    }

    @FXML
    private void goToMakeClaim(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("ajout.fxml"));
            Parent InstructorProfileRoot = loader.load();

            // Pass the user ID and role to the AdminProfileController if needed
            

            // Create a new scene
            Scene instructorProfileScene = new Scene(InstructorProfileRoot);

            // Get the current stage
            Stage currentStage = (Stage) adminNavBar.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(instructorProfileScene);
            currentStage.setTitle("Instructor Dashboard");
            currentStage.show();
        
            // Handle any exceptions that may occur during loading
        }
        
    
    

    @FXML
    private void goToNotifications(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("dajout.fxml"));
            Parent InstructorProfileRoot = loader.load();

            // Pass the user ID and role to the AdminProfileController if needed
            

            // Create a new scene
            Scene instructorProfileScene = new Scene(InstructorProfileRoot);

            // Get the current stage
            Stage currentStage = (Stage) adminNavBar.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(instructorProfileScene);
            currentStage.setTitle("Instructor Dashboard");
            currentStage.show();
        } 
        
    
    


    @FXML
    private void signOut(ActionEvent event) {
                 try {
            // Load the Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loader.load();

            // Create a new scene
            Scene loginScene = new Scene(loginRoot);

            // Get the current stage
            Stage currentStage = (Stage) signOutButton.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    
    
    @FXML
    private void goToNoMessage(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("SendMessage.fxml"));
            Parent InstructorProfileRoot = loader.load();

      
            
            Scene instructorProfileScene = new Scene(InstructorProfileRoot);

            // Get the current stage
            Stage currentStage = (Stage) adminNavBar.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(instructorProfileScene);
            currentStage.setTitle("Instructor Profile");
            currentStage.show();
        } 
    
}

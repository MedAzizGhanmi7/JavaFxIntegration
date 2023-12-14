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
public class StudentDashboardFXMLController implements Initializable {

    @FXML
    private MenuBar adminNavBar;
    @FXML
    private Menu profile;
    @FXML
    private MenuItem gotoprofile;
    @FXML
    private Menu store;
    @FXML
    private MenuItem findCourses;
    @FXML
    private Menu claim;
    @FXML
    private MenuItem makeClaim;
    @FXML
    private Menu notifications;
    @FXML
    private MenuItem gotonotifications;
    private int userId;
    private String userRole;
    @FXML
    private Button signOutButton;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            String cssFile = getClass().getResource("login.css").toExternalForm();
            anchorPane.getStylesheets().add(cssFile);
    }

    public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
    }

    @FXML
    private void goToStudentProfile(ActionEvent event) {
        System.out.println("goStudentrofile called");
        try {

            // Load the AdminProfile.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentProfile.fxml"));
            Parent studentProfileRoot = loader.load();

            // Pass the user ID and role to the AdminProfileController if needed
            StudentProfileController studentProfileController = loader.getController();
            studentProfileController.initData(userId, userRole);

            // Create a new scene
            Scene studentProfileScene = new Scene(studentProfileRoot);

            // Get the current stage
            Stage currentStage = (Stage) adminNavBar.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(studentProfileScene);
            currentStage.setTitle("Student Profile");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }
@FXML
private void findCourses(ActionEvent event) {
    try {
        // Load the dashboard.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent dashboardRoot = loader.load();

        // Pass any necessary data to the dashboard controller if needed
        DashboardControler dashboardController = loader.getController();
        // You can call any initialization method on the dashboardController if necessary

        // Create a new scene
        Scene dashboardScene = new Scene(dashboardRoot);

        // Get the current stage
        Stage currentStage = (Stage) adminNavBar.getScene().getWindow();

        // Set the new scene on the current stage
        currentStage.setScene(dashboardScene);
        currentStage.setTitle("Dashboard"); // You can set a title for the dashboard if needed
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
            currentStage.setTitle("");
            currentStage.show();
        
            // Handle any exceptions that may occur during loading
        }

    @FXML
    private void goToNotifications(ActionEvent event) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("dajout.fxml"));
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
            currentStage.setTitle("");
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
    }


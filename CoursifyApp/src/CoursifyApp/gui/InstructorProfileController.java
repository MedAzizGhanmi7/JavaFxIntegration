/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Instructor;

import CoursifyApp.services.InstructorService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghanm
 */
public class InstructorProfileController implements Initializable {

    @FXML
    private ImageView profilePicImageview;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label lastName;
    @FXML
    private Label gender;
    @FXML
    private Label instructorType;
    @FXML
    private Label registrationDate;
    @FXML
    private Label password;
    @FXML
    private Label firstName;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label adress;
    @FXML
    private Label phoneNumber;
    @FXML
    private Hyperlink deleteAccLink;
    @FXML
    private Button editProfilebtn;
    @FXML
    private Label bio;
    @FXML
    private Label yearsOfExperience;
    private int userId;
    private String userRole;
    private String profileImagePath;
    private Image profileImage;
    private Instructor instructor;
    @FXML
    private AnchorPane Anchorpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                             String cssFile = getClass().getResource("login.css").toExternalForm();
            Anchorpane.getStylesheets().add(cssFile);
        // TODO
        if (profileImage == null && profileImagePath != null && !profileImagePath.isEmpty()) {
        profileImage = new Image("file:" + profileImagePath);
        profilePicImageview.setImage(profileImage);
    }
        
        instructor = null ;
    }   
    
     public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;

        // Load admin data based on userId
        InstructorService instructorService = new InstructorService();
        this.instructor = instructorService.getById(userId); // Retrieve the Admin object
        
      
       

        // Update labels with admin data
        username.setText(instructor.getUsername());
        email.setText(instructor.getEmail());
        lastName.setText(instructor.getLastName());
        gender.setText(instructor.getGender());
        instructorType.setText(instructor.getUserType());
        registrationDate.setText(instructor.getRegistrationDate().toString());
        password.setText(instructor.getPassword());
        firstName.setText(instructor.getFirstName());
        dateOfBirth.setText(instructor.getDateOfBirth().toString());
        adress.setText(instructor.getAddress());
        phoneNumber.setText(instructor.getPhoneNumber());
        bio.setText(instructor.getBio());
        yearsOfExperience.setText(""+instructor.getYearsOfExperience());

        // Load the profile picture
        profileImagePath  = instructor.getProfilePicture();

        // If profileImage is not loaded and profilePictureBytes is available, load the image from the bytes
          if (profileImage == null && profileImagePath != null && !profileImagePath.isEmpty()) {
        profileImage = new Image("file:" + profileImagePath);
        profilePicImageview.setImage(profileImage);
    }
    }
    @FXML
    private void goBackToInstructorDashboard(ActionEvent event) {
        try {
            // Load the AdminDashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructorDashboardFXML.fxml"));
            Parent instructorDashboardRoot = loader.load();

            // Create a new scene
            Scene instructorDashboardScene = new Scene(instructorDashboardRoot);

            // Get the controller of the dashboard
            InstructorDashboardFXMLController dashboardController = loader.getController();

            // Pass the user ID and role back to the dashboard
            dashboardController.initData(userId, userRole);

            // Set the new scene on the current stage
            Stage currentStage = (Stage) profilePicImageview.getScene().getWindow();
            currentStage.setScene(instructorDashboardScene);
            currentStage.setTitle("Instructor Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
        
    }

    @FXML
    private void deleteMyacc(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Delete Account");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("This action cannot be undone.");

        // Show the dialog and wait for a user response
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            // User confirmed the deletion, so delete the account
            deleteAccount();

            // Redirect to the login interface
            redirectToLogin();
        }
    }

    @FXML
    private void editProfile(ActionEvent event) {
             try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAccountInstructor.fxml"));
            Parent editAccountInsctructorRoot = loader.load();

            EditAccountInstructorController editController = loader.getController();
            
          
            editController.initData(userId, userRole);
          

            Stage currentStage = (Stage) editProfilebtn.getScene().getWindow();
            Scene editAccountAdminScene = new Scene(editAccountInsctructorRoot);

            currentStage.setScene(editAccountAdminScene);
            currentStage.setTitle("Edit Instructor Profile");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
      
    }

    private void deleteAccount() {
       InstructorService instructorService = new InstructorService();
        instructorService.delete(userId); // Delete the admin account based on the user ID    }    
    }

    private void redirectToLogin() {
   try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);

            Stage currentStage = (Stage) profilePicImageview.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }    }    
    
}

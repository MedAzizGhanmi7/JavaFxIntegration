/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Student;
import CoursifyApp.services.AdminService;
import CoursifyApp.services.StudentService;
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
public class StudentProfileController implements Initializable {

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
    private Label StudentType;
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
    private Label major;
    @FXML
    private Label currentGradeLevel;
    
     private int userId;
    private String userRole;
    private String profileImagePath;
    private Image profileImage;
    private Student student;
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
        
        student = null ;
    }    

    @FXML
    private void goBackToStudentDashboard(ActionEvent event) {
           try {
            // Load the AdminDashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboardFXML.fxml"));
            Parent studentDashboardRoot = loader.load();

            // Create a new scene
            Scene studentDashboardScene = new Scene(studentDashboardRoot);

            // Get the controller of the dashboard
            StudentDashboardFXMLController dashboardController = loader.getController();

            // Pass the user ID and role back to the dashboard
            dashboardController.initData(userId, userRole);

            // Set the new scene on the current stage
            Stage currentStage = (Stage) profilePicImageview.getScene().getWindow();
            currentStage.setScene(studentDashboardScene);
            currentStage.setTitle("Admin Dashboard");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAccountStudent.fxml"));
            Parent editAccountStudentRoot = loader.load();

            EditAccountStudentController editController = loader.getController();
            
          
            editController.initData(userId, userRole);
          

            Stage currentStage = (Stage) editProfilebtn.getScene().getWindow();
            Scene editAccountAdminScene = new Scene(editAccountStudentRoot);

            currentStage.setScene(editAccountAdminScene);
            currentStage.setTitle("Edit Student Profile");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;

        // Load admin data based on userId
        StudentService studentService = new StudentService();
        this.student = studentService.getById(userId); // Retrieve the Admin object
        
      
       

        // Update labels with admin data
        username.setText(student.getUsername());
        email.setText(student.getEmail());
        lastName.setText(student.getLastName());
        gender.setText(student.getGender());
        StudentType.setText(student.getUserType());
        registrationDate.setText(student.getRegistrationDate().toString());
        password.setText(student.getPassword());
        firstName.setText(student.getFirstName());
        dateOfBirth.setText(student.getDateOfBirth().toString());
        adress.setText(student.getAddress());
        phoneNumber.setText(student.getPhoneNumber());
        major.setText(student.getMajor());
        currentGradeLevel.setText(student.getCurrentGradeLevel());

        // Load the profile picture
        profileImagePath  = student.getProfilePicture();

        // If profileImage is not loaded and profilePictureBytes is available, load the image from the bytes
        if (profileImage == null && profileImagePath != null && !profileImagePath.isEmpty()) {
        profileImage = new Image("file:" + profileImagePath);
        profilePicImageview.setImage(profileImage);
            }   
    }

    private void deleteAccount() {
         StudentService studentService = new StudentService();
        studentService.delete(userId); // Delete the admin account based on the user ID    }

    
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

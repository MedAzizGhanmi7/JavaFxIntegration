package CoursifyApp.gui;
import CoursifyApp.entities.Admin;
import CoursifyApp.gui.AdminDashboardFXMLController;
import CoursifyApp.gui.EditAccountAdminController;
import CoursifyApp.services.AdminService;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminProfileController implements Initializable {

    @FXML
    private ImageView profilePicImageview;
    @FXML
    private Label adminUsername;
    @FXML
    private Label adminEmail;
    @FXML
    private Label adminLastName;
    @FXML
    private Label adminGender;
    @FXML
    private Label AdminType;
    @FXML
    private Label adminRegistrationDate;
    @FXML
    private Label adminPassword;
    @FXML
    private Label adminFirstName;
    @FXML
    private Label adminDateOfBirth;
    @FXML
    private Label adminAdress;
    @FXML
    private Label adminPhoneNumber;

    private int userId;
    private String userRole;
    private String profileImagePath;

    private Image profileImage;
    @FXML
    private Hyperlink deleteAccLink;
    @FXML
    private Button editProfilebtn;

    // Initialize admin as null initially
    private Admin admin ;
    @FXML
    private AnchorPane Anchorpane;

   @Override
public void initialize(URL url, ResourceBundle rb) {
                         String cssFile = getClass().getResource("login.css").toExternalForm();
            Anchorpane.getStylesheets().add(cssFile);
    if (profileImage == null && profileImagePath != null && !profileImagePath.isEmpty()) {
        profileImage = new Image("file:" + profileImagePath);
        profilePicImageview.setImage(profileImage);
    }
    admin = null;
}


    public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;

        // Load admin data based on userId
        AdminService adminService = new AdminService();
        this.admin = adminService.getById(userId); // Retrieve the Admin object
        
      
       

        // Update labels with admin data
        adminUsername.setText(admin.getUsername());
        adminEmail.setText(admin.getEmail());
        adminLastName.setText(admin.getLastName());
        adminGender.setText(admin.getGender());
        AdminType.setText(admin.getUserType());
        adminRegistrationDate.setText(admin.getRegistrationDate().toString());
        adminPassword.setText(admin.getPassword());
        adminFirstName.setText(admin.getFirstName());
        adminDateOfBirth.setText(admin.getDateOfBirth().toString());
        adminAdress.setText(admin.getAddress());
        adminPhoneNumber.setText(admin.getPhoneNumber());

        // Load the profile picture
         profileImagePath = admin.getProfilePicture();

        // If profileImage is not loaded and profilePictureBytes is available, load the image from the bytes
          if (profileImage == null && profileImagePath != null && !profileImagePath.isEmpty()) {
        profileImage = new Image("file:" + profileImagePath);
        profilePicImageview.setImage(profileImage);
                                                       }
    }

    @FXML
    private void goBackToAdminDashboard(ActionEvent event) {
        try {
            // Load the AdminDashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboardFXML.fxml"));
            Parent adminDashboardRoot = loader.load();

            // Create a new scene
            Scene adminDashboardScene = new Scene(adminDashboardRoot);

            // Get the controller of the dashboard
            AdminDashboardFXMLController dashboardController = loader.getController();

            // Pass the user ID and role back to the dashboard
            dashboardController.initData(userId, userRole);

            // Set the new scene on the current stage
            Stage currentStage = (Stage) profilePicImageview.getScene().getWindow();
            currentStage.setScene(adminDashboardScene);
            currentStage.setTitle("Admin Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    @FXML
    private void deleteMyacc(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
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

    private void deleteAccount() {
        AdminService adminService = new AdminService();
        adminService.delete(userId); // Delete the admin account based on the user ID
    }

    // Method to redirect to the login interface
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
        }
    }

  @FXML
    private void editProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAccountAdmin.fxml"));
            Parent editAccountAdminRoot = loader.load();

            EditAccountAdminController editController = loader.getController();
            //editController.setAdmin(admin);
            System.out.println(admin.toString());
            editController.initData(userId, userRole);
          

            Stage currentStage = (Stage) editProfilebtn.getScene().getWindow();
            Scene editAccountAdminScene = new Scene(editAccountAdminRoot);

            currentStage.setScene(editAccountAdminScene);
            currentStage.setTitle("Edit Admin Profile");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package CoursifyApp.gui;

import CoursifyApp.services.AdminService;
import CoursifyApp.services.AuthService;
import CoursifyApp.services.InstructorService;
import CoursifyApp.services.StudentService;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.mail.Authenticator;
import com.twilio.rest.api.v2010.account.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;







public class LoginController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Hyperlink createAccountLink;
    @FXML
    private Hyperlink resetPass;

    private boolean emailExistsFlag = false; // Flag to track email existence
    private String authenticatedRole;
    private int authenticatedUserID; // Store the authenticated user's ID
    private String restRole;
    
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          String cssFile = getClass().getResource("login.css").toExternalForm();
            anchorPane.getStylesheets().add(cssFile);
    }
    
 

    @FXML
    private void login(ActionEvent event) {
        AuthService authService = new AuthService();
        String emailText = email.getText();
        String[] roles = {"admin", "student", "instructor"};
        AdminService as = new AdminService();
        StudentService ss = new StudentService();
        InstructorService is = new InstructorService();

        // Check if the email exists in any of the roles
        for (String role : roles) {
            if (role.equals("admin")) {
                emailExistsFlag = as.isEmailExists(emailText);
            } else if (role.equals("student")) {
                emailExistsFlag = ss.isEmailExists(emailText);
            } else if (role.equals("instructor")) {
                emailExistsFlag = is.isEmailExists(emailText);
            }

            if (emailExistsFlag) {
                // Email exists in one of the roles, break out of the loop
                break;
            }
        }

        if (!emailExistsFlag) {
            errorMessageLabel.setText("Email does not exist.");
            resetPass.setVisible(false);
            System.out.println("Email does not exist in any role.");
        } else {
            // Email exists, proceed to authenticate
            int id = -1;
            authenticatedRole = null;

            for (String role : roles) {
                id = authService.authenticate(emailText, password.getText(), role);
                if (id != -1) {
                    // Authentication successful, store the role and break out of the loop
                    authenticatedRole = role;
                    authenticatedUserID = id;
                    restRole =authenticatedRole;// Store the authenticated user's ID
                    break;
                }
            }

            if (id == -1) {
                errorMessageLabel.setText("Authentication failed.");
                resetPass.setVisible(true);
               
                
            } else {
                // Authentication successful, do something with the user ID and role
               
                System.out.println("Authenticated user ID: " + authenticatedUserID);
                System.out.println("Authenticated user role: " + authenticatedRole);
                
                
                

                // Redirect to the appropriate window based on the role
                if ("admin".equals(authenticatedRole)) {
                   
                   
                    openAdminDashboard(authenticatedUserID);
                 
                    
                } 
                
                
                
                else if ("student".equals(authenticatedRole)) {
                  
                   openStudentDashboard(authenticatedUserID);
                   
                   
                } else if ("instructor".equals(authenticatedRole)) {
                  
                    openInstructorDashboard(authenticatedUserID);
                
                }
                // Add similar handling for other roles if needed

                // Clear the error message and hide the "Forgot Password?" hyperlink
               /* errorMessageLabel.setText("");
                resetPass.setVisible(false);*/
            }
        }
    }

    // Helper method to open the Admin Dashboard with user ID and role
    private void openAdminDashboard(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboardFXML.fxml"));
            Parent adminDashboardRoot = loader.load();
            AdminDashboardFXMLController controller = loader.getController();
            controller.initData(userId, authenticatedRole);

            // Get the current stage
            Stage currentStage = (Stage) loginBtn.getScene().getWindow();

            // Create a new scene for the admin dashboard
            Scene adminDashboardScene = new Scene(adminDashboardRoot);

            // Set the new scene on the current stage
            currentStage.setScene(adminDashboardScene);
            currentStage.setTitle("Admin Dashboard");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    private void openStudentDashboard(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboardFXML.fxml"));
            Parent studentDashboardRoot = loader.load();
            StudentDashboardFXMLController controller = loader.getController();
            controller.initData(userId, authenticatedRole);

            // Get the current stage
            Stage currentStage = (Stage) loginBtn.getScene().getWindow();

            // Create a new scene for the admin dashboard
            Scene adminDashboardScene = new Scene(studentDashboardRoot);

            // Set the new scene on the current stage
            currentStage.setScene(adminDashboardScene);
            currentStage.setTitle("Student Dashboard");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    private void openInstructorDashboard(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructorDashboardFXML.fxml"));
            Parent instructorDashboardRoot = loader.load();
            InstructorDashboardFXMLController controller = loader.getController();
            controller.initData(userId, authenticatedRole);

            // Get the current stage
            Stage currentStage = (Stage) loginBtn.getScene().getWindow();

            // Create a new scene for the admin dashboard
            Scene instructorDashboardScene = new Scene(instructorDashboardRoot);

            // Set the new scene on the current stage
            currentStage.setScene(instructorDashboardScene);
            currentStage.setTitle("Instructor Dashboard");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    @FXML
    private void createAccLink(ActionEvent event) {
        try {
            // Load the CreateAccount.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
            Parent createAccountRoot = loader.load();

            // Get the current stage
            Stage currentStage = (Stage) loginBtn.getScene().getWindow();

            // Create a new scene for the create account page
            Scene createAccountScene = new Scene(createAccountRoot);

            // Set the new scene on the current stage
            currentStage.setScene(createAccountScene);
            currentStage.setTitle("Create Account");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    @FXML
    private void resetPassword(ActionEvent event) {
        
      
        String mail = email.getText();
       
        String pass = "";
        String phone="";
       

            AdminService as = new AdminService();
            pass = as.getPassByEmail(mail);
            phone = as.getPhoneNumberdByEmail(mail);
            if (pass !=null){
              sendPassViaSMS(phone, pass);
            showPasswordRecoveryAlert();
            return;
            }
            
              InstructorService is = new InstructorService();
             pass = is.getPassByEmail(mail);
             phone = is.getPhoneNumberdByEmail(mail);
            if (pass !=null){
          
            sendPassViaSMS(phone, pass);
            showPasswordRecoveryAlert();
            return;
            }
            
             StudentService ss = new StudentService();
            pass = ss.getPassByEmail(mail);
             phone = ss.getPhoneNumberdByEmail(mail);
            if (pass !=null){
            sendPassViaSMS(phone, pass);
            showPasswordRecoveryAlert();
            return;
            }
   
        }

     private void showPasswordRecoveryAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Recovery");
        alert.setHeaderText("Password Sent");
        alert.setContentText("We have sent your password via SMS.");
        alert.showAndWait();
    }
     
     private void sendPassViaSMS(String phoneNumber, String password) {
    final String accountSid = "AC3afa4061d2e4956156e8b281f2c08e3a";
    final String authToken = "681b803f14e63f3c8f6ba569f098f412";
    final String twilioPhoneNumber = "+17605383076"; // Your Twilio phone number

    Twilio.init(accountSid, authToken);

    String userPhoneNumber ="+216" + phoneNumber; // Make sure to format the phone number correctly
    LocalDateTime creationTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
            String formattedCreationTime = creationTime.format(formatter);
    // Send a message with the user's password via SMS
    Message message = Message.creator(
            new PhoneNumber(userPhoneNumber),
            new PhoneNumber(twilioPhoneNumber),
            "Your Coursify password is: " + password + " last login attempt :"+formattedCreationTime
    ).create();

    System.out.println("Password sent via SMS.");
}
     
     
     


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Instructor;
import CoursifyApp.services.AdminService;
import CoursifyApp.services.InstructorService;
import CoursifyApp.services.StudentService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghanm
 */
public class EditAccountInstructorController implements Initializable {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lasttName;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private RadioButton genderFemale;
    @FXML
    private RadioButton genderMale;
    private ToggleGroup toggleGroup;
    @FXML
    private Button saveBtn;
    @FXML
    private Label typeErrorLabel;
    @FXML
    private Label generalErrorLabel;
    @FXML
    private Button selectimgButton;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Button goBackbtn;
    @FXML
    private TextField yearsOfExperience;
    @FXML
    private TextArea bio;
    private int userId;
    private String userRole;
    private Image profileImage;
    private byte[] selectedImageData;
    private Instructor instructor;
    private String profileImagePath;
    @FXML
    private AnchorPane Anchorpane;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                   String cssFile = getClass().getResource("login.css").toExternalForm();
            Anchorpane.getStylesheets().add(cssFile);
        toggleGroup = new ToggleGroup();
        genderMale.setToggleGroup(toggleGroup);
        genderFemale.setToggleGroup(toggleGroup);
    }

    public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;

        InstructorService instructorService = new InstructorService();
        this.instructor = instructorService.getById(userId);

        username.setText(instructor.getUsername());
        email.setText(instructor.getEmail());
        lasttName.setText(instructor.getLastName());
        password.setText(instructor.getPassword());
        firstName.setText(instructor.getFirstName());
        address.setText(instructor.getAddress());
        phoneNumber.setText(instructor.getPhoneNumber());
        yearsOfExperience.setText("" + instructor.getYearsOfExperience());
        bio.setText(instructor.getBio());

        profileImagePath  = instructor.getProfilePicture();

        if (profileImage == null && profileImagePath != null && !profileImagePath.isEmpty()) {
        profileImage = new Image("file:" + profileImagePath);
        profileImageView.setImage(profileImage);
    }

        String gender = instructor.getGender();
        if ("Male".equalsIgnoreCase(gender)) {
            genderMale.setSelected(true);
        } else if ("Female".equalsIgnoreCase(gender)) {
            genderFemale.setSelected(true);
        }

        dateOfBirth.setValue(instructor.getDateOfBirth().toLocalDate());

        email.setEditable(false);
        username.setEditable(false);
        phoneNumber.setEditable(false);
    }

    @FXML
    private void SaveChanges(ActionEvent event) {
        if (isValidInputData()) {
            instructor.setFirstName(firstName.getText());
            instructor.setLastName(lasttName.getText());
            instructor.setPassword(password.getText());
            instructor.setAddress(address.getText());
            instructor.setBio(bio.getText());
            instructor.setYearsOfExperience(Integer.parseInt(yearsOfExperience.getText()));

            instructor.setGender(genderMale.isSelected() ? "Male" : "Female");
            instructor.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth.getValue()));

           if (profileImagePath != null) {
            instructor.setProfilePicture(profileImagePath);
        }
            InstructorService instructorService = new InstructorService();
            instructorService.update(instructor);

            showSuccessDialog("Profile Updated", "Your profile has been updated successfully.");
        }
    }

    @FXML
    private void selectImage(ActionEvent event) {
   FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        profileImagePath = selectedFile.getAbsolutePath();

        try {
            // Load and display the selected image in the ImageView
            Image image = new Image("file:" + profileImagePath);
            profileImageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message).
        }
    }
    }

    @FXML
    private void GoBacktoInstructorProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructorProfile.fxml"));
            Parent instructorProfileRoot = loader.load();
            InstructorProfileController ProfileController = loader.getController();
            ProfileController.initData(userId, userRole);

            Stage currentStage = (Stage) goBackbtn.getScene().getWindow();

            Scene instructorProfileScene = new Scene(instructorProfileRoot);

            currentStage.setScene(instructorProfileScene);
            currentStage.setTitle("Instructor Profile");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSuccessDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOK);

        alert.showAndWait();
    }

    private boolean isValidInputData() {
        if (isEmpty(firstName, "First name")) {
            return false;
        }
        if (isEmpty(lasttName, "Last name")) {
            return false;
        }
        if (isEmpty(password, "Password")) {
            return false;
        }
        if (isEmpty(address, "Address")) {
            return false;
        }
        if (isbioEmpty(bio, "bio")) {
            return false;
        }
        if (isEmpty(yearsOfExperience, "Experience")) {
            return false;
        }

        int years;
        try {
            years = Integer.parseInt(yearsOfExperience.getText());
            if (years < 0) {
                generalErrorLabel.setText("Years of experience should be a positive number.");
                generalErrorLabel.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            generalErrorLabel.setText("Years of experience must be a valid number.");
            generalErrorLabel.setVisible(true);
            return false;
        }

        if (!isStrongPassword(password.getText())) {
            generalErrorLabel.setText("Password must meet the strength requirements.");
            generalErrorLabel.setVisible(true);
            return false;
        }

        if (!isLettersOnly(firstName.getText())) {
            generalErrorLabel.setText("First name should contain only letters.");
            generalErrorLabel.setVisible(true);
            return false;
        }

        if (!isLettersOnly(lasttName.getText())) {
            generalErrorLabel.setText("Last name should contain only letters.");
            generalErrorLabel.setVisible(true);
            return false;
        }

        if (dateOfBirth.getValue() == null) {
            generalErrorLabel.setText("Please select your date of birth.");
            generalErrorLabel.setVisible(true);
            return false;
        }

        LocalDate selectedDate = dateOfBirth.getValue();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(selectedDate, currentDate).getYears();
        int minimumAge = 10;

        if (age < minimumAge) {
            generalErrorLabel.setText("You must be at least 10 years old to edit your profile.");
            generalErrorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean isEmpty(TextField field, String fieldName) {
        if (field.getText().trim().isEmpty()) {
            generalErrorLabel.setText(fieldName + " should not be empty.");
            generalErrorLabel.setVisible(true);
            return true;
        }
        return false;
    }

    private boolean isbioEmpty(TextArea field, String fieldName) {
        if (field.getText().trim().isEmpty()) {
            generalErrorLabel.setText(fieldName + " should not be empty.");
            generalErrorLabel.setVisible(true);
            return true;
        }
        return false;
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[!@#$%^&*()].*");
    }

    private boolean isLettersOnly(String value) {
        String regex = "^[a-zA-Z]+$";
        return value.matches(regex);
    }

}

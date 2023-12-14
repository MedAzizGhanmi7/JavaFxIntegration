package CoursifyApp.gui;

import CoursifyApp.entities.Admin;
import CoursifyApp.gui.AdminProfileController;
import CoursifyApp.services.AdminService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.scene.layout.AnchorPane;

public class EditAccountAdminController implements Initializable {

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
    private Label typeErrorLabel;
    @FXML
    private Label generalErrorLabel;
    @FXML
    private Button selectimgButton;
    @FXML
    private ImageView profileImageView;

    private String profileImagePath;


    private Admin admin;
    @FXML
    private Button saveBtn;
    @FXML
    private Button goBackbtn;

    private int userId;
    private String userRole;
    private Image profileImage;
    @FXML
    private AnchorPane Anchorpane;

    public void initData(int userId, String userRole) {
                   String cssFile = getClass().getResource("login.css").toExternalForm();
            Anchorpane.getStylesheets().add(cssFile);
        this.userId = userId;
        this.userRole = userRole;

        AdminService adminService = new AdminService();
        this.admin = adminService.getById(userId);

        username.setText(admin.getUsername());
        email.setText(admin.getEmail());
        lasttName.setText(admin.getLastName());
        password.setText(admin.getPassword());
        firstName.setText(admin.getFirstName());
        address.setText(admin.getAddress());
        phoneNumber.setText(admin.getPhoneNumber());

        profileImagePath  = admin.getProfilePicture();

        if (profileImage == null && profileImagePath != null && !profileImagePath.isEmpty()) {
        profileImage = new Image("file:" + profileImagePath);
        profileImageView.setImage(profileImage);
    }

        String gender = admin.getGender();
        if ("Male".equalsIgnoreCase(gender)) {
            genderMale.setSelected(true);
        } else if ("Female".equalsIgnoreCase(gender)) {
            genderFemale.setSelected(true);
        }

        dateOfBirth.setValue(admin.getDateOfBirth().toLocalDate());

        email.setEditable(false);
        username.setEditable(false);
        phoneNumber.setEditable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toggleGroup = new ToggleGroup();
        genderMale.setToggleGroup(toggleGroup);
        genderFemale.setToggleGroup(toggleGroup);
    }

    @FXML
    private void SaveChanges() {
        if (isValidInputData()) {
            admin.setFirstName(firstName.getText());
            admin.setLastName(lasttName.getText());
            admin.setPassword(password.getText());
            admin.setAddress(address.getText());
            admin.setGender(genderMale.isSelected() ? "Male" : "Female");
            admin.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth.getValue()));

          if (profileImagePath != null) {
            admin.setProfilePicture(profileImagePath);
        }

            AdminService adminService = new AdminService();
            adminService.update(admin);

            showSuccessDialog("Profile Updated", "Your profile has been updated successfully.");
        }
    }

    @FXML
    private void selectImage() {
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

    private boolean isValidInputData() {
        if (isEmpty(firstName, "First name")) return false;
        if (isEmpty(lasttName, "Last name")) return false;
        if (isEmpty(password, "Password")) return false;
        if (isEmpty(address, "Address")) return false;


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

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    private boolean isLettersOnly(String value) {
        String regex = "^[a-zA-Z]+$";
        return value.matches(regex);
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

    @FXML
    private void GoBacktoAdminProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminProfile.fxml"));
            Parent adminProfileRoot = loader.load();
            AdminProfileController adminProfileController = loader.getController();
            adminProfileController.initData(userId, userRole);

            Stage currentStage = (Stage) goBackbtn.getScene().getWindow();

            Scene adminProfileScene = new Scene(adminProfileRoot);

            currentStage.setScene(adminProfileScene);
            currentStage.setTitle("Admin Profile");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

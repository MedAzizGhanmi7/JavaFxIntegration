package CoursifyApp.gui;

import CoursifyApp.entities.Admin;
import CoursifyApp.entities.Instructor;
import CoursifyApp.entities.Student;
import CoursifyApp.services.AdminService;
import CoursifyApp.services.InstructorService;
import CoursifyApp.services.StudentService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class CreateAccountAdminController implements Initializable {

    private String phoneNumberValue;
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
    private Button CreateAccBtn;

    @FXML
    private Label typeErrorLabel;
    @FXML
    private Label generalErrorLabel;
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private Button selectimgButton;
    @FXML
    private ImageView profileImageView;
    private String  selectedImageData;
    @FXML
    private Button goBackbtn;
     private int userId;
    private String userRole;
    private Admin admin;
    @FXML
    private AnchorPane Anchorpane;
    
    public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;}


    @Override
    public void initialize(URL url, ResourceBundle rb) {
                     String cssFile = getClass().getResource("login.css").toExternalForm();
            Anchorpane.getStylesheets().add(cssFile);
        phoneNumberValue = "";
        toggleGroup = new ToggleGroup();
        genderMale.setToggleGroup(toggleGroup);
        genderFemale.setToggleGroup(toggleGroup);
        
      
     
         
            
    

        ObservableList<String> countries = FXCollections.observableArrayList(
                "AE +971",
                "AF +93",
                "AL +355",
                "AM +374",
                "AO +244",
                "AR +54",
                "AT +43",
                "AU +61",
                "AZ +994",
                "BA +387",
                "BB +1",
                "BD +880",
                "BE +32",
                "BF +226",
                "BG +359",
                "BH +973",
                "BI +257",
                "BJ +229",
                "BN +673",
                "BO +591",
                "BR +55",
                "BS +1",
                "BT +975",
                "BW +267",
                "BY +375",
                "BZ +501",
                "CA +1",
                "CD +243",
                "CF +236",
                "CG +242",
                "CH +41",
                "CI +225",
                "CL +56",
                "CM +237",
                "CN +86",
                "CO +57",
                "CR +506",
                "CU +53",
                "CV +238",
                "CY +357",
                "CZ +420",
                "DE +49",
                "DJ +253",
                "DK +45",
                "DM +1",
                "DO +1",
                "DZ +213",
                "EC +593",
                "EE +372",
                "EG +20",
                "ER +291",
                "ES +34",
                "ET +251",
                "FI +358",
                "FJ +679",
                "FM +691",
                "FR +33",
                "GA +241",
                "GB +44",
                "GE +995",
                "GH +233",
                "GR +30",
                "GT +502",
                "GW +245",
                "GY +592",
                "HN +504",
                "HR +385",
                "HT +509",
                "HU +36",
                "ID +62",
                "IE +353",
                "IL +972",
                "IN +91",
                "IQ +964",
                "IR +98",
                "IS +354",
                "IT +39",
                "JM +1",
                "JP +81",
                "KE +254",
                "KG +996",
                "KH +855",
                "KM +269",
                "KP +850",
                "KR +82",
                "KW +965",
                "KZ +7",
                "LA +856",
                "LB +961",
                "LI +423",
                "LK +94",
                "LR +231",
                "LS +266",
                "LT +370",
                "LU +352",
                "LV +371",
                "LY +218",
                "MA +212",
                "MC +377",
                "MD +373",
                "ME +382",
                "MG +261",
                "MK +389",
                "ML +223",
                "MM +95",
                "MN +976",
                "MR +222",
                "MT +356",
                "MU +230",
                "MW +265",
                "MX +52",
                "MY +60",
                "MZ +258",
                "NA +264",
                "NE +227",
                "NG +234",
                "NI +505",
                "NL +31",
                "NO +47",
                "NP +977",
                "NR +674",
                "NZ +64",
                "OM +968",
                "PA +507",
                "PE +51",
                "PG +675",
                "PH +63",
                "PK +92",
                "PL +48",
                "PT +351",
                "PW +680",
                "PY +595",
                "QA +974",
                "RO +40",
                "RS +381",
                "RU +7",
                "RW +250",
                "SA +966",
                "SB +677",
                "SC +248",
                "SD +249",
                "SE +46",
                "SG +65",
                "SI +386",
                "SK +421",
                "SL +232",
                "SM +378",
                "SN +221",
                "SO +252",
                "SR +597",
                "ST +239",
                "SV +503",
                "SY +963",
                "SZ +268",
                "TD +235",
                "TG +228",
                "TH +66",
                "TJ +992",
                "TL +670",
                "TM +993",
                "TN +216",
                "TO +676",
                "TR +90",
                "TT +1",
                "TW +886",
                "TZ +255",
                "UA +380",
                "UG +256",
                "US +1",
                "UY +598",
                "UZ +998",
                "VA +379",
                "VE +58",
                "VN +84",
                "VU +678",
                "WS +685",
                "ZA +27",
                "ZM +260",
                "ZW +263"
        );
        countryComboBox.setItems(countries);
        countryComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                phoneNumberValue = phoneNumber.getText();
                String selectedCountry = countryComboBox.getValue();

                if (selectedCountry != null && !phoneNumberValue.isEmpty()) {

                    phoneNumberValue = "+" + selectedCountry + phoneNumberValue;
                }
            }
        });
    }

    @FXML
    private void CreateAcc(ActionEvent event) {
        String selectedUserType = "admin";
        String selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        typeErrorLabel.setVisible(false);
        generalErrorLabel.setVisible(false);

        StudentService studentService = new StudentService();
        InstructorService instructorService = new InstructorService();
        AdminService adminService = new AdminService();
        if (studentService.isEmailExists(email.getText()) || instructorService.isEmailExists(email.getText()) || adminService.isEmailExists(email.getText())) {
            generalErrorLabel.setText("This email is already in use. Please use a different email.");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (studentService.isUsernameExists(username.getText()) || instructorService.isUsernameExists(username.getText())|| adminService.isUsernameExists(username.getText())) {
            generalErrorLabel.setText("This username is already in use. Please choose a different username.");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (studentService.isPhoneNumberExists(selectedCountry + " " + phoneNumber.getText()) || instructorService.isPhoneNumberExists(selectedCountry + " " + phoneNumber.getText())|| adminService.isPhoneNumberExists(selectedCountry + " " + phoneNumber.getText())) {
            generalErrorLabel.setText("This phone number is already in use. Please use a different phone number.");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (!isUsernameValid(username.getText())) {
            generalErrorLabel.setText("Username should consist of 4 to 20 letters and numbers.");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (!isStrongPassword(password.getText())) {
            String passwordRequirements = getPasswordRequirementsMessage(password.getText());
            generalErrorLabel.setText(passwordRequirements);
            generalErrorLabel.setVisible(true);
            return;
        }

        if (!isLettersOnly(firstName.getText())) {
            generalErrorLabel.setText("First name should contain only letters.");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (!isLettersOnly(lasttName.getText())) {
            generalErrorLabel.setText("Last name should contain only letters.");
            generalErrorLabel.setVisible(true);
            return;
        }
        int maxNameLength = 50;

        if (lasttName.getText().length() > maxNameLength || firstName.getText().length() > maxNameLength) {
            generalErrorLabel.setText("First name and last name should not exceed " + maxNameLength + " characters.");
            generalErrorLabel.setVisible(true);
            return;
        }

       
        if (selectedCountry == null || selectedCountry.isEmpty()) {
            typeErrorLabel.setText("Please select a Country");
            typeErrorLabel.setVisible(true);
            return;
        }
        if (!isValidPhoneNumber(phoneNumber.getText())) {
            generalErrorLabel.setText("Please enter a valid phone number");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (firstName.getText().isEmpty() || lasttName.getText().isEmpty()
                || username.getText().isEmpty() || email.getText().isEmpty()
                || password.getText().isEmpty() || address.getText().isEmpty()
                || phoneNumber.getText().isEmpty() /* || selectedImageData == null*/) { // Check if a valid image is selected
            generalErrorLabel.setText("Please fill in all required fields, including selecting a valid image");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (!isValidEmail(email.getText())) {
            generalErrorLabel.setText("Please enter a valid email");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (selectedImageData == null) {
            generalErrorLabel.setText("Please select profile picture");
            generalErrorLabel.setVisible(true);
            return;
        }

        if (toggleGroup.getSelectedToggle() == null) {
            generalErrorLabel.setText("Please select a gender");
            generalErrorLabel.setVisible(true);
            return;
        }
        if (dateOfBirth.getValue() == null) {
            generalErrorLabel.setText("Please select your date of birth");
            generalErrorLabel.setVisible(true);
            return;
        }
        LocalDate selectedDate = dateOfBirth.getValue();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(selectedDate, currentDate).getYears();

        int minimumAge = 10;

        if (age < minimumAge) {
            generalErrorLabel.setText("You must be at least " + minimumAge + " years old to create an account.");
            generalErrorLabel.setVisible(true);
            return;

        }
       
            Admin admin = new Admin();
            admin.setRegistrationDate(new Date(System.currentTimeMillis()));
            admin.setUsername(username.getText());
            admin.setPassword(password.getText());
            admin.setEmail(email.getText());
            admin.setDateOfBirth(Date.valueOf(dateOfBirth.getValue()));
            admin.setFirstName(firstName.getText());
            admin.setLastName(lasttName.getText());
            admin.setAddress(address.getText());
            admin.setProfilePicture(selectedImageData); // Store the image as a BLOB
            admin.setPhoneNumber(selectedCountry + " " + phoneNumber.getText());
            Toggle selectedToggle = toggleGroup.getSelectedToggle();
            if (selectedToggle != null) {
                RadioButton selectedRadioButton = (RadioButton) selectedToggle;
                String selectedValue = selectedRadioButton.getText();
                admin.setGender(selectedValue);
            }
            adminService.create(admin);
            sendAccountCreationEmail(email.getText(), "Admin");
            showSuccessDialog("Account Created", "Admin account has been successfully created.");
            

        

          }

    @FXML
    private void selectImage(ActionEvent event) 
    {
     FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        selectedImageData = selectedFile.getAbsolutePath();

        try {
            // Load and display the selected image in the ImageView
            Image image = new Image("file:" + selectedImageData);
            profileImageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message).
        }
    }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[A-Za-z]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Use this regex pattern to validate phone numbers with optional country code and spaces
        String regex = "^\\d{6,14}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private String getPasswordRequirementsMessage(String password) {
        StringBuilder requirements = new StringBuilder("Password should");

        if (password.length() < 8) {
            requirements.append("be at least 8 characters long");
        } else if (!password.matches(".*[A-Z].*")) {
            requirements.append("include at least one uppercase letter");
        } else if (!password.matches(".*[a-z].*")) {
            requirements.append("include at least one lowercase letter");
        } else if (!password.matches(".*\\d.*")) {
            requirements.append("include at least one digit\n");
        } else if (!password.matches(".*[!@#$%^&*()].*")) {
            requirements.append("include at least one special character (!@#$%^&*())");
        }

        return requirements.toString();
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

    private boolean isUsernameValid(String username) {
        // Define the username pattern: consists of letters and numbers, 4 to 20 characters long
        String regex = "^[a-zA-Z0-9]{4,20}$";
        return username.matches(regex);
    }

    private void sendAccountCreationEmail(String userEmail, String userType) {
        final String senderEmail = "medstudio29@gmail.com"; // Change this to your sender email
        final String senderPassword = "xxcc ynql cugu qtjk";    // Change this to your sender email password

        // Set up the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // You might need to change this based on your email provider
        props.put("mail.smtp.port", "587");           // You might need to change this based on your email provider
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a session with the sender's email and password
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Account Created Successfully");

            // Get the current time of creation
            LocalDateTime creationTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
            String formattedCreationTime = creationTime.format(formatter);

            // Include the time of creation in the email content
            String emailContent = "Your " + userType + " account has been successfully created on " + formattedCreationTime + ".";
            message.setText(emailContent);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Email sending failed.");
        }
    }
        private void showSuccessDialog(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Add a callback for the OK button in the dialog
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOK);

        // Get the result when the OK button is clicked
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == buttonTypeOK) {
            // User clicked OK, navigate to the admin dashboard
            switchToAdminDashboard();
        }
    }

    private void switchToAdminDashboard() {
        try {
            // Load the AdminDashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboardFXML.fxml"));
            Parent adminDashboardRoot = loader.load();
            AdminDashboardFXMLController Controller = loader.getController();
            Controller.initData(userId, userRole);

            // Get the current stage
            Stage currentStage = (Stage) CreateAccBtn.getScene().getWindow();

            // Create a new scene for the admin dashboard
            Scene adminDashboardScene = new Scene(adminDashboardRoot);

            // Set the new scene on the current stage
            currentStage.setScene(adminDashboardScene);
            currentStage.setTitle("Admin Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    @FXML
    private void GoBacktoDashboard(ActionEvent event) {
        switchToAdminDashboard();
    }
    


}

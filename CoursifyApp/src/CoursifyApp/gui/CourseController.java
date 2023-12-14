/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import CoursifyApp.entities.Course;
import CoursifyApp.entities.Instructor;
import CoursifyApp.entities.TwilioSMS;
import CoursifyApp.services.CourseService;
import CoursifyApp.services.InstructorService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author hassa
 */
public class CourseController implements Initializable {

    @FXML
    private DatePicker ccreated;
    @FXML
    private TextField cname;
    @FXML
    private TextField ctitle;
    @FXML
    private TextField cdiscription;
    @FXML
    private TextField cprice;
    @FXML
    private TextField cimage;
    @FXML
    private TextField countent;
    @FXML
    private CheckBox cactive;
    @FXML
    private Button cbtnadd;
    @FXML
    private Button cbtnupload;
    @FXML
    private Label instructorLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label createdatLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label imageLabel;
    @FXML
    private Pane pnlstatus;
    @FXML
    private Label lblstatusMini;
    @FXML
    private Label lblStatus;
    private Stage stage; // The stage for this controller
    @FXML
    private Button buttonghome;
    private Instructor instructor ;
    
     private File selectedImageFile;
    private String imageName = null;
    private int nomTest = 0;
    private int descriptionTest = 0;
    private int validiteTest = 0;
    private int priceTest = 0;
    private int photoTest = 0;
    
     private int userId;
    private String userRole;
     public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
        InstructorService is = new InstructorService();
    Instructor instructor = is.getById(userId);
    long instructorId = instructor.getId();
    }
     public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
javafx.util.Duration duration = javafx.util.Duration.seconds(5); // Use the fully qualified class name



    /*
    @FXML
    private void addCourse(ActionEvent event) {
        String title = ctitle.getText();
        String description = cdiscription.getText();
        float price = Float.parseFloat(cprice.getText());
        String image = cimage.getText();
        boolean isActive = cactive.isSelected(); // Checkbox value
        List<String> courseContent = Arrays.asList(countent.getText().split(",")); // Assuming course content is comma-separated
        String instructorName = cname.getText();

        // Create a new Course instance with current 'createdAt' and 'updatedAt'
        Date currentDate = new Date(System.currentTimeMillis());
        Course newCourse = new Course(title, description, price, image, isActive, currentDate, currentDate, courseContent, instructorName);

        CourseService cs = new CourseService();
        cs.create(newCourse);

        // Print a message to the console indicating that the course has been added
        System.out.println("Course added: " + newCourse.getTitle());

        // You can also clear the input fields or perform any other necessary actions here
    }
*/
    @FXML
    private void addCourse(ActionEvent event) {
        Course course = new Course();
    String title = ctitle.getText();
    String description = cdiscription.getText();
    String priceText = cprice.getText();
    String instructorName = cname.getText();
    String createdAtText = ccreated.getEditor().getText(); // Get the text from DatePicker

    titleLabel.setTextFill(Color.BLACK); // Clear error message and set the text color to black
    descriptionLabel.setTextFill(Color.BLACK); // Clear error message and set the text color to black
    priceLabel.setTextFill(Color.BLACK); // Clear error message and set the text color to black
    instructorLabel.setTextFill(Color.BLACK); // Clear error message and set the text color to black
    createdatLabel.setTextFill(Color.BLACK); // Clear error message and set the text color to black

    boolean hasErrors = false;

    // Check if required fields are empty and set the text color to red for error messages
    if (title.isEmpty()) {
        titleLabel.setTextFill(Color.RED);
        titleLabel.setText("Title is required.");
        hasErrors = true;
    }
    if (description.isEmpty()) {
        descriptionLabel.setTextFill(Color.RED);
        descriptionLabel.setText("Description is required.");
        hasErrors = true;
    }
    if (priceText.isEmpty()) {
        priceLabel.setTextFill(Color.RED);
        priceLabel.setText("Price is required.");
        hasErrors = true;
    }
    if (instructorName.isEmpty()) {
        instructorLabel.setTextFill(Color.RED);
        instructorLabel.setText("Instructor name is required.");
        hasErrors = true;
    }
    if (createdAtText.isEmpty()) {
        createdatLabel.setTextFill(Color.RED);
        createdatLabel.setText("Created at is required.");
        hasErrors = true;
    }
    if (imageName == null) {
            photoTest = 0;
            System.out.println("l'image est nulle");
        } else {
            photoTest = 1;
            course.setImage(imageName);
        }

    if (hasErrors) {
        return;
    }

    float price;
    try {
        price = Float.parseFloat(priceText);
    } catch (NumberFormatException e) {
        priceLabel.setTextFill(Color.RED);
        priceLabel.setText("Invalid price. Please enter a valid number.");
        return;
    }

    // Optionally, you can check if the image URL is empty or valid

    // Create a new Course instance with current 'createdAt' and 'updatedAt'
    Date currentDate = new Date(System.currentTimeMillis());
    Course newCourse = new Course(title,description,price,cimage.getText(),cactive.isSelected(),currentDate, currentDate, Arrays.asList(countent.getText().split(",")),  instructorName);

    CourseService cs = new CourseService();

    boolean isCourseAdded = addCourseToDatabase(newCourse, cs);

    // Display a notification based on the result
    if (isCourseAdded) {
        // Show a success notification
        Notifications.create()
            .title("Success")
            .text("Course added successfully.")
            .darkStyle()
            .position(Pos.TOP_RIGHT)
            .hideAfter(javafx.util.Duration.seconds(5))
            .showInformation();
    } else {
        // Show an error notification
        Notifications.create()
            .title("Error")
            .text("Course addition failed. Please check your input.")
            .darkStyle()
            .position(Pos.TOP_RIGHT)
            .hideAfter(javafx.util.Duration.seconds(5))
            .showError();
    }
}

private boolean addCourseToDatabase(Course newCourse, CourseService cs) {
    try {
        cs.create(newCourse);

        // Print a message to the console indicating that the course has been added
        System.out.println("Course added: " + newCourse.getTitle());

        // Now, send an SMS notification
        String recipientPhoneNumber = "21623671593"; // Replace with the recipient's phone number
        String messageText = "New course added: " + newCourse.getTitle() + " by " + newCourse.getInstructorName();

        // Use TwilioSMS to send the SMS notification
        TwilioSMS.sendCustomMessage(recipientPhoneNumber, messageText);

        // You can also clear the input fields or perform any other necessary actions here

        return true; // Course added successfully
    } catch (Exception e) {
        e.printStackTrace();
        return false; // Error occurred while adding the course
    }
}

//@FXML
//private void uploadImage(ActionEvent event) throws IOException {

//}
  @FXML
    void uploadImage(MouseEvent event) throws IOException {
           /* FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
    File selectedFile = fileChooser.showOpenDialog(null);

    imageLabel.setText(""); // Clear any previous error message
    imageLabel.setTextFill(Color.BLACK); // Reset text color to black

    if (selectedFile != null) {
        String fileName = selectedFile.getName(); // Get the name of the file
        cimage.setText(fileName); // Set the image file name in the cimage TextField

        // Optionally, you can store the selected file in a variable for later use
        // File selectedImageFile = selectedFile;
    } else {
        imageLabel.setText("Please select a valid image file.");
        imageLabel.setTextFill(Color.RED); // Set text color to red
    }*/
    FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veuillez choisir votre fichier");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            //imageInput.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
           
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
        String imageName = uniqueID + extension;
         System.out.println( "le code de votre image est" + imageName);
         cimage.setText(imageName);

            Path destination = Paths.get(System.getProperty("user.dir"), "src", "CoursifyApp", "image", imageName);
            Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            
        }
    }
    @FXML
    private void ghome(ActionEvent event) {
          try {
        // Load the "home" FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseMainPage.fxml"));
        Parent root = loader.load();

        // Create a new stage for the "Home" page
        Stage homeStage = new Stage();
        homeStage.setScene(new Scene(root));

        // Set the title of the "Home" window
        homeStage.setTitle("Home");

        // Close the current stage (CourseModification)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Show the "Home" window
        homeStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
        
        
        
        
    }

  






}



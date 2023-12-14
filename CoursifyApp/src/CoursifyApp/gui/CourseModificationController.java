/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Course;
import CoursifyApp.services.CourseService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hassa
 */
public class CourseModificationController implements Initializable {

  @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField imageField;
    @FXML
    private CheckBox isActiveCheckbox;
    @FXML
    private TextArea courseContentTextArea;
    @FXML
    private Button saveButton;

    private Course course;  // The course you want to modify
    private CourseService courseService;
    @FXML
    private Button uploadImageButton;
    @FXML
    private ImageView courseImageView;
    @FXML
    private Button btngotohome;
    @FXML
    private Pane pnlstatus;
    @FXML
    private Label lblstatusMini;
    @FXML
    private Label lblStatus;

public void setCourse(Course course) {
        this.course = course;
        loadCourseDetails(); // Load course details into input fields
    }

    public void initialize(URL url, ResourceBundle rb) {
        courseService = new CourseService();
    }

    private void loadCourseDetails() {
        if (course != null) {
            titleField.setText(course.getTitle());
            descriptionField.setText(course.getDescription());
            priceField.setText(String.valueOf(course.getPrice()));
            imageField.setText(course.getImage());
            isActiveCheckbox.setSelected(course.isActive());
            courseContentTextArea.setText(String.join("\n", course.getCourseContent()));
        }
    }


   @FXML
private void saveCourse() {
    if (course != null) {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String priceText = priceField.getText();
        String image = imageField.getText();
        String courseContent = courseContentTextArea.getText();
        
        if (title.isEmpty() || description.isEmpty() || priceText.isEmpty() || image.isEmpty() || courseContent.isEmpty()) {
            showNotification("Please fill in all fields.");
        } else if (!isFloat(priceText)) {
            showNotification("Price must be a valid number.");
        } else {
            float price = Float.parseFloat(priceText);

            // Update the course with the new attributes
            course.setTitle(title);
            course.setDescription(description);
            course.setPrice(price);
            course.setImage(image);
            course.setActive(isActiveCheckbox.isSelected());

            // Set course content by splitting the text area's content by newline
            List<String> contentList = Arrays.asList(courseContent.split("\n"));
            course.setCourseContent(contentList);

            // Update the updatedAt with the current time
            course.setUpdatedAt(new Date());

            // Call the service to update the course
            courseService.update(course);

            // Close the modification window
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();

            showNotification("Course updated successfully.");
        }
    }
}

private boolean isFloat(String input) {
    try {
        Float.parseFloat(input);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

private void showNotification(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Notification");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}


      @FXML
    private void uploadImage() {
        // Create a FileChooser to select an image file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Handle the selected image file and load it
            loadCourseImage(selectedFile.toURI().toString());
        }
    }

    private void loadCourseImage(String imageUrl) {
        // Load the image using the provided URL
        Image image = new Image(imageUrl);
        courseImageView.setImage(image);
    }

@FXML
private void goToHome(ActionEvent event) {
    try {
        // Load the "home" FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
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

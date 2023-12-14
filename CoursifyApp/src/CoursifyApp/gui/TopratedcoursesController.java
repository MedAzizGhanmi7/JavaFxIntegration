package CoursifyApp.gui;

import CoursifyApp.entities.Course;
import CoursifyApp.services.CourseService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hassa
 */
public class TopratedcoursesController implements Initializable {

   @FXML
    private Pane pnlstatus;
    @FXML
    private Label lblstatusMini;
    @FXML
    private Label lblStatus;
    @FXML
    private TableView<Course> topRatedCoursesTable; // Specify the generic type as Course
    @FXML
    private TableColumn<Course, String> title; // Specify the generic type as Course
    @FXML
    private TableColumn<Course, String> instructorName; // Specify the generic type as Course
    @FXML
    private TableColumn<Course, Float> averageRating; // Specify the generic type as Course

    private CourseService courseService = new CourseService(); // Replace with your actual service
    @FXML
    private Button btngotohomepage;
     private Stage stage; 
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Course> topRatedCourses = courseService.getTopRatedCourses(3); // Adjust the limit as needed
        populateTopRatedCourses(topRatedCourses);
    }

   public void populateTopRatedCourses(List<Course> topRatedCourses) {
    title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    instructorName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstructorName()));
    averageRating.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAverageRating()));

    topRatedCoursesTable.getItems().setAll(topRatedCourses);
}

 @FXML
private void returntohome(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Create a new scene for the home page
        Stage mainPageStage = new Stage();
        mainPageStage.setScene(new Scene(root));

        // Set the title of the home page window
        mainPageStage.setTitle("Home Page");

        // Close the current scene (ReviewHomePage)
        Stage currentStage = (Stage) btngotohomepage.getScene().getWindow();
        currentStage.close();

        // Show the home page window
        mainPageStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}


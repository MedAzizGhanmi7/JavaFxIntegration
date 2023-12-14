package CoursifyApp.gui;

import CoursifyApp.entities.Course;
import CoursifyApp.entities.Review;
import CoursifyApp.services.ReviewService;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;
import javafx.util.Duration;

public class ReviewPageController implements Initializable {

    @FXML
    private TextField rcoursetitle;
    @FXML
    private TextField rrname;
    @FXML
    private Rating rrating;

    @FXML
    private TextField rcmnttitle;
    @FXML
    private TextField rcmntcont;
    @FXML
    private DatePicker rcreated;
    @FXML
    private Button rbtnadd;
    @FXML
    private Label coursetitleLabel;
    @FXML
    private Label reviewerLabel;
    @FXML
    private Label cmnttitleLabel;
    @FXML
    private Label cmntcontentLabel;
      private int courseId;
    private Course selectedCourse;
    @FXML
    private Label rcreatedLabel;
    @FXML
    private Button btngthaddr;
    @FXML
    private Pane pnlstatus;
    @FXML
    private Label lblstatusMini;
    @FXML
    private Label lblStatus;

    // Set the courseId from CourseMainPageController
   
     public void setCourse(Course course) {
        this.selectedCourse = course;
        if (selectedCourse != null) {
            rcoursetitle.setText(selectedCourse.getTitle());
        }
    }
       
     public void setCourseInfo(int courseId, String courseTitle) {
        // Set the course ID and title in your controller
        this.courseId = courseId;
        rcoursetitle.setText(courseTitle);
    }
     
     
     
     
      public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    // Review service
    private ReviewService reviewService = new ReviewService();

    @Override
public void initialize(URL url, ResourceBundle rb) {
    rrating.setRating(0.0);

//    if (selectedCourse != null) {
//        .setText("Course Title: " + selectedCourse.getTitle());
//    }
   // rcoursetitle.setEditable(false);

}

    @FXML
    private void addReview(ActionEvent event) {
        String courseTitle = rcoursetitle.getText();
        String reviewerName = rrname.getText();
        double rating = rrating.getRating();
        String commentTitle = rcmnttitle.getText();
        String commentContent = rcmntcont.getText();
        LocalDate createdAt = rcreated.getValue();

        // Input validation

        // Check if required fields are not empty
        if (courseTitle.isEmpty() || reviewerName.isEmpty() || commentTitle.isEmpty() || commentContent.isEmpty() || createdAt == null) {
            // Display an error notification
            Notifications.create()
                    .title("Input Error")
                    .text("Please fill in all the required fields.")
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .hideAfter(Duration.seconds(5))
                    .showError();
            return; // Exit the method without adding the review if a required field is missing
        }

        // Check if the creation date is not earlier than the current date
        LocalDate currentDate = LocalDate.now();
        if (createdAt.isBefore(currentDate)) {
            // Display an error notification
            Notifications.create()
                    .title("Input Error")
                    .text("The creation date cannot be earlier than the current date.")
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .hideAfter(Duration.seconds(5))
                    .showError();
            return; // Exit the method without adding the review if the date is incorrect
        }

        // Check if the reviewer name doesn't contain numbers
        if (containsNumbers(reviewerName)) {
            // Display an error notification
            Notifications.create()
                    .title("Input Error")
                    .text("Reviewer name cannot contain numbers.")
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .hideAfter(Duration.seconds(5))
                    .showError();
            return; // Exit the method without adding the review if the reviewer name contains numbers
        }

        // Check if comment title and comment content contain prohibited words
        String prohibitedWords = "fuck|hoe|shit|merde|bitch"; // Define prohibited words
        if (containsProhibitedWords(commentTitle, prohibitedWords) || containsProhibitedWords(commentContent, prohibitedWords)) {
            // Create and show multiple notifications at different positions
            for (int i = 0; i < 10; i++) {
                showProhibitedWordsNotification(i);
            }
            // Display a center notification that stays for 7 seconds
            showCenterNotification("Prohibited Words", "The comment contains prohibited words. Please remove them. ⚠", Duration.seconds(7));
            return; // Exit the method without adding the review if prohibited words are found
        }

        // All validations are successful, create the Review object
        Review newReview = new Review(0, courseId, courseTitle, reviewerName, (int) rating, commentTitle, commentContent, Date.from(createdAt.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        try {
            // Add the review to the database
            reviewService.create(newReview);

            // Display a success notification
            Notifications.create()
                    .title("Success")
                    .text("Review added successfully.")
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .hideAfter(Duration.seconds(5))
                    .showInformation();
        } catch (Exception e) {
            // Handle exceptions and display an error notification
            e.printStackTrace();

            Notifications.create()
                    .title("Error")
                    .text("Failed to add the review. Please check your input.")
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .hideAfter(Duration.seconds(5))
                    .showError();
        }
    }

    private boolean containsNumbers(String input) {
        return input.matches(".*\\d.*");
    }

    private boolean containsProhibitedWords(String input, String prohibitedWords) {
        return input.matches(".*\\b(" + prohibitedWords + ")\\b.*");
    }

    private void showProhibitedWordsNotification(int index) {
        String notificationText = "The comment contains prohibited words. Please remove them. ⚠";

        Notifications notification = Notifications.create()
                .title("Prohibited Words")
                .text(notificationText)
                .darkStyle()
                .position(getPositionForIndex(index))
                .hideAfter(Duration.seconds(5));

        // Set a red background for the notification
        notification.showError();
    }

    private void showCenterNotification(String title, String text, Duration duration) {
        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .darkStyle()
                .position(Pos.CENTER)
                .hideAfter(duration);

        notification.showError();
    }

    private Pos getPositionForIndex(int index) {
        // Define different positions based on the index
        switch (index % 5) {
            case 0:
                return Pos.TOP_LEFT;
            case 1:
                return Pos.TOP_CENTER;
            case 2:
                return Pos.TOP_RIGHT;
            case 3:
                return Pos.CENTER_LEFT;
            case 4:
                return Pos.CENTER_RIGHT;
            default:
                return Pos.TOP_LEFT;
        }
    }

    @FXML
    private void gohomefromaddreview(ActionEvent event) {
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

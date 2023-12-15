package CoursifyApp.gui;

import CoursifyApp.entities.Course;
import CoursifyApp.entities.Instructor;
import CoursifyApp.gui.Ch.ChapterEditorController;
import CoursifyApp.services.CourseService;
import CoursifyApp.services.InstructorService;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CourseMainPageController implements Initializable {
    @FXML
    private ListView<Course> courseListView; // Replace with ListView

    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    private Button btndelete;
    @FXML
    private Button btnadd;
    @FXML
    private Button btnsavemodif;

    private Stage stage; // The stage for this controller
    @FXML
    private TextField searchtextentry;
    @FXML
    private Button btnsearch;
    @FXML
    private Button btnclear;
    @FXML
    private Button btncourse;
    @FXML
    private Button btnprofile;
    @FXML
    private Button btnstudent;
    @FXML
    private Button btnreviews;
    @FXML
    private Pane pnlstatus;
    @FXML
    private Label lblstatusMini;
    @FXML
    private Label lblStatus;
   
    private Instructor instructor ;
    @FXML
    private Button btngototoprated;
    @FXML
    private Button btngodash;
    @FXML
    private Button btnlogout;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    
    
      private int userId;
    private String userRole;
     public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
        InstructorService is = new InstructorService();
       instructor= is.getById(userId);
    }
    
    @Override
public void initialize(URL url, ResourceBundle rb) {
    // Load courses into the ListView
    loadCourses();

    // Set a custom cell factory to display courses without IDs
    courseListView.setCellFactory(listView -> new ListCell<Course>() {
        @Override
        protected void updateItem(Course item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText("Title: " + item.getTitle() + "\nDescription: " + item.getDescription());
            }
        }
    });

    // Handle double-click to show course details
    courseListView.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2) { // Check for double-click
            Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();
            if (selectedCourse != null) {
                // Show a dialog with additional details
                showCourseDetailsDialog(selectedCourse);
            }
        }
    });
    ContextMenu contextMenu = new ContextMenu();

    MenuItem deleteMenuItem = new MenuItem("Delete");
    deleteMenuItem.setOnAction(e -> deleteCourse());

    MenuItem modifyMenuItem = new MenuItem("Modify");
    modifyMenuItem.setOnAction(e -> modifyCourse());
    
     MenuItem addReviewMenuItem = new MenuItem("Add Review");
    addReviewMenuItem.setOnAction(e -> openAddReviewPage());

    // Add the "Add Review" menu item to the context menu
    contextMenu.getItems().addAll(deleteMenuItem, modifyMenuItem, addReviewMenuItem);

    // Set the ContextMenu for the ListView
    courseListView.setContextMenu(contextMenu);
}
private void showCourseDetailsDialog(Course course) {
    // Create and configure the dialog (you can use JavaFX Dialog or another dialog library)
    // For example, you can use JavaFX's Alert dialog.
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Course Details");
    alert.setHeaderText(null);
    alert.setContentText("Title: " + course.getTitle() + "\nDescription: " + course.getDescription() +
            "\nPrice: " + course.getPrice() + "\nActive: " + (course.isActive() ? "Yes" : "No") +
            "\nCreated At: " + course.getCreatedAt() + "\nUpdated At: " + course.getUpdatedAt() +
            "\nCourse Content: " + String.join(", ", course.getCourseContent()) +
            "\nInstructor Name: " + course.getInstructorName());

    // Show the dialog
    alert.showAndWait();
}

//    @FXML
//    private void handelcourse(ActionEvent event) {
//    }
//
//    @FXML
//    private void handelprofile(ActionEvent event) {
//    }
//
//    @FXML
//    private void handelstudent(ActionEvent event) {
//    }
//
//    @FXML
//    private void handelreviews(ActionEvent event) {
//    }

    @FXML
    private void gototopratedcourses(ActionEvent event) {
        try {
            // Load the "add page" FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Topratedcourses.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage addStage = new Stage();
            addStage.setScene(new Scene(root));

            // Set the title of the "add page" window
            addStage.setTitle("Top rated courses");

            // Close the current stage (CourseMainPage)
           // stage.close();

            // Show the "add page" window
            addStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    @FXML
    private void godash(ActionEvent event) {
         try {
            // Load the "add page" FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage addStage = new Stage();
            addStage.setScene(new Scene(root));

            // Set the title of the "add page" window
            addStage.setTitle("Dashboard");

            // Close the current stage (CourseMainPage)
            stage.close();

            // Show the "add page" window
            addStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    @FXML
    private void logout(ActionEvent event) {
         try {
            // Load the "add page" FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage addStage = new Stage();
            addStage.setScene(new Scene(root));

            // Set the title of the "add page" window
            addStage.setTitle("login");

            // Close the current stage (CourseMainPage)
            stage.close();

            // Show the "add page" window
            addStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class ListStringConverter extends StringConverter<List<String>> {
        @Override
        public String toString(List<String> list) {
            if (list == null) {
                return "";
            }
            return String.join(", ", list);
        }

        @Override
        public List<String> fromString(String string) {
            if (string == null) {
                return null;
            }
            return Arrays.asList(string.split(", "));
        }
    }

   private void loadCourses() {
        // Fetch courses from a data source (CourseService)
        CourseService courseService = new CourseService();
        courseList.setAll(courseService.getAll());

        // Set the items for the ListView
        courseListView.setItems(courseList);
    }
   
   @FXML
   private void editcourse(ActionEvent event)
   {
       Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();
       if(selectedCourse == null)
           return;
       try {
           // Load the "add page" FXML file
           FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("CoursifyApp/gui/Ch/ChapterEditor.fxml"));
           Parent root = loader.load();
           ChapterEditorController controller = (ChapterEditorController)loader.getController();
           controller.initialize(selectedCourse);

           // Create a new stage
           Stage addStage = new Stage();
           addStage.setScene(new Scene(root));

           // Set the title of the "add page" window
           addStage.setTitle("Top rated courses");

           // Close the current stage (CourseMainPage)
           // stage.close();

           // Show the "add page" window
           addStage.show();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
    @FXML
    private void deletecourse(ActionEvent event) {
        Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            // Fetch courses from a data source (CourseService)
            CourseService courseService = new CourseService();

            // Remove the selected course from the ListView
            courseList.remove(selectedCourse);

            // Delete the course from the database using its ID
            courseService.delete((int) selectedCourse.getCourseId());
        }
    }

    @FXML
    private void gotoadd(ActionEvent event) {
        try {
            // Load the "add page" FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("course.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage addStage = new Stage();
            addStage.setScene(new Scene(root));

            // Set the title of the "add page" window
            addStage.setTitle("Add Course");

            // Close the current stage (CourseMainPage)
            //stage.close();

            // Show the "add page" window
            addStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveModification(ActionEvent event) {
        // Loop through the items and update changes
        CourseService courseService = new CourseService();
        for (Course modifiedCourse : courseList) {
            courseService.update(modifiedCourse);
        }
    }

   @FXML
private void searchCoursesByName(ActionEvent event) {
    String searchName = searchtextentry.getText().toLowerCase();
    CourseService courseService = new CourseService();
    
    List<Course> searchResults = courseService.getAll().stream()
            .filter(course -> course.getTitle().toLowerCase().contains(searchName))
            .collect(Collectors.toList());

    courseList.setAll(searchResults);
}


    @FXML
    private void clearSearch(ActionEvent event) {
        loadCourses(); // Load all courses
    }
    
    
    private void deleteCourse() {
    Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();
    if (selectedCourse != null) {
        // Fetch courses from a data source (CourseService)
        CourseService courseService = new CourseService();

        // Remove the selected course from the ListView
        courseList.remove(selectedCourse);

        // Delete the course from the database using its ID
        courseService.delete((int) selectedCourse.getCourseId()); // Use the appropriate method and parameter
    }
}

private void modifyCourse() {
    Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();
    if (selectedCourse != null) {
        try {
            // Load the "courseModification.fxml" FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("courseModification.fxml"));
            Parent root = loader.load();

            // Pass the selected course to the CourseModificationController
            CourseModificationController modificationController = loader.getController();
            modificationController.setCourse(selectedCourse);

            // Create a new stage for modification
            Stage modificationStage = new Stage();
            modificationStage.setScene(new Scene(root));

            // Set the title of the "courseModification" window
            modificationStage.setTitle("Modify Course");

            // Show the modification window
            modificationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//private void openAddReviewPage() {
//    Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();
//    if (selectedCourse != null) {
//        try {
//            // Load the "reviewPage.fxml" FXML file for adding reviews
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("reviewPage.fxml"));
//            Parent root = loader.load();
//
//            // Get the controller for the review page
//            ReviewPageController reviewController = loader.getController();
//
//            // Set the course in the ReviewPageController
//            reviewController.setCourse(selectedCourse);
//
//            // Create a new stage for the "Add Review" page
//            Stage reviewStage = new Stage();
//            reviewStage.setScene(new Scene(root));
//
//            // Set the title of the "Add Review" page
//            reviewStage.setTitle("Add Review");
//
//            // Show the "Add Review" page
//            reviewStage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
///////////////////////////////
private void openAddReviewPage() {
    Course selectedCourse = courseListView.getSelectionModel().getSelectedItem();
    if (selectedCourse != null) {
        try {
            // Load the "reviewPage.fxml" FXML file for adding reviews
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reviewPage.fxml"));
            Parent root = loader.load();

            // Get the controller for the review page
            ReviewPageController reviewController = loader.getController();

            // Set the course ID and title in the ReviewPageController
            reviewController.setCourseInfo((int) selectedCourse.getCourseId(), selectedCourse.getTitle());

            // Create a new stage for the "Add Review" page
            Stage reviewStage = new Stage();
            reviewStage.setScene(new Scene(root));

            // Set the title of the "Add Review" page
            reviewStage.setTitle("Add Review");

            // Show the "Add Review" page
            reviewStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
@FXML
private void handleClicks(ActionEvent event) {
    BackgroundFill backgroundFill = null;
    if (event.getSource() == btncourse) {
        lblStatus.setText("Course Status");
        backgroundFill = new BackgroundFill(Color.rgb(128, 0, 128), CornerRadii.EMPTY, Insets.EMPTY);
        lblstatusMini.setText("Home/Courses");
    } else if (event.getSource() == btnprofile) {
        lblStatus.setText("Profile Status");
        backgroundFill = new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY);
        lblstatusMini.setText("Home/Profile");
    } else if (event.getSource() == btnstudent) {
        lblStatus.setText("Student Status");
        backgroundFill = new BackgroundFill(Color.rgb(0, 0, 255), CornerRadii.EMPTY, Insets.EMPTY);
        lblstatusMini.setText("Home/Students");
    } else if (event.getSource() == btnreviews) {
        lblStatus.setText("Reviews Status");
        backgroundFill = new BackgroundFill(Color.rgb(255, 255, 0), CornerRadii.EMPTY, Insets.EMPTY);
        lblstatusMini.setText("Home/Reviews");
        
        // Load the "ReviewHomePage.fxml" file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewHomePage.fxml"));
            Parent root = loader.load();

            // Create a new stage for the "ReviewHomePage"
            Stage reviewStage = new Stage();
            reviewStage.setScene(new Scene(root));

            // Set the title of the "ReviewHomePage"
            reviewStage.setTitle("Review Home Page");

            // Close the current stage (CourseMainPage)
            Stage currentStage = (Stage) pnlstatus.getScene().getWindow();
            currentStage.close();

            // Show the "ReviewHomePage"
            reviewStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update the background of pnlstatus
    pnlstatus.setBackground(new Background(backgroundFill));
}




}
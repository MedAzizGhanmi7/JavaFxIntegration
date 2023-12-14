package CoursifyApp.gui;

import CoursifyApp.entities.Review;
import CoursifyApp.services.ReviewService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class ReviewHomePageController implements Initializable {

    @FXML
    private TableView<Review> tvreview;
    @FXML
    private TableColumn<Review, String> courstitleTv;
    @FXML
    private TableColumn<Review, String> reviewerNameTV;
    @FXML
    private TableColumn<Review, Integer> ratingTV;
    @FXML
    private TableColumn<Review, String> commentTitleTV;
    @FXML
    private TableColumn<Review, String> commentContentTv;
    @FXML
    private TableColumn<Review, String> createdAtTv;
    @FXML
    private Button btnaddreview;

     private final ObservableList<Review> reviewList = FXCollections.observableArrayList();
    private final ReviewService reviewService = new ReviewService();
    @FXML
    private Button btndelete;
    @FXML
    private Button btnsave;
    @FXML
    private TextField searchtf;
    @FXML
    private Button btnsearch;
    @FXML
    private Button buttonghomer;
    @FXML
    private Pane pnlstatus;
    @FXML
    private Label lblstatusMini;
    @FXML
    private Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColumns();
        loadReviews();

        // Enable editing for the columns you want to edit
        commentTitleTV.setCellFactory(TextFieldTableCell.forTableColumn());
        reviewerNameTV.setCellFactory(TextFieldTableCell.forTableColumn());
        ratingTV.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        commentContentTv.setCellFactory(TextFieldTableCell.forTableColumn());

        // Leave the "createdAt" column as it is (not editable)
        createdAtTv.setCellFactory(column -> new TableCell<Review, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getItem());
            }
        });

        createdAtTv.setOnEditCommit(event -> {
            // Do nothing since we don't want to edit createdAt
        });
    }

     private void initializeColumns() {
        courstitleTv.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        reviewerNameTV.setCellValueFactory(new PropertyValueFactory<>("reviewerName"));
        ratingTV.setCellValueFactory(new PropertyValueFactory<>("rating"));
        commentTitleTV.setCellValueFactory(new PropertyValueFactory<>("commentTitle"));
        commentContentTv.setCellValueFactory(new PropertyValueFactory<>("commentContent"));
        createdAtTv.setCellFactory(column -> {
    return new TableCell<Review, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                // Format the Timestamp as a String here
                setText(item.toString());  // Or use a SimpleDateFormat if you want a specific format
            }
        }
    };
});

    }

    private void loadReviews() {
        List<Review> reviews = reviewService.getAll();
        reviewList.setAll(reviews);
        tvreview.setItems(reviewList);
    }

    @FXML
    private void gotoreview(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewPage.fxml"));
            Parent root = loader.load();

            // Create a new stage for the "Add Review" page
            Stage reviewAddStage = new Stage();
            reviewAddStage.setScene(new Scene(root));

            // Set the title of the "Add Review" window
            reviewAddStage.setTitle("Add Review");

            // Close the current stage (ReviewHomePage)
            Stage currentStage = (Stage) btnaddreview.getScene().getWindow();
            currentStage.close();

            // Show the "Add Review" window
            reviewAddStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deletreview(ActionEvent event) {
        Review selectedReview = tvreview.getSelectionModel().getSelectedItem();

        if (selectedReview != null) {
            // Show a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Review");
            alert.setHeaderText("Are you sure you want to delete this review?");
            alert.setContentText("Review ID: " + selectedReview.getReviewId());

            // Get the result of the confirmation dialog
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Delete the selected review from the database
                reviewService.delete((int) selectedReview.getReviewId());

                // Remove the review from the TableView
                reviewList.remove(selectedReview);
            }
        }
    }

@FXML
private void savechanges(ActionEvent event) {
    // Commit the current cell edit
    tvreview.edit(-1, null);

    // Loop through the items and update changes
    ReviewService reviewService = new ReviewService();
    for (Review modifiedReview : reviewList) {
        // Log the details of the review being updated
       // System.out.println("Review ID: " + modifiedReview.getReviewId());
        System.out.println("Original Comment Title: " + modifiedReview.getCommentTitle());
        
        // Perform the update
        reviewService.update(modifiedReview);
        
        // Log that the update was attempted
        System.out.println("Review update attempted in the database");
    }

    // Show a confirmation message or perform any other actions as needed
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Review Modifications Saved");
    alert.setHeaderText(null);
    alert.setContentText("Review modifications have been saved to the database.");
    alert.showAndWait();
}

@FXML
private void searchreviews(ActionEvent event) {
    String searchKeyword = searchtf.getText(); // Get the search keyword from the TextField
    List<Review> matchingReviews = searchReviews(searchKeyword); // Call the search function
    reviewList.setAll(matchingReviews); // Update the TableView with matching results
    tvreview.refresh(); // Refresh the TableView to reflect the changes
}


private List<Review> searchReviews(String keyword) {
    List<Review> matchingReviews = new ArrayList<>();

    for (Review review : reviewService.getAll()) {
        if (containsKeyword(review, keyword)) {
            matchingReviews.add(review);
        }
    }

    return matchingReviews;
}

private boolean containsKeyword(Review review, String keyword) {
    return review.getCourseTitle().toLowerCase().contains(keyword.toLowerCase()) ||
           review.getReviewerName().toLowerCase().contains(keyword.toLowerCase()) ||
           String.valueOf(review.getRating()).contains(keyword) ||
           review.getCommentTitle().toLowerCase().contains(keyword.toLowerCase()) ||
           review.getCommentContent().toLowerCase().contains(keyword.toLowerCase());
}

    @FXML
private void ghomer(ActionEvent event) {
   try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CousMainPage.fxml"));
        Parent root = loader.load();

        // Créez une nouvelle scène pour la page d'accueil
        Stage mainPageStage = new Stage();
        mainPageStage.setScene(new Scene(root));

        // Définissez le titre de la fenêtre de la page d'accueil
        mainPageStage.setTitle("Page d'accueil");

        // Fermez la scène actuelle (ReviewHomePage)
        Stage currentStage = (Stage) btnaddreview.getScene().getWindow();
        currentStage.close();

        // Affichez la fenêtre de la page d'accueil
        mainPageStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}



   


}
package CoursifyApp.gui;


import CoursifyApp.services.CommentService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CommentTableViewController implements Initializable {

    @FXML
    private TableView<CoursifyApp.entities.Comment> commentTable;
    @FXML
    private TableColumn<CoursifyApp.entities.Comment, Integer> idComment;
    @FXML
    private TableColumn<CoursifyApp.entities.Comment, String> Content;
    @FXML
    private TableColumn<CoursifyApp.entities.Comment, String> DateComment;
    @FXML
    private TableColumn<CoursifyApp.entities.Comment, String> EmailCommenter;
    private CommentService commentService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commentService = new CommentService();
        idComment.setCellValueFactory(new PropertyValueFactory<>("id"));
        Content.setCellValueFactory(new PropertyValueFactory<>("ContenuCommentaire"));
        DateComment.setCellValueFactory(new PropertyValueFactory<>("date"));
        EmailCommenter.setCellValueFactory(new PropertyValueFactory<>("EmailCommentateur"));

        populateTable();
    } 

    private void populateTable() {
        commentTable.getItems().clear();
        List<CoursifyApp.entities.Comment> comments = commentService.getAll();
        commentTable.getItems().addAll(comments);
    }

@FXML
private void update(ActionEvent event) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/deltaVelorum/coursify/messagerie/gui/UpdateComment.fxml"));
        Parent root = fxmlLoader.load();
        UpdateCommentController updateCommentController = fxmlLoader.getController();
        CoursifyApp.entities.Comment selectedComment = commentTable.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            updateCommentController.setSelectedComment(selectedComment);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun commentaire sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un commentaire pour la mise à jour.");
            alert.showAndWait();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


@FXML
private void delete(ActionEvent event) {
    CoursifyApp.entities.Comment selectedComment = commentTable.getSelectionModel().getSelectedItem();
    if (selectedComment == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucun commentaire sélectionné");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un commentaire à supprimer.");
        alert.showAndWait();
    } else {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce commentaire ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            int commentId = selectedComment.getId();
            commentService.delete(commentId);
            populateTable();
        }
    }
}


    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/deltaVelorum/coursify/messagerie/gui/Comment.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

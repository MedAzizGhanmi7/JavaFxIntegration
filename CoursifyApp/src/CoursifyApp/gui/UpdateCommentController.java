package CoursifyApp.gui;


import CoursifyApp.entities.*;
import CoursifyApp.services.*;
import com.deltaVelorum.coursify.messagerie.gui.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class UpdateCommentController implements Initializable {

    @FXML
    private TextArea ContenuCommentaire;
    private CoursifyApp.entities.Comment selectedComment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    



    public void setSelectedComment(CoursifyApp.entities.Comment comment) {
        this.selectedComment = comment;
        ContenuCommentaire.setText(comment.getContenuCommentaire());
    }

    @FXML
    private void update(ActionEvent event) {
        if (selectedComment != null) {
            String contenu = ContenuCommentaire.getText();

            if (contenu.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir le champ de contenu.");
                alert.showAndWait();
            } else {
                selectedComment.setContenuCommentaire(contenu);
                CommentService commentService = new CommentService();
                commentService.update(selectedComment);
                showSuccessMessageDialog(selectedComment);
                closeWindow();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun commentaire sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un commentaire pour la mise à jour.");
            alert.showAndWait();
        }
    }

    private void showSuccessMessageDialog(CoursifyApp.entities.Comment comment) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Commentaire mis à jour avec succès.");
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) ContenuCommentaire.getScene().getWindow();
        stage.close();
    }
}

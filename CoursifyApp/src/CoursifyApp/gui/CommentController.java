/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;
import CoursifyApp.services.CommentService;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
/**
 * FXML Controller class
 *
 * @author PC
 */
public class CommentController implements Initializable {

    @FXML
    private TextField EmailCommentateur;
    @FXML
    private TextArea ContenuCommentaire;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void getAll(ActionEvent event) {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/deltaVelorum/coursify/messagerie/gui/CommentTableView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private void showSuccessMessageDialog(CoursifyApp.entities.Comment comment) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Commentaire ajouté avec succès à la base de données.");
        alert.setContentText("L'id de votre message est : " + comment.getId());
        alert.showAndWait();
    }
            private boolean validateEmail(String email) {
        return email.matches("^(.+)@(.+)$");
    }
            
        private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void getByid(ActionEvent event) {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/deltaVelorum/coursify/messagerie/gui/getById.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void Exit(ActionEvent event) {
    }

    @FXML
    private void Comment(ActionEvent event) {
        String emailCommentateur = EmailCommentateur.getText();
        String content = ContenuCommentaire.getText();
        
        CommentService commentService = new CommentService();
        if (validateEmail(emailCommentateur)) {
            CoursifyApp.entities.Comment newComment = new CoursifyApp.entities.Comment();
            newComment.setEmailCommentateur(emailCommentateur);
            newComment.setContenuCommentaire(content);
            commentService.add(newComment);

            showSuccessMessageDialog(newComment);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/deltaVelorum/coursify/messagerie/gui/Comment.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showErrorAlert("Veuillez saisir des adresses e-mail valides.");
        }
    }
    
    }

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateViewController implements Initializable {

    @FXML
    private TextField ObjetMessage;
    @FXML
    private TextArea ContenuMessage;
    private Message selectedMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Return(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/deltaVelorum/coursify/messagerie/gui/TableView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSuccessMessageDialog(Message message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Message mis à jour avec succès.");
        alert.showAndWait();
    }

    @FXML
    private void Update(ActionEvent event) {
        if (selectedMessage != null) {
            String subject = ObjetMessage.getText();
            String content = ContenuMessage.getText();

            if (subject.isEmpty() || content.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
            } else {
                selectedMessage.setObjetMessage(subject);
                selectedMessage.setContenuMessage(content);
                MessageService messageService = new MessageService();
                messageService.update(selectedMessage);
                showSuccessMessageDialog(selectedMessage);
                closeWindow();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun message sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un message pour la mise à jour.");
            alert.showAndWait();
        }
    }

    public void setSelectedMessage(Message message) {
        this.selectedMessage = message;
        ObjetMessage.setText(message.getObjetMessage());
        ContenuMessage.setText(message.getContenuMessage());
    }

    private void closeWindow() {
        Stage stage = (Stage) ObjetMessage.getScene().getWindow();
        stage.close();
    }
}

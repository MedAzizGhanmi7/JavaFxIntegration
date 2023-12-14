package CoursifyApp.gui;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import CoursifyApp.entities.Reclamation;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import CoursifyApp.services.ReclamationService;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AffichController implements Initializable {

    @FXML
    private JFXListView<Reclamation> reclamationListView;

    private ReclamationService reclamationService = new ReclamationService();
    @FXML
    private JFXButton deleteButton;

    @Override
        public void initialize(URL location, ResourceBundle resources) {
              ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(reclamationService.getAll());

       
        reclamationListView.setItems(reclamations);
       
     deleteButton.setOnAction(event -> {
    Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
    if (selectedReclamation != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer la reclamation?");
        alert.setContentText("Voulez-vous vraiment supprimer cette reclamatio ?");
Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        // Utilisez l'ID de la réclamation pour la supprimer
        int reclamationId = selectedReclamation.getId(); // Assurez-vous que la classe Reclamation a une méthode getId()
        reclamationService.delete(reclamationId);
        reclamationListView.getItems().remove(selectedReclamation);
    }}});}
    





    

    @FXML
    private void ff(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboardFXML.fxml"));
    Parent instructorDashboardRoot = loader.load();
    
    // Créer une nouvelle scène avec le fichier FXML chargé
    Scene instructorDashboardScene = new Scene(instructorDashboardRoot);

    // Récupérer la scène actuelle à partir du bouton "Retour"
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Changer la scène pour afficher la page InstructorDashboardFXML.fxml
    currentStage.setScene(instructorDashboardScene);
    currentStage.setTitle("Instructor Dashboard"); // Mettre à jour le titre si nécessaire
    currentStage.show();

    }

    @FXML
    private void rep(ActionEvent event) throws IOException {
           Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
    if (selectedReclamation != null) {
        // Chargez la nouvelle interface de réponse aux réclamations (FXML)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("repondrereclamtion.fxml"));
        Parent root = loader.load();

        // Accédez au contrôleur de la nouvelle interface
        RepondrereclamtionController reponseController = loader.getController();

        // Passez la réclamation sélectionnée au contrôleur de réponse
        reponseController.setReclamation(selectedReclamation);

        // Créez une nouvelle fenêtre pour la réponse
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    }

    @FXML
    private void excell(ActionEvent event) {
        Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Reclamations");

    // Define your data or fetch it from your model
    ObservableList<Reclamation> reclamations = reclamationListView.getItems();

    // Create a header row
    Row headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("ID");
    headerRow.createCell(1).setCellValue("numero_mobile");
    headerRow.createCell(2).setCellValue("Description");
    headerRow.createCell(3).setCellValue("objet");
    headerRow.createCell(4).setCellValue("Nom");
    headerRow.createCell(5).setCellValue("prenom");
    headerRow.createCell(6).setCellValue("email");

    // Populate the Excel sheet with your data
    int rowIndex = 1;
    for (Reclamation reclamation : reclamations) {
        Row dataRow = sheet.createRow(rowIndex++);
        dataRow.createCell(0).setCellValue(reclamation.getId());
       dataRow.createCell(1).setCellValue(reclamation.getNumero_mobile());
        dataRow.createCell(2).setCellValue(reclamation.getDescription());
         dataRow.createCell(3).setCellValue(reclamation.getObjet());
          dataRow.createCell(4).setCellValue(reclamation.getNom());
          dataRow.createCell(5).setCellValue(reclamation.getPrenom());
          dataRow.createCell(6).setCellValue(reclamation.getEmail());
    }

    try {
        // Save the Excel file
        FileOutputStream fileOut = new FileOutputStream("reclamations.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        // Show a success message
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Export to Excel");
        alert.setHeaderText(null);
        alert.setContentText("Data has been successfully exported to 'reclamations.xlsx'.");
        alert.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
        // Handle any errors
    }
    }

}

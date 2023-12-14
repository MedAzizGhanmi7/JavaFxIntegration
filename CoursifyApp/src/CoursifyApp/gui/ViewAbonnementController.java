package CoursifyApp.gui;
import CoursifyApp.utilities.Myconnection;
import CoursifyApp.entities.Abonnement;
import CoursifyApp.services.AbonnementService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ViewAbonnementController implements Initializable {

    @FXML
    private Button btndeleteA;
    @FXML
    private TextField rechercherTe;
    @FXML
    private Button btnrechercher;
    @FXML
    private Button btnrecharge;
    @FXML
    private TableColumn<Abonnement, String> nomab;
    @FXML
    private TableColumn<Abonnement, String> coursab;
    @FXML
    private TableColumn<Abonnement, String> typeab;
    @FXML
    private TableColumn<Abonnement, String> datedeb;
    @FXML
    private TableColumn<Abonnement, String> dateexp;
    @FXML
    private TableView<Abonnement> tableview;
    
    
  


@FXML
private void deleteAbon(ActionEvent event) {
    Abonnement selectedAbonnement = tableview.getSelectionModel().getSelectedItem();

    if (selectedAbonnement != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Abonnement");
        alert.setContentText("Are you sure you want to delete this Abonnement?");
        
        // Add buttons to the alert dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        
        // Show the alert and wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == buttonTypeYes) {
            // User clicked "Yes," proceed with deletion

            // Delete the selected item from the database
            AbonnementService abonnementService = new AbonnementService();
            abonnementService.delete(selectedAbonnement.getIdAbonnement());

            // Remove the item from the TableView
            tableview.getItems().remove(selectedAbonnement);
            
            showNotification("Abonnement deleted successfully.");
        }
    } else {
        showNotification("Please select an Abonnement to delete.");
    }
}

    
    
    private void showNotification(String message) {
        Notifications.create()
                .title("Notification")
                .text(message)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5))
                .showInformation();
    }



@FXML
    private void rechercher(ActionEvent event) {
        String userName = rechercherTe.getText();
        if (!userName.isEmpty()) {
            try {
                AbonnementService abonnementService = new AbonnementService();
                Abonnement abonnement = abonnementService.getByName(userName);

                if (abonnement != null) {
                    tableview.getItems().clear();
                    tableview.getItems().add(abonnement);
                } else {
                    tableview.getItems().clear();
                    showNotification("No abonnement found for the specified user.");
                }
            } catch (Exception ex) {
                System.err.println("Error while searching for abonnement: " + ex.getMessage());
                showNotification("An error occurred while searching for the abonnement.");
            }
        } else {
            showNotification("Please enter a user name to search for an abonnement.");
        }
    }



@FXML
private void btnrecharge(ActionEvent event) {
    String userName = rechercherTe.getText();
    if (!userName.isEmpty()) {
        try {
            AbonnementService abonnementService = new AbonnementService();
            Abonnement abonnement = abonnementService.getByName(userName);

            if (abonnement != null) {
                tableview.getItems().clear();
                tableview.getItems().add(abonnement);
            } else {
                tableview.getItems().clear();
                showNotification("No abonnement found for the specified user.");
            }
            
            populateTableView(); // Refresh the TableView with all abonnement data
        } catch (Exception ex) {
            System.err.println("Error while searching for abonnement: " + ex.getMessage());
            showNotification("An error occurred while searching for the abonnement.");
        }
    } else {
        showNotification("Please enter a user name to search for an abonnement.");
    }
}

public void initialize(URL url, ResourceBundle rb) {
    // Set cell value factories for each column
    nomab.setCellValueFactory(new PropertyValueFactory<>("user"));
    coursab.setCellValueFactory(new PropertyValueFactory<>("cours"));
    typeab.setCellValueFactory(new PropertyValueFactory<>("typeAbonnement"));
    datedeb.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
    dateexp.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));

    // Populate the TableView with abonnement data
    populateTableView();

    // Create a context menu for the table rows
   ContextMenu contextMenu = new ContextMenu();
    MenuItem modifyMenuItem = new MenuItem("Modify Abonnement");
    modifyMenuItem.setOnAction(event -> {
        Abonnement selectedAbonnement = tableview.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            // Pass the selected abonnement to the ModifyAbonnementController
            openModifyAbonnementPage(selectedAbonnement);
        }
    });
    contextMenu.getItems().add(modifyMenuItem);

    // Attach the context menu to the TableView
    tableview.setContextMenu(contextMenu);

    // Add a mouse listener to detect right-click for the context menu
    tableview.setRowFactory(tv -> {
        TableRow<Abonnement> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(tableview, event.getScreenX(), event.getScreenY());
            }
        });
        return row;
    });
}


private void openModifyAbonnementPage(Abonnement abonnement) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAbonnement.fxml"));
        Parent root = loader.load();
        ModifyAbonnementController modifyController = loader.getController();

        // Pass the selected Abonnement to the ModifyAbonnementController
        modifyController.initData(abonnement); // Call the method on the instance

        Stage stage = new Stage();
        stage.setTitle("Modify Abonnement");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
    
    
    
    
 //Method to populate the TableView with abonnement data
 private void populateTableView() {
        // Fetch abonnement data from your service
        AbonnementService abonnementService = new AbonnementService(); // Create an instance of AbonnementService
        List<Abonnement> abonnements = abonnementService.getAll();

        // Create an ObservableList from the retrieved data
        ObservableList<Abonnement> abonnementData = FXCollections.observableArrayList(abonnements);

        // Set the data in the TableView
        tableview.setItems(abonnementData);
    }
    }


package CoursifyApp.gui;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import CoursifyApp.entities.Admin;
import CoursifyApp.entities.Instructor;
import CoursifyApp.entities.Student;
import CoursifyApp.gui.AdminProfileController;
import CoursifyApp.services.AdminService;
import CoursifyApp.services.StudentService;
import CoursifyApp.services.InstructorService;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AdminDashboardFXMLController implements Initializable {

    @FXML
    private ListView<Object> listView;
    private AdminService adminService;
    private StudentService studentService;
    private InstructorService instructorService;
    @FXML
    private Button addAdminbtn;
    @FXML
    private Button deleteUserbtn;
    
    private int userId;
    private String userRole;
    @FXML
    private MenuBar adminNavBar;
    @FXML
    private Menu profile;
    @FXML
    private MenuItem gotoprofile;
    @FXML
    private Menu UserManagement;
    @FXML
    private MenuItem manageAdminsClick;
    @FXML
    private MenuItem manageStudentsClick;
    @FXML
    private MenuItem manageInstructorsClick;
    @FXML
    private Menu claimsManagement;
    @FXML
    private MenuItem manageclaims;
    @FXML
    private Menu notifications;
    @FXML
    private MenuItem gotonotifications;
    @FXML
    private Button signOutButton;
    @FXML
    private Button toggleAccountbtn;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField searchField;
    @FXML
    private HBox hbox;
    private String currentMenuSelection;

    private ObservableList<Object> filteredEntities;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentMenuSelection="";
        
            String cssFile = getClass().getResource("login.css").toExternalForm();
            anchorPane.getStylesheets().add(cssFile);
        adminService = new AdminService();
        studentService = new StudentService();
        instructorService = new InstructorService();
        addAdminbtn.setVisible(false);
        deleteUserbtn.setVisible(false);
        toggleAccountbtn.setVisible(false);
        hbox.setVisible(false);
            filteredEntities = FXCollections.observableArrayList();
            listView.setItems(filteredEntities);
    }

    public void initData(int userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
    }

    @FXML
    private void manageAdminsClick(ActionEvent event) {
        listView.setVisible(true);
        populateEntities(adminService.getAll());
        addAdminbtn.setVisible(true);
        deleteUserbtn.setVisible(true);
        toggleAccountbtn.setVisible(true);
        hbox.setVisible(true);
        currentMenuSelection="admins";
    }

    @FXML
    private void manageStudentsClick(ActionEvent event) {
        listView.setVisible(true);
        populateEntities(studentService.getAll());
        addAdminbtn.setVisible(false);
        deleteUserbtn.setVisible(true);
        toggleAccountbtn.setVisible(true);
        hbox.setVisible(true);
        currentMenuSelection="students";
    }

    @FXML
    private void manageInstructorsClick(ActionEvent event) {
        listView.setVisible(true);
        populateEntities(instructorService.getAll());
        addAdminbtn.setVisible(false);
        deleteUserbtn.setVisible(true);
        toggleAccountbtn.setVisible(true);
        hbox.setVisible(true);
        currentMenuSelection="instructors";
    }
    private void populateEntities(List<?> entities) {
    listView.getItems().setAll(entities);
    listView.setCellFactory(param -> new ListCell<Object>() {
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                if (item instanceof Admin) {
                    Admin admin = (Admin) item;
                    setText("Admin: Username - " + admin.getUsername() +
                            ", Email - " + admin.getEmail() +
                            ", FirstName - " + admin.getFirstName() +
                            ", LastName - " + admin.getLastName() +
                            ", Date of Birth - " + admin.getDateOfBirth() +
                            ", Gender - " + admin.getGender() +
                            ", Address - " + admin.getAddress() +
                            ", Phone Number - " + admin.getPhoneNumber() +
                            
                            ", Registration Date - " + admin.getRegistrationDate());
                } else if (item instanceof Student) {
                    Student student = (Student) item;
                    setText("Student: Username - " + student.getUsername() +
                            ", Email - " + student.getEmail() +
                            ", FirstName - " + student.getFirstName() +
                            ", LastName - " + student.getLastName() +
                            ", Date of Birth - " + student.getDateOfBirth() +
                            ", Gender - " + student.getGender() +
                            ", Address - " + student.getAddress() +
                            ", Phone Number - " + student.getPhoneNumber() +
                          
                            ", Registration Date - " + student.getRegistrationDate() +
                            ", Major - " + student.getMajor() +
                            ", Grade - " + student.getCurrentGradeLevel());
                } else if (item instanceof Instructor) {
                    Instructor instructor = (Instructor) item;
                    setText("Instructor: Username - " + instructor.getUsername() +
                            ", Email - " + instructor.getEmail() +
                            ", FirstName - " + instructor.getFirstName() +
                            ", LastName - " + instructor.getLastName() +
                            ", Date of Birth - " + instructor.getDateOfBirth() +
                            ", Gender - " + instructor.getGender() +
                            ", Address - " + instructor.getAddress() +
                            ", Phone Number - " + instructor.getPhoneNumber() +
                           
                            ", Registration Date - " + instructor.getRegistrationDate() +
                            ", Bio - " + instructor.getBio() +
                            ", Experience - " + instructor.getYearsOfExperience());
                }
            }
        }
    });
}

    @FXML
    private void addAdmin(ActionEvent event) {
      Stage currentStage = (Stage) listView.getScene().getWindow();

    try {
        // Close the current stage
        currentStage.close();

        // Load the CreateAdminAccount.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccountAdmin.fxml"));
        Parent adminCreationRoot = loader.load();
        CreateAccountAdminController controller = loader.getController();
        controller.initData(userId, userRole);

        // Create a new stage for the "Add Admin" window
        Stage stage = new Stage();
        stage.setTitle("Add Admin"); // Set the window title
        Scene scene = new Scene(adminCreationRoot);
        stage.setScene(scene);

        // Show the new stage with its default size
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        // Handle any exceptions that may occur during loading
    }
    }

    @FXML
    private void deleteUser(ActionEvent event) {
            Object selectedItem = listView.getSelectionModel().getSelectedItem();

    if (selectedItem != null) {
        // Check the type of the selected item and handle deletion accordingly
        if (selectedItem instanceof Admin) {
            Admin admin = (Admin) selectedItem;
            adminService.delete(admin.getId()); // Delete the Admin using your service
        } else if (selectedItem instanceof Student) {
            Student student = (Student) selectedItem;
            studentService.delete(student.getId()); // Delete the Student using your service
        } else if (selectedItem instanceof Instructor) {
            Instructor instructor = (Instructor) selectedItem;
            instructorService.delete(instructor.getId()); // Delete the Instructor using your service
        }

        // Remove the selected item from the list view
        listView.getItems().remove(selectedItem);
    } else {
        // Display an error message or handle the case where no item is selected
        // You can use a dialog or other method to inform the user.
    }
    }

    @FXML
    private void goToadminProfile(ActionEvent event) {
        try {
            // Load the AdminProfile.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminProfile.fxml"));
            Parent adminProfileRoot = loader.load();

            // Pass the user ID and role to the AdminProfileController if needed
            AdminProfileController adminProfileController = loader.getController();
            adminProfileController.initData(userId, userRole);

            // Create a new scene
            Scene adminProfileScene = new Scene(adminProfileRoot);

            // Get the current stage
            Stage currentStage = (Stage) listView.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(adminProfileScene);
            currentStage.setTitle("Admin Profile");
            currentStage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }

    @FXML
private void goToaClaims(ActionEvent event) throws IOException {
    // Handle navigation to claims management
    FXMLLoader loader = new FXMLLoader(getClass().getResource("affich.fxml"));
    Parent root = loader.load();

    // Créer une nouvelle instance de Stage
    Stage newStage = new Stage();

    Scene scene = new Scene(root);

    newStage.setTitle("Affichage");
    newStage.setScene(scene);
    newStage.show();
}


       // Handle any exceptions that may occur during loading
        
    

    @FXML
    private void goToNotifications(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("affidhd.fxml"));
            Parent InstructorProfileRoot = loader.load();

            // Pass the user ID and role to the AdminProfileController if needed
            

            // Create a new scene
            Scene instructorProfileScene = new Scene(InstructorProfileRoot);

            // Get the current stage
            Stage currentStage = (Stage) adminNavBar.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(instructorProfileScene);
            currentStage.setTitle("");
            currentStage.show();
        } 

    @FXML
    private void signOut(ActionEvent event) {
        try {
            // Load the Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loader.load();

            // Create a new scene
            Scene loginScene = new Scene(loginRoot);

            // Get the current stage
            Stage currentStage = (Stage) listView.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during loading
        }
    }
      @FXML
    private void toggleSelectedUser(ActionEvent event) {
        Object selectedItem = listView.getSelectionModel().getSelectedItem();

    if (selectedItem != null) {
        // Check the type of the selected item and handle the toggle accordingly
        if (selectedItem instanceof Admin) {
            Admin admin = (Admin) selectedItem;
            adminService.toggleAccountStatus(admin.getId()); 
             showSuccessDialog( "Account status action", "Admin account status tggoled" );
        } else if (selectedItem instanceof Student) {
            Student student = (Student) selectedItem;
            studentService.toggleAccountStatus(student.getId()); 
              showSuccessDialog( "Account status action", "Student account status tggoled" );
        } else if (selectedItem instanceof Instructor) {
            Instructor instructor = (Instructor) selectedItem;
            instructorService.toggleAccountStatus(instructor.getId());
             showSuccessDialog( "Account status action", "Instructor account status tggoled" );
        }
        
       

        // Update the displayed data to reflect the new account status
        // You may need to refresh the listView or update the UI elements accordingly.
    } 
    }
    
       private void showSuccessDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOK);

        alert.showAndWait();
    }

@FXML
private void searchButtonClicked(ActionEvent event) {
    String email = searchField.getText().toLowerCase().trim();

    if (email.isEmpty()) {
        // If the search field is empty, reset the list to display all entities based on the current selection.
        switch (currentMenuSelection) {
            case "admins":
                populateEntities(adminService.getAll());
                break;
            case "students":
                populateEntities(studentService.getAll());
                break;
            case "instructors":
                populateEntities(instructorService.getAll());
                break;
            default:
                // Handle the default case or show an error
        }
    } else{
        
          switch (currentMenuSelection) {
            case "admins":
                populateEntities(adminService.getAdminByEmail(email));
                break;
            case "students":
                populateEntities(studentService.getByEmail(email));
                break;
            case "instructors":
                 populateEntities(instructorService.getInstructorByEmail(email));
                break;
            default:
                // Handle the default case or show an error
        }
        
        
    }
}






}

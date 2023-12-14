/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Course;
import CoursifyApp.services.CourseService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hassa
 */
public class OneCourseListCardControler implements Initializable {

    @FXML
    private ImageView img;
    @FXML
    private Text CourseName;
    @FXML
    private Text stockProduit;
    @FXML
    private Text Description;
    @FXML
    private Text stockProduit11;
    @FXML
    private HBox priceHbox;
    @FXML
    private Text price;
    @FXML
    private HBox editCourse;
    @FXML
    private HBox deleteCourse;
    @FXML
    private Text stockProduit1;
    @FXML
    private Text stockProduit12;
    private Course course;
    @FXML
    private Text instructorName;
    @FXML
    private Text Courseinstructor;
    @FXML
    private Button btngotoabonnement;
   

    /**
     * Initializes the controller class.
     */
   public void setCourseData(Course course) {
            // Instancier le service de produit
            CourseService CourseService = new CourseService();
            this.course = course ;

           // String s="C:\\Users\\zaghd\\Desktop\\lacrim\\PidevJava\\src\\Pidev\\OffresUploads\\" + course.getImage_offre();
            String s="C:\\Users\\hassa\\Videos\\CoursifyApp\\CoursifyApp\\src\\CoursifyApp\\image\\" + course.getImage();
            //System.out.println(s);
                        File file = new File(s);
            if(file.exists()) {
                Image image = new Image(file.toURI().toString());
                img.setImage(image);
            } else {
                System.out.println(file);
                System.out.println("test");
            }
             instructorName.setText(course.getInstructorName());
            CourseName.setText(course.getTitle());
            // get category Name
            Description.setText(course.getDescription());
    
            price.setText("" + course.getPrice());
             deleteCourse.setId(String.valueOf(course.getCourseId()));

            deleteCourse.setOnMouseClicked(event -> {
            System.out.println("ID du offre à supprimer : " + course.getCourseId());
            CourseService.delete((int) course.getCourseId()); // supprimer le contenu de la liste et afficher la nouvelle liste(apres
                // supprimer)

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListCourse.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis le controller de
                // OneProductListCard.fxml
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de ProductsList.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // end
        });
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
           
            }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void abonner(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Abonnement.fxml"));
        Parent root = loader.load();

        // Create a new stage for the "Abonnement" page
        Stage abonnementStage = new Stage();
        abonnementStage.setScene(new Scene(root));

        // Set the title of the "Abonnement" window
        abonnementStage.setTitle("Abonnement");

        // Close the current stage
        Stage currentStage = (Stage) btngotoabonnement.getScene().getWindow();
        currentStage.close();

        // Show the "Abonnement" window
        abonnementStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
}

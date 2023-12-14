/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.gui;

import CoursifyApp.entities.Course;
import CoursifyApp.services.CourseService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author hassa
 */
public class ListeCourseControler implements Initializable {

    @FXML
    private Pane content_area;
    @FXML
    private GridPane courseListContainer;
    CourseService CourseService = new CourseService();
    @FXML
    private TextField txtRechercher1;
    @FXML
    private TextField txtRechercher;
    
     private ObservableList<Course> courseList = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instancier le service de produit
        List<Course> course;

        


             List<Course> courses= CourseService.getAll();
            
   
      

        // product list ------------------------------------------------
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < courses.size() ; i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OneCourseListCard.fxml"));
                HBox OneCourseCard = fxmlLoader.load();
               OneCourseListCardControler courseCardController = fxmlLoader.getController();
                courseCardController.setCourseData(courses.get(i));

                if (column == 1) {
                    column = 0;
                    ++row;
                }
                courseListContainer.add(OneCourseCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(OneCourseCard, new Insets(0, 10, 25, 10));
                OneCourseCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

//    private void searchcourse(ActionEvent event) {
//        
//        
//        
//    }
    
}

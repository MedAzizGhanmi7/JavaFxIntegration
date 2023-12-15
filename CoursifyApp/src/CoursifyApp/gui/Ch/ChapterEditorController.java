package CoursifyApp.gui.Ch;

import CoursifyApp.entities.Chapitre;
import CoursifyApp.entities.Course;
import CoursifyApp.services.ChapitreService;
import CoursifyApp.services.InstructorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ChapterEditorController {
    @FXML
    public TableView<Chapitre> chaptersView;
    private List<Chapitre> originalChapitres;
    private Course course;
    /*public void initialize()
    {
        ChapitreService.getInstance().createTableIfNotExist();
        Utils.setupChaptersTable(chaptersView);
        // for testing, we load all chapters. but IRL need to load chapters only related to a course x.
        originalChapitres = ChapitreService.getInstance().getAll();
        chaptersView.getItems().addAll(ChapitreService.getInstance().getAll());
    }*/
    public void initialize(Course _course)
    {
        course = _course;
        System.out.println(course);
        ChapitreService.getInstance().createTableIfNotExist();
        Utils.setupChaptersTable(chaptersView);
        // load course chapters
        originalChapitres = new ArrayList<>();
        for(Chapitre chapitre : ChapitreService.getInstance().getAll())
        {
            if(chapitre.getCourseId() == course.getCourseId())
            {
                originalChapitres.add(chapitre);
            }
        }
        chaptersView.getItems().addAll(originalChapitres);
    }
    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent)
    {
        updateChapitresInDB();

        ButtonType sendButtonType = new ButtonType("Send Email");
        ButtonType cancelButtonType = new ButtonType("Cancel");
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to send an email?", sendButtonType, cancelButtonType);
        confirmationDialog.setTitle("Email Confirmation");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == sendButtonType)
            {
                String emailTitle = "Your Coursify course ("+course.getTitle()+") was changed successfully!";
                String emailContent = "Make sure to look at the new reviews for further adjustements!";

                InstructorService iser = new InstructorService();
                String target = iser.getById((int)course.getInstructorId()).getEmail();
                        //"aymen.ayoo@gmail.com";

                System.out.println("Sending email to: " + target);
                Utils.sendMail(target, emailTitle, emailContent);
            }
        });
    }

    void updateChapitresInDB()
    {
        for(Chapitre c: originalChapitres)
        {
            boolean wasDeleted = true;
            for(Chapitre newC: chaptersView.getItems())
            {
                if(c.getId() == newC.getId())
                {
                    wasDeleted = false;
                    break;
                }
            }
            if(wasDeleted)
            {
                ChapitreService.getInstance().fullDelete(c);
            }
        }

        for(Chapitre chapitre : chaptersView.getItems())
        {
            boolean contains = false;
            for(Chapitre c: originalChapitres)
            {
                if(c.getId() == chapitre.getId())
                {
                    contains = true;
                    break;
                }
            }
            // if existing, update it
            if(contains)
            {
                if(!Utils.AssertNotNullOrALERT(chapitre.getName()))
                    return;
            }
            if(contains)
            {
                ChapitreService.getInstance().update(chapitre);
            }
            else // otherwise add it to DB
            {
                ChapitreService.getInstance().add(chapitre);
            }
        }

        if(course == null)
        {
            System.out.println("ERRRRR NO COURSE");
            return;
        }
        originalChapitres = new ArrayList<>();
        for(Chapitre chapitre : ChapitreService.getInstance().getAll())
        {
            if(chapitre.getCourseId() == course.getCourseId())
            {
                originalChapitres.add(chapitre);
            }
        }
    }


    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        Chapitre c = new Chapitre();
        c.setCourseId((int) course.getCourseId());
        Utils.addChaptersToChaptersTable(chaptersView, c);
        // need to uupdate DB, otherwise when edit is clicked it will show error of foreign key not found
        updateChapitresInDB();
    }
    @FXML
    public void onRemoveButtonClick(ActionEvent actionEvent) {
        Utils.removeSelectedChapter(chaptersView);
    }
    @FXML
    public void onQuitButtonClick(ActionEvent actionEvent) {
        Utils.quitCurrentWindow(actionEvent);
    }
    @FXML
    public void onEditButtonClick(ActionEvent actionEvent) {
        Utils.editSelectedChapter(chaptersView);
    }

    public void onPreviewButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("CoursifyApp/gui/Ch/CourseViewer.fxml"));
            Parent root = loader.load();
            CourseViewerController controller = loader.getController();
            controller.initialize(course);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}

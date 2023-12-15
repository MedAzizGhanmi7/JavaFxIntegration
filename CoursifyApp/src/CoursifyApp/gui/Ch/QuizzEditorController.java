package CoursifyApp.gui.Ch;

import CoursifyApp.entities.Chapitre;
import CoursifyApp.entities.ChapitreQuizz;
import CoursifyApp.entities.ChapitreQuizzAnswer;
import CoursifyApp.entities.ChapitreQuizzQuestion;
import CoursifyApp.services.ChapitreQuizzService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class QuizzEditorController {
    @FXML
    public ListView<String> questionsList;
    @FXML
    public TableView<ChapitreQuizzAnswer> answersList;
    private Chapitre chapitre;
    private ChapitreQuizz quizz;
    private int selectedQuestion;
    public void initialize(Chapitre selectedItem)
    {
        ChapitreQuizzService.getInstance().createTableIfNotExist();

        if(selectedItem == null)
            System.err.println("[FileEditorController] Chapitre cannot be null!");
        chapitre = selectedItem;
        initQuizz();

        Utils.setupQuizzEditorAnswersTreeView(answersList);
        Utils.setupQuestionsList(questionsList, quizz);
        Utils.resetAndFillQuestionsList(questionsList, quizz);

        SelectionModel<String> selectionModel = questionsList.getSelectionModel();
        // Add a change listener to the selection model
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected item: " + newValue);
                selectedQuestion = selectionModel.getSelectedIndex();
                Utils.resetAndFillAnswersList(answersList, quizz, selectedQuestion);
            }
        });
    }
    private void initQuizz()
    {
        List<ChapitreQuizz> allQuizzes = ChapitreQuizzService.getInstance().getAll();
        for(ChapitreQuizz elem: allQuizzes)
        {
            if(elem.getChapitreId() == chapitre.getId())
            {
                quizz = elem;
                break;
            }
        }
        if(quizz == null)
        {
            System.out.println("No quizz found for chapitre: " + chapitre.getName() + ". will add one.");
            quizz = new ChapitreQuizz(chapitre.getId());
            ChapitreQuizzService.getInstance().add(quizz);
        }
    }
    public void onAddQuestionButtonClick(ActionEvent actionEvent)
    {
        ChapitreQuizzQuestion newQuestion = new ChapitreQuizzQuestion();
        newQuestion.setText("new question");
        quizz.getQuestions().add(newQuestion);
        Utils.resetAndFillQuestionsList(questionsList, quizz);
    }

    public void onRemoveQuestionButtonClick(ActionEvent actionEvent)
    {
        Utils.removeSelectedQuestion(questionsList);
        quizz.getQuestions().remove(selectedQuestion);
        Utils.resetAndFillQuestionsList(questionsList, quizz);
    }

    public void onAddAnswerButtonClick(ActionEvent actionEvent) {
        if(!Utils.assertAnyQuestionSelected(questionsList))
            return;
        ChapitreQuizzAnswer newAnswer = new ChapitreQuizzAnswer();
        newAnswer.setText("new answer");
        Utils.addAnswersToTreeView(answersList, newAnswer);
        quizz.getQuestions().get(selectedQuestion).getAnswers().add(newAnswer);
    }

    public void onRemoveAnswerButtonClick(ActionEvent actionEvent) {
        Utils.removeSelectedAnswer(answersList);

        SelectionModel<ChapitreQuizzAnswer> selectionModel = answersList.getSelectionModel();
        ChapitreQuizzAnswer selectedItem = selectionModel.getSelectedItem();
        quizz.getQuestions().get(selectedQuestion).getAnswers().remove(selectedItem);
    }

    public void onAIButtonClick(ActionEvent actionEvent) {

    }

    public void onGenerateRandomQuizzButtonClick(ActionEvent actionEvent) {
        ArrayList<ChapitreQuizzQuestion> newQuestions = Utils.getTriviaQuestions(1);
        quizz.getQuestions().addAll(newQuestions);
        Utils.resetAndFillQuestionsList(questionsList, quizz);
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
        for(ChapitreQuizzQuestion q : quizz.getQuestions())
        {
            if(!Utils.AssertNotNullOrALERT(q.getText(), "Question"))
                return;
            for(ChapitreQuizzAnswer a : q.getAnswers())
            {
                if(!Utils.AssertNotNullOrALERT(a.getText(), "Answer"))
                    return;
            }
        }
        ChapitreQuizzService.getInstance().update(quizz);
    }

    public void onQuitButtonClick(ActionEvent actionEvent) {
        Utils.quitCurrentWindow(actionEvent);
    }

}

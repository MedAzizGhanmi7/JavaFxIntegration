package CoursifyApp.gui.Ch;

import CoursifyApp.entities.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Utils {

    static final String emailAddress = "";
    static final String emailPassword = "";
    public static String getStringSizeLengthFile(long size)
    {
        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;

        if(size < sizeMb)
            return df.format(size / sizeKb)+ " Kb";
        else if(size < sizeGb)
            return df.format(size / sizeMb) + " Mb";
        else if(size < sizeTerra)
            return df.format(size / sizeGb) + " Gb";

        return "";
    }

    public static void readTTS(String text) {
        //https://stackoverflow.com/a/48606173
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();
        System.out.println("Available Voices:" + voices.length);
        for (Voice voice : voices) {
            System.out.println(voice.getName());
        }
        System.out.println("----------");
        Voice voice = voiceManager.getVoice("kevin16");
        //Voice voice = voiceManager.getVoice("kevin16");
        voice.setVolume(1.0f);
        voice.setRate(100.f);
        voice.allocate();
        voice.speak(text);
        voice.deallocate();
    }

    public static ArrayList<ChapitreQuizzQuestion> getTriviaQuestions(int numberOfQuestions) {
        ArrayList<ChapitreQuizzQuestion> questionsList = new ArrayList<>();

        try {
            String apiUrl = "https://opentdb.com/api.php?amount=" + numberOfQuestions + "&type=multiple";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            // Parse JSON response to extract trivia questions and answers
            JSONObject jsonObject = new JSONObject(content.toString());
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject questionObject = results.getJSONObject(i);
                String questionText = questionObject.getString("question");

                JSONArray incorrectAnswersArray = questionObject.getJSONArray("incorrect_answers");
                ArrayList<ChapitreQuizzAnswer> answers = new ArrayList<>();
                for (int j = 0; j < incorrectAnswersArray.length(); j++) {
                    answers.add(new ChapitreQuizzAnswer(incorrectAnswersArray.getString(j), false));
                }

                // Add the correct answer
                answers.add(new ChapitreQuizzAnswer(questionObject.getString("correct_answer"), true));

                ChapitreQuizzQuestion question = new ChapitreQuizzQuestion();
                question.setText(questionText);
                question.setAnswers(answers);
                questionsList.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionsList;
    }

    // https://rollbar.com/blog/how-to-use-chatgpt-api-with-java/
    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromChatGPTJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String extractMessageFromChatGPTJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }


    public static List<String> getGoogleAutocompleteSuggestions(String query) throws IOException, JSONException {
        String apiUrl = "http://suggestqueries.google.com/complete/search?client=firefox&q=" + query.replace(" ", "%20");
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        List<String> suggestions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Parse the JSON response
            JSONArray jsonArray = new JSONArray(response.toString());
            JSONArray suggestionsArray = jsonArray.getJSONArray(1);
            for (int i = 0; i < suggestionsArray.length(); i++) {
                String suggestion = suggestionsArray.getString(i);
                suggestions.add(suggestion);
            }

        } finally {
            connection.disconnect();
        }

        return suggestions;
    }

    public static void sendMail(String targetUserMail, String title, String content)
    {
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730

        // Email properties
        String host = //"smtp.example.com"; // Your SMTP server address
                        "smtp.gmail.com";

        // the sender email
        String username = emailAddress;
        String password = emailPassword;

        // Set up properties for the mail session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // Use port 587 for TLS

        // Create a mail session with the specified properties
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetUserMail));

            // Set the email subject and text
            message.setSubject(title);
            message.setText(content);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void quitCurrentWindow(ActionEvent actionEvent) {
        // Platform.exit();
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public static void setupQuestionsList(ListView<String> list, ChapitreQuizz quizz)
    {
        list.setEditable(true);
        list.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        list.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = list.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < quizz.getQuestions().size()) {
                    showInputDialog(list, quizz, selectedIndex);
                }
            }
        });

    }

    private static void showInputDialog(ListView<String> list, ChapitreQuizz quizz, int selectedIndex) {
        TextInputDialog dialog = new TextInputDialog(list.getItems().get(selectedIndex));
        dialog.setTitle("Edit Question");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new question:");

        dialog.showAndWait().ifPresent(newText -> {
            quizz.getQuestions().get(selectedIndex).setText(newText);
            list.getItems().set(selectedIndex, newText);
            System.out.println("Question renamed: " + newText);
        });
    }



    public static void resetAndFillQuestionsList(ListView<String> list, ChapitreQuizz quizz)
    {
        list.getItems().clear();
        for (ChapitreQuizzQuestion question : quizz.getQuestions()) {
            list.getItems().add(question.getText());
        }
    }

    public static void resetAndFillAnswersList(TableView<ChapitreQuizzAnswer> answersList,
                                               ChapitreQuizz quizz, int selectedQuestion) {
        answersList.getItems().clear();
        for (ChapitreQuizzAnswer answer : quizz.getQuestions().get(selectedQuestion).getAnswers()) {
            answersList.getItems().add(answer);
        }
    }

    public static void removeSelectedQuestion(ListView<String> list)
    {
        SelectionModel<String> selectionModel = list.getSelectionModel();
        String selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            //System.out.println("Selected item: " + selectedItem.getName());
            list.getItems().remove(selectedItem);
        }
    }


    public static boolean AssertNotNullOrALERT(String name, String prefix) {
        if(isNullOrEmpty(name))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, prefix + " cannot be empty!", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        return true;
    }
    public static boolean AssertNotNullOrALERT(String name){
        return AssertNotNullOrALERT(name, "Name");
    }

    public static boolean assertAnyQuestionSelected(ListView<String> list) {

        SelectionModel<String> selectionModel = list.getSelectionModel();
        String selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "No question selected!", ButtonType.OK);
        alert.showAndWait();
        return false;
    }

    public static void setupQuizzEditorAnswersTreeView(TableView<ChapitreQuizzAnswer> table)
    {
        TableColumn<ChapitreQuizzAnswer, String> answerTextCol = new TableColumn<>("Answer");
        answerTextCol.setMinWidth(150);
        answerTextCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        answerTextCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<ChapitreQuizzAnswer, Boolean> isCorrectCol = new TableColumn<>("Correct?");
        isCorrectCol.setMinWidth(100);
        // option 1: use "isCorrect() and getIsCorrect() as getter/setter"
        //isCorrectCol.setCellValueFactory(new PropertyValueFactory<>("isCorrect"));
        // option 2: custom jfx value loader
        isCorrectCol.setCellValueFactory(cellData -> {
            ChapitreQuizzAnswer answer = cellData.getValue();
            BooleanProperty property = new SimpleBooleanProperty(answer.getIsCorrect());
            property.addListener((observable, oldValue, newValue) -> {
                answer.setIsCorrect(newValue);
                System.out.println("Answer was modified!");
            });
            return property;
        });
        isCorrectCol.setCellFactory(col -> {
            CheckBoxTableCell<ChapitreQuizzAnswer, Boolean> checkBoxCell = new CheckBoxTableCell<>();
            checkBoxCell.setAlignment(Pos.CENTER);
            return checkBoxCell;
        });

        // Updating the data (rather than the UI)
        answerTextCol.setOnEditCommit(event -> {
            ChapitreQuizzAnswer answer = event.getRowValue();
            answer.setText(event.getNewValue());
            System.out.println("Answer was modified!");
        });
        isCorrectCol.setOnEditCommit(event -> {
            ChapitreQuizzAnswer answer = event.getRowValue();
            answer.setIsCorrect(event.getNewValue());
            System.out.println("Answer was modified!");
        });

        table.getColumns().addAll(answerTextCol, isCorrectCol);
        table.setEditable(true);
    }

    public static void addAnswersToTreeView(TableView<ChapitreQuizzAnswer> table, ChapitreQuizzAnswer c)
    {
        ObservableList<ChapitreQuizzAnswer> data = table.getItems();
        data.add(c);
    }
    public static void addAnswersToTreeView(TableView<ChapitreQuizzAnswer> table, ObservableList<ChapitreQuizzAnswer> data)
    {
        table.setItems(data);
    }

    public static void removeSelectedAnswer(TableView<ChapitreQuizzAnswer> table)
    {
        SelectionModel<ChapitreQuizzAnswer> selectionModel = table.getSelectionModel();
        ChapitreQuizzAnswer selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            //System.out.println("Selected item: " + selectedItem.getName());
            table.getItems().remove(selectedItem);
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    public static void setupChaptersTable(TableView<Chapitre> table)
    {
        TableColumn<Chapitre, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        //nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellFactory(column -> {
            AutoCompletionTextFieldCellFactory q = new AutoCompletionTextFieldCellFactory();
            AutoCompletionTextField textField = q.getTextField();
            textField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                if(isNullOrEmpty(newValue))
                    return;
                if(!textField.isFocused())
                    return;

                System.out.println("chapter rename: " + oldValue + " to " + newValue);

                try {
                    List<String> suggestions = getGoogleAutocompleteSuggestions(newValue);
                    //System.out.println(suggestions);
                    textField.getEntries().clear();
                    textField.getEntries().addAll(suggestions);
                } catch (IOException e) {
                    System.err.println("Google API error: can't get suggestions");
                } catch (JSONException e) {
                    System.err.println("Google API error: can't get suggestions");
                }
            });
            return q;
        });

        TableColumn<Chapitre, ChapitreType> typeCol = new TableColumn<>("Type");
        typeCol.setMinWidth(50);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setCellFactory(ComboBoxTableCell.forTableColumn(ChapitreType.values()));

        TableColumn<Chapitre, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setMinWidth(250);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setOnEditCommit(event -> {
            /*Chapitre chapitre = event.getRowValue();
            chapitre.setName(event.getNewValue());
            System.out.println("Name commit. " + event.getNewValue());*/

            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < table.getItems().size()) {
                Chapitre chapitre = table.getItems().get(selectedIndex);
                chapitre.setName(event.getNewValue());
                System.out.println("Name commit. " + event.getNewValue());
            } else {
                System.out.println("Invalid row index: " + selectedIndex);
            }

        });
        typeCol.setOnEditCommit(event -> {
            Chapitre chapitre = event.getRowValue();
            chapitre.setType(event.getNewValue());
        });
        descriptionCol.setOnEditCommit(event -> {
            Chapitre chapitre = event.getRowValue();
            chapitre.setDescription(event.getNewValue());
        });


        table.getColumns().addAll(nameCol, typeCol, descriptionCol);
        table.setEditable(true);
    }
    public static void addChaptersToChaptersTable(TableView<Chapitre> table, Chapitre c)
    {
        ObservableList<Chapitre> data = table.getItems();
        data.add(c);
    }
    public static void addChaptersToChaptersTable(TableView<Chapitre> table, ObservableList<Chapitre> data)
    {
        table.setItems(data); // overrides existing items.
    }

    public static void removeSelectedChapter(TableView<Chapitre> table)
    {
        SelectionModel<Chapitre> selectionModel = table.getSelectionModel();
        Chapitre selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            //System.out.println("Selected item: " + selectedItem.getName());
            table.getItems().remove(selectedItem);
        }
    }
    public static void editSelectedChapter(TableView<Chapitre> table)
    {
        SelectionModel<Chapitre> selectionModel = table.getSelectionModel();
        Chapitre selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            //System.out.println("Selected item: " + selectedItem.getName());
            ChapitreType type = selectedItem.getType();
            if(type == ChapitreType.Quizz)
            {
                try {
                    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("CoursifyApp/gui/Ch/QuizzEditor.fxml"));
                    Parent root = loader.load();
                    QuizzEditorController quizzEditorController = loader.getController();
                    quizzEditorController.initialize(selectedItem);

                    Stage quizzEditorStage = new Stage();
                    quizzEditorStage.initModality(Modality.APPLICATION_MODAL);
                    quizzEditorStage.setTitle("Quizz Editor");
                    quizzEditorStage.setScene(new Scene(root));
                    quizzEditorStage.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
            else
            {
                try {
                    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("CoursifyApp/gui/Ch/FileEditor.fxml"));
                    Parent root = loader.load();
                    FileEditorController controller = loader.getController();
                    controller.initialize(selectedItem);

                    Stage fileEditorStage = new Stage();
                    controller.setStage(fileEditorStage);
                    fileEditorStage.initModality(Modality.APPLICATION_MODAL);
                    fileEditorStage.setTitle("File Editor");
                    fileEditorStage.setScene(new Scene(root));
                    fileEditorStage.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }

            }
        }
    }

}

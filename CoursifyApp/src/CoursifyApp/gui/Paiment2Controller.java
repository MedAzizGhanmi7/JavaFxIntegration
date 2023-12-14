package CoursifyApp.gui;



import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class Paiment2Controller implements Initializable {

    @FXML
     TextField date;
    @FXML
     TextField cvv;
    @FXML
     TextField tele;
    @FXML
     TextField rib;
     
    @FXML
    private Label erib;
    @FXML
    private Label edate;
    @FXML
    private Label ecvv;
    @FXML
    private Label etel;
private static final String ACCOUNT_SID = "ACdc69f87d9f1adb6751c2899d6b60f481";
    private static final String AUTH_TOKEN = "4e5b14557d7981c67d434c6943d3b79c";
    private static final String TWILIO_PHONE_NUMBER = "+15086194484";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }

    @FXML
 
private void payer(ActionEvent event) {
  String phoneNumber = tele.getText();
   String cvvText = cvv.getText();
    String ribText = rib.getText();
    String teleText = tele.getText();
    String dateText = date.getText();
    erib.setText("");
    edate.setText("");
    ecvv.setText("");
    etel.setText("");
     if (cvvText.isEmpty() || ribText.isEmpty() || teleText.isEmpty() || dateText.isEmpty()) {
        // Vérifiez que tous les champs sont remplis
        erib.setText("Tous les champs doivent être remplis.");
            edate.setText(" les champs doivent être remplis.");
             ecvv.setText(" les champs doivent être remplis.");
             etel.setText(" les champs doivent être remplis.");
              
        return;
    }
     else{

    if (!cvvText.matches("\\d{3}")) {
        // Affichez un message d'erreur pour le CVV
        ecvv.setText("Le CVV doit contenir exactement 3 chiffres.");
        return;
    }

    if (!ribText.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
        // Affichez un message d'erreur pour le RIB
         erib.setText("Le RIB doit être au format XXXX-XXXX-XXXX-XXXX, où X est un chiffre.");
        return;
    }

    if (!teleText.matches("\\+216\\d{8}")) {
        // Affichez un message d'erreur pour le numéro de téléphone
         etel.setText("Le numéro de téléphone doit commencer par +216 suivi de 8 chiffres..");
        return;
    }

    if (!dateText.matches("\\d{4}-\\d{2}")) {
        // Affichez un message d'erreur pour la date
           edate.setText("La date doit être au format AAAA-MM (ex. 2023-10).");
        return;
    }
     }

        String verificationCode = generateVerificationCode();
         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                "Votre code de vérification : " +verificationCode
        ).create();
           TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Vérification du code");
    dialog.setHeaderText("Veuillez saisir le code de vérification envoyé par SMS :");
    dialog.setContentText("Code de vérification :");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent() && result.get().equals(verificationCode)) {
        // Le code de vérification est correct
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Vérification réussie");
        successAlert.setHeaderText(null);
        successAlert.setContentText("La vérification a été réussie. Le paiement est confirmé.");
        successAlert.showAndWait();
    } else {
        // Le code de vérification est incorrect
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Vérification échouée");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Le code de vérification est incorrect. Veuillez réessayer.");
        errorAlert.showAndWait();
    }
}
  

        private String generateVerificationCode() {
    Random rand = new Random();
    int code = rand.nextInt(9000) + 1000;
    return Integer.toString(code);
}
}
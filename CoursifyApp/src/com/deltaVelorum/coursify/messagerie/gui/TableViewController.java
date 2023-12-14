/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltaVelorum.coursify.messagerie.gui;

import CoursifyApp.entities.Message;
import CoursifyApp.gui.UpdateViewController;
import CoursifyApp.services.MessageService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author ghanm
 */
public class TableViewController implements Initializable {

    @FXML
    private TableView<Message> messageTable;
    @FXML
    private TableColumn<Message, Integer> idColumn;
    @FXML
    private TableColumn<Message, String> subjectColumn;
    @FXML
    private TableColumn<Message, String> contentColumn;
    @FXML
    private TableColumn<Message, String> dateColumn;
    @FXML
    private TableColumn<Message, String> senderColumn;
    @FXML
    private TableColumn<Message, String> recipientColumn;
    private MessageService messageService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messageService = new MessageService();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("objetMessage"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("contenuMessage"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateEnvoi"));
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("emailEmetteur"));
        recipientColumn.setCellValueFactory(new PropertyValueFactory<>("emailDestinataire"));

        populateTable();
    }

    @FXML
    private void Update(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateView.fxml"));
            Parent root = fxmlLoader.load();
            UpdateViewController updateViewController = fxmlLoader.getController();
            Message selectedMessage = messageTable.getSelectionModel().getSelectedItem();
            if (selectedMessage != null) {
                updateViewController.setSelectedMessage(selectedMessage);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucun message sélectionné");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un message pour la mise à jour.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateTable() {
        messageTable.getItems().clear();
        List<Message> messages = messageService.getAll();
        messageTable.getItems().addAll(messages);
    }

    @FXML
    private void delete(ActionEvent event) {
        Message selectedMessage = messageTable.getSelectionModel().getSelectedItem();
        if (selectedMessage == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun message sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un message à supprimer.");
            alert.showAndWait();
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce message ?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int messageId = selectedMessage.getId();
                messageService.delete(messageId);
                populateTable();
            }
        }
    }

    @FXML
    private void Return(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SendMessage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

@FXML
private void Import(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Sélectionner le fichier à importer");
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        try {
            FileInputStream file = new FileInputStream(selectedFile);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                int id = 0;
                String subject = "";
                String content = "";
                String date = "";
                String sender = "";
                String recipient = "";

                Cell idCell = row.getCell(0);
                if (idCell.getCellType() == CellType.NUMERIC) {
                    id = (int) idCell.getNumericCellValue();
                }

                Cell subjectCell = row.getCell(1);
                if (subjectCell.getCellType() == CellType.STRING) {
                    subject = subjectCell.getStringCellValue();
                }

                Cell contentCell = row.getCell(2);
                if (contentCell.getCellType() == CellType.STRING) {
                    content = contentCell.getStringCellValue();
                }

                Cell dateCell = row.getCell(3);
                if (dateCell.getCellType() == CellType.STRING) {
                    date = dateCell.getStringCellValue();
                }

                Cell senderCell = row.getCell(4);
                if (senderCell.getCellType() == CellType.STRING) {
                    sender = senderCell.getStringCellValue();
                }

                Cell recipientCell = row.getCell(5);
                if (recipientCell.getCellType() == CellType.STRING) {
                    recipient = recipientCell.getStringCellValue();
                }

                if (!date.isEmpty()) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        java.util.Date parsed = format.parse(date);
                        Date sqlDate = new Date(parsed.getTime());

                        Message message = new Message(id, subject, content, sqlDate, sender, recipient);
                        messageService.add(message);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            populateTable();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Import réussi");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été importées avec succès depuis le fichier sélectionné.");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucun fichier sélectionné");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un fichier à importer.");
        alert.showAndWait();
    }
}




    @FXML
    private void Export(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Messages");

            // Création de l'en-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Subject");
            headerRow.createCell(2).setCellValue("Content");
            headerRow.createCell(3).setCellValue("Date");
            headerRow.createCell(4).setCellValue("Sender");
            headerRow.createCell(5).setCellValue("Recipient");

            // Ajout des données de la table
            int rowNum = 1;
            for (Message message : messageTable.getItems()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(message.getId());
                row.createCell(1).setCellValue(message.getObjetMessage());
                row.createCell(2).setCellValue(message.getContenuMessage());
                row.createCell(3).setCellValue(message.getDateEnvoi().toString());
                row.createCell(4).setCellValue(message.getEmailEmetteur());
                row.createCell(5).setCellValue(message.getEmailDestinataire());
            }

            // Écriture dans le fichier Excel
            FileOutputStream fileOut = new FileOutputStream("messages.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            // Affichage d'une alerte pour indiquer que l'export a réussi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export réussi");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été exportées avec succès vers le fichier messages.xlsx.");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }}
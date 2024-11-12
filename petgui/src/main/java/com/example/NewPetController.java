package com.example;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

// bring all the necessary imports first before jumping into the class
public class NewPetController {
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField costField;
    @FXML private ImageView imageView;
    private String imagePath;

    @FXML
    private void handleSave() {

        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double cost = Double.parseDouble(costField.getText());

            Pet newPet = new Pet(name, age, cost, imagePath);
            PetDataManager.addPet(newPet);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Pet information has been successfully updated!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update the pet. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Pet Image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePath = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    private Button cancelButton; 

    @FXML
    private void handleCancel(ActionEvent event) {
    nameField.clear();
    ageField.clear();
    costField.clear();
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
    }
}

package com.example;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PrimaryController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField costField;
    @FXML private ImageView petImageView;

    private List<Pet> pets;
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        pets = PetDataManager.loadPets();
        if (!pets.isEmpty()) {
            displayPet(pets.get(currentIndex));
        }

        Circle clip = new Circle(95, 80, 80); 
        petImageView.setClip(clip);
        petImageView.getStyleClass().add("circular-image");
        startFloatingAnimation();
    }

    private void startFloatingAnimation() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), petImageView);
        transition.setByY(15); 
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE); 
        transition.play(); 
    }

    private void displayPet(Pet pet) {
        nameField.setText(pet.getName());
        ageField.setText(String.valueOf(pet.getAge()));
        costField.setText(String.valueOf(pet.getCost()));
        petImageView.setImage(new Image("file:" + pet.getImagePath()));
    }

    @FXML
    private void handleNext() {
        if (currentIndex < pets.size() - 1) {
            currentIndex++;
            displayPet(pets.get(currentIndex));
        }
    }

    @FXML
    private void handlePrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            displayPet(pets.get(currentIndex));
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            Pet pet = pets.get(currentIndex);
            pet.setName(nameField.getText());
            pet.setAge(Integer.parseInt(ageField.getText()));
            pet.setCost(Double.parseDouble(costField.getText()));
            PetDataManager.savePets(pets);
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
    private void handleNewPet() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("new_pet.fxml"));
            Parent newPetRoot = loader.load();

            Stage newPetStage = new Stage();
            newPetStage.setTitle("Add New Pet");
            newPetStage.setScene(new Scene(newPetRoot));

            newPetStage.showAndWait();

            pets = PetDataManager.loadPets();
            currentIndex = pets.size() - 1;
            displayPet(pets.get(currentIndex));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleDelete() {
        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDelete.setTitle("Delete Confirmation");
        confirmDelete.setHeaderText(null);
        confirmDelete.setContentText("Are you sure you want to delete this pet?");

        Optional<ButtonType> result = confirmDelete.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Pet pet = pets.get(currentIndex);
                PetDataManager.deletePet(pet);
                pets.remove(currentIndex);
                if (!pets.isEmpty()) {
                    currentIndex = Math.min(currentIndex, pets.size() - 1);
                    displayPet(pets.get(currentIndex));
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Pet has been successfully deleted!");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete the pet. Please try again.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Pet Image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            pets.get(currentIndex).setImagePath(file.getAbsolutePath());
            petImageView.setImage(new Image("file:" + file.getAbsolutePath()));
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}

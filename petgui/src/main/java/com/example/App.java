package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 520, 350);
        stage.setTitle("Pet Shop");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the root of the scene to a new FXML layout
     * @param fxml the FXML file name (without .fxml extension)
     * @throws IOException if the FXML file cannot be loaded
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads the specified FXML file
     * @param fxml the FXML file name (without .fxml extension)
     * @return the loaded Parent node
     * @throws IOException if the FXML file cannot be loaded
     */
    private static javafx.scene.Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}

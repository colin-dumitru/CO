package edu.simplex.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Catalin Dumitru
 * Universitatea Alexandru Ioan Cuza
 */
public class Main extends Application {
    public static void main(String args[]) throws IOException {
        Application.launch(Main.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("/main.fxml"));
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.setTitle("Simplex Application");
        stage.show();
    }
}

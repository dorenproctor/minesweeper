package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public int windowWidth = 640;
    public int windowHeight = 670;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("minesweeper.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root, windowWidth, windowHeight));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

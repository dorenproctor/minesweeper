package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

public class Controller {

    //DIFFICULTY BOX
    @FXML
    private ChoiceBox difficultyBox;
    @FXML
    private GridPane gp;
    @FXML
    private Button startButton;

    @FXML
    private void initialize() {
        System.out.println("initializing");
        ObservableList<String> difficulties = FXCollections.observableArrayList(
                "easy", "medium", "hard");
        difficultyBox.setItems(difficulties);
        difficultyBox.setValue("easy");

        startGame();
    }

    //START BUTTON


    @FXML
    private void startGame() {
    }
}

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private ChoiceBox difficultyBox;
    @FXML
    private Pane pane;
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

    @FXML
    private void startGame() {
        pane.getChildren().removeAll();
        MineField mf = new MineField();
        mf.grid = pane;
        mf.makeBoard();
        System.out.println("new game started");
        System.out.println("x_tot:" + mf.X_TILES + "  y_tot:" + mf.Y_TILES);

        giveButtonsEvents(mf);
    }

    private void giveButtonsEvents(MineField mf) {
        for (int y=0; y < mf.Y_TILES; y++) {
            for (int x=0; x < mf.X_TILES; x++) {
                System.out.println("X:" + x + "  Y:" + y);
                Button btn = mf.cellArray[y][x].btn;
                btn.setLayoutX(30*x);
                btn.setLayoutY(30*y);
                pane.getChildren().add(btn);
                btn.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY)
                        btn.setText("2");
                    else
                        btn.setText("1");
                    });
            }
        }
    }
}

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private ChoiceBox difficultyBox;
    @FXML
    private Pane pane;
    @FXML
    private Button startButton;
    @FXML
    TextField winOrLoseText;

    @FXML
    private void initialize() { //happens immediately when run
        System.out.println("initializing");
        ObservableList<String> difficulties = FXCollections.observableArrayList(
                "easy", "medium", "hard");
        difficultyBox.setItems(difficulties);
        difficultyBox.setValue("easy");
        winOrLoseText.setStyle("-fx-background-color: transparent");

        startGame();
    }

    @FXML
    private void startGame() { //happens when first run or when startButton is clicked
        pane.getChildren().removeAll();

        MineField mf = new MineField();
        mf.generateBoard();
        System.out.println("new game started");
        //System.out.println("x_tot:" + mf.xTiles + "  y_tot:" + mf.yTiles);

        addMinefieldButtons(mf);
        pane.setPrefHeight(800);
        pane.setStyle("-fx-background-color: lightgray");
        winOrLoseText.setText("");
    }

    private void addMinefieldButtons(MineField mf) {
        for (int x = 0; x < mf.xTiles; x++) {
            for (int y = 0; y < mf.yTiles; y++) {
                //System.out.println("X:" + x + "  Y:" + y);
                Button btn = mf.cellArray[x][y].btn;
                btn.setLayoutX(30*x);
                btn.setLayoutY(30*y);
                btn.setPrefSize(30,30);
                btn.setStyle("-fx-background-insets: 0,1,2");
                btn.setStyle("-fx-padding: 8 8 8 8;");
                //btn.setStyle("-fx-background-radius: 10,10");

                pane.getChildren().add(btn);
                final int i = x; //need final variable for lambda expression
                final int j = y;
                btn.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) { //right click
                        mf.mark(i, j);
                    }
                    else {                                           //left click
                        mf.expose(i, j);
                        cellClicked(mf);
                    }
                    });
            }
        }
    }

    private void cellClicked(MineField mf) {
        if (mf.exploded) {
            winOrLoseText.setText("        GAME OVER");
            freezeCells(mf);
        }
        if (mf.unexposedCells == 0) {
            winOrLoseText.setText("        YOU WIN");
            freezeCells(mf);
        }
    }

    private void freezeCells(MineField mf) {
        for (int x = 0; x < mf.xTiles; x++) {
            for (int y = 0; y < mf.yTiles; y++) {
                Button btn = mf.cellArray[x][y].btn;
                if (mf.exploded && mf.cellArray[x][y].hasMine)
                    btn.setText("!");
                final int i = x; //need final variable for lambda expression
                final int j = y;
                btn.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) { //right click
                        //do nothing
                    }
                    else {                                           //left click
                        //do nothing
                    }
                });
            }
        }
    }
}

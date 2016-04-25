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
    TextField safeCellsLeft;
    @FXML
    TextField minesLeft;


    @FXML
    private void initialize() { //happens immediately when run
        System.out.println("initializing");
        ObservableList<String> difficulties = FXCollections.observableArrayList(
                "Beginner", "Intermediate", "Expert");
        difficultyBox.setItems(difficulties);
        difficultyBox.setValue("Beginner");
        winOrLoseText.setStyle("-fx-background-color: transparent");
        safeCellsLeft.setStyle("-fx-background-color: transparent");
        minesLeft.setStyle("-fx-background-color: transparent");

        startGame();
    }

    @FXML
    private void startGame() { //happens when first run or when startButton is clicked
        pane.getChildren().clear();
        MineField mf = new MineField();

        setDifficulty(mf);

        mf.generateBoard();
        System.out.println("new game started");
        pane.setPrefHeight(1000);
        pane.setStyle("-fx-background-color: lightgray");
        winOrLoseText.setText("");
        safeCellsLeft.setText("Safe Cells Left: "+mf.unexposedCount());
        minesLeft.setText("Mines Left: "+mf.minesLeft);
        addMinefieldButtons(mf);
    }

    private void setDifficulty(MineField mf) {
        if (difficultyBox.getValue() == "Beginner") {
            mf.H = 320;
            mf.W = 320;
            mf.numMines = 10;
        }else if (difficultyBox.getValue() == "Intermediate") {
            mf.H = 640;
            mf.W = 640;
            mf.numMines = 40;
        }else if (difficultyBox.getValue() == "Expert") {
            mf.H = 640;
            mf.W = 1240;
            mf.numMines = 99;
        }
    }

    private void addMinefieldButtons(MineField mf) {
        for (int x = 0; x < mf.xTiles; x++) {
            for (int y = 0; y < mf.yTiles; y++) {
                Button btn = mf.cellArray[x][y].btn;
                btn.setLayoutX(30*x);
                btn.setLayoutY(30*y);
                btn.setPrefSize(30,30);
                btn.setStyle("-fx-background-insets: 0,1,2");
                btn.setStyle("-fx-padding: 8 8 8 8;");

                pane.getChildren().add(btn);
                final int i = x; //need final variables for lambda expression
                final int j = y;
                btn.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) { //right click
                        mf.mark(i, j);
                        cellClicked(mf);
                    } else {                                           //left click
                        mf.expose(i, j);
                        cellClicked(mf);
                    }
                    });
            }
        }
    }

    private void cellClicked(MineField mf) {
        safeCellsLeft.setText("Safe Cells Left: "+mf.unexposedCount());
        minesLeft.setText("Mines Left: "+mf.minesLeft);
        if (mf.exploded) {
            winOrLoseText.setText("    GAME OVER");
            freezeCells(mf);
        }
        if (mf.unexposedCells == 0) {
            winOrLoseText.setText("    YOU WIN");
            freezeCells(mf);
        }
    }

    private void freezeCells(MineField mf) {
        for (int x = 0; x < mf.xTiles; x++) {
            for (int y = 0; y < mf.yTiles; y++) {
                Button btn = mf.cellArray[x][y].btn;
                if (mf.exploded && mf.cellArray[x][y].hasMine) //when you lost
                    btn.setText("!"); //it reveals all the mines
                btn.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) { //right click
                        //do nothing
                    } else {                                           //left click
                        //do nothing
                    }
                });
            }
        }
    }
}

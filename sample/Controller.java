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

    private int cellSize;

    @FXML
    private ChoiceBox difficultyBox;
    @FXML
    private Button startButton;
    @FXML
    private Pane pane;
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
        startGame();
    }


    @FXML
    private void startGame() { //happens when first run or when startButton is clicked
        pane.getChildren().clear();
        MineField mf = new MineField();
        setDifficulty(mf);
        mf.generateBoard();
        winOrLoseText.setText("");
        safeCellsLeft.setText("Safe Cells Left: "+mf.unexposedCount());
        minesLeft.setText("Mines Left: "+mf.minesLeft);
        addMinefieldButtons(mf);
        System.out.println("new game started");
    }


    private void setDifficulty(MineField mf) {
        if (difficultyBox.getValue() == "Beginner") {
            mf.H = 8;
            mf.W = 8;
            mf.numMines = 10;
            cellSize = 80;
        }else if (difficultyBox.getValue() == "Intermediate") {
            mf.H = 16;
            mf.W = 16;
            mf.numMines = 40;
            cellSize = 40;
        }else if (difficultyBox.getValue() == "Expert") {
            mf.H = 24;
            mf.W = 24;
            mf.numMines = 99;
            cellSize = 27;
        }
    }


    private void addMinefieldButtons(MineField mf) {
        for (int x = 0; x < mf.xTiles; x++) {
            for (int y = 0; y < mf.yTiles; y++) {
                Button btn = mf.cellArray[x][y].btn;
                btn.setLayoutX(cellSize*x);
                btn.setLayoutY(cellSize*y);
                btn.setPrefSize(cellSize,cellSize);
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
            winOrLoseText.setText("GAME OVER");
            freezeCells(mf);
        }
        if (mf.unexposedCells == 0) {
            winOrLoseText.setText("YOU WIN");
            freezeCells(mf);
        }
    }


    private void freezeCells(MineField mf) {
        for (int x = 0; x < mf.xTiles; x++) {
            for (int y = 0; y < mf.yTiles; y++) {
                Button btn = mf.cellArray[x][y].btn;
                if (mf.exploded && mf.cellArray[x][y].hasMine) //when you lose
                    btn.setText("!"); //it reveals all the mines
                btn.setOnMouseClicked(event -> { //clicks don't do anything
                    if (event.getButton() == MouseButton.SECONDARY) {
                        //do nothing
                    }
                });
            }
        }
    }
}

package minesweeper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;

public class Controller {

    MineField minefield;
    Timer timer;
    boolean timeStarted;
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
    TextField timeText;


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
        minefield = new MineField();
        setDifficulty();
        minefield.generateBoard();
        addMinefieldButtons();
        timer = new Timer();
        timeStarted = false;
        winOrLoseText.setText("");
        safeCellsLeft.setText("Safe Cells Left: "+ minefield.unexposedCount());
        timeText.setText("Time:  0");
        System.out.println("new game started");
    }


    private void setDifficulty() {
        if (difficultyBox.getValue() == "Beginner") {
            minefield.H = 8;
            minefield.W = 8;
            minefield.numMines = 10;
            cellSize = 80;
        }else if (difficultyBox.getValue() == "Intermediate") {
            minefield.H = 16;
            minefield.W = 16;
            minefield.numMines = 40;
            cellSize = 40;
        }else if (difficultyBox.getValue() == "Expert") {
            minefield.H = 24;
            minefield.W = 24;
            minefield.numMines = 99;
            cellSize = 27;
        }
    }


    private void addMinefieldButtons() {
        for (int x = 0; x < minefield.W; x++) {
            for (int y = 0; y < minefield.H; y++) {
                Button btn = minefield.cellArray[x][y].btn;
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
                        minefield.mark(i, j);
                    } else {                                           //left click
                        minefield.expose(i, j);
                    }
                    cellClicked();
                    updateTimer();
                });
                btn.setOnMouseMoved(event -> {
                    if (timeStarted)
                        updateTimer();
                });
            }
        }
    }


    private void cellClicked() {
        if (!timeStarted) {
            timer.startOver();
            timeStarted = true;
        }
        safeCellsLeft.setText("Safe Cells Left: "+ minefield.unexposedCount());
        if (minefield.exploded) {
            winOrLoseText.setText("GAME OVER");
            freezeCells();
        }
        if (minefield.unexposedCells == 0) {
            winOrLoseText.setText("YOU WIN");
            freezeCells();
        }
    }


    private void updateTimer() {
        if (!(minefield.exploded || (minefield.unexposedCells == 0)))
            timeText.setText("Time:  "+timer.timePassed());
    }


    private void freezeCells() {
        for (int x = 0; x < minefield.W; x++) {
            for (int y = 0; y < minefield.H; y++) {
                Button btn = minefield.cellArray[x][y].btn;
                if (minefield.exploded && minefield.cellArray[x][y].hasMine) //when you lose
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

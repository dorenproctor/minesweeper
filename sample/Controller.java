package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private void initialize() { //happens immediately when run
        System.out.println("initializing");
        ObservableList<String> difficulties = FXCollections.observableArrayList(
                "easy", "medium", "hard");
        difficultyBox.setItems(difficulties);
        difficultyBox.setValue("easy");

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
    }

    private void addMinefieldButtons(MineField mf) {
        for (int x = 0; x < mf.xTiles; x++) {
            for (int y = 0; y < mf.yTiles; y++) {
                //System.out.println("X:" + x + "  Y:" + y);
                Button btn = mf.cellArray[x][y].btn;
                btn.setLayoutX(30*x);
                btn.setLayoutY(30*y);
                btn.setPrefSize(30,30);

                if (mf.cellArray[x][y].hasBomb)
                    btn.setText("X");
                else {
                    String neighboringMines = Integer.toString(mf.cellArray[x][y].neighboringMines);
                    btn.setText(neighboringMines);
                    }

                pane.getChildren().add(btn);
                btn.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) //right click
                        btn.setText("2");
                    else                                            //left click
                        btn.setText("1");
                    });
            }
        }
    }
}

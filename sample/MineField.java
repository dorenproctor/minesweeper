package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MineField extends Application {

    final int TILE_SIZE = 40;
    final int H = 1000;
    final int W = 800;
    final int X_TILES = (W / TILE_SIZE);
    final int Y_TILES = (H / TILE_SIZE);





    public Button[][] board = new Button[X_TILES][Y_TILES];

    public GridPane makeBoard() {
        GridPane grid = new GridPane();
        grid.setPrefSize(W, H);

        for(int y=0; y<Y_TILES; y++) {
            for(int x=0; x<X_TILES; x++) {
                Button btn = new Button();
                btn.setLayoutX(x);
                btn.setLayoutY(y);
                board[x][y] = btn;
                grid.getChildren().add(btn);
            }
        }

        return null;
    }

    public boolean mark(int column, int row) {
        //if (cell is not marked)
            //cell is marked
        //if (cell is marked)
            //cell is not marked
        return true;
    }


    public int expose(int column, int row) {
        //if (cell is not marked)
            //expose cell
        //if a cell was safely exposed and no bombs are adjacent {
            //newly revealed neighbors can be revealed by call(s) to isExposed
            return 0;
        //}
        //if (cell was safely exposed and has bombs next to it)
            //return number of bombs next to it
        //if (bomb exploded)
            //return -1;
    }

    public int isExposed(int column, int row) {
        return 0;
    }

    public int unexposedCount() {
        return 0; //number of unexposed cells
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("minesweeper.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

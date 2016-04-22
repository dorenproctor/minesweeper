package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class MineField extends Application {

    public final int TILE_SIZE = 40;
    public final int H = 800;
    public final int W = 720;
    public final int X_TILES = (W / TILE_SIZE);
    public final int Y_TILES = (H / TILE_SIZE);

    public Pane grid;



    public Cell[][] cellArray = new Cell[X_TILES][Y_TILES];

    public void makeBoard() {
        grid.setPrefSize(W, H);

        for(int x=0; x<X_TILES; x++) {
            for(int y=0; y<Y_TILES; y++) {
                Cell newCell = new Cell();
                cellArray[x][y] = newCell;
            }
        }

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
        primaryStage.setScene(new Scene(root, 30*X_TILES, 30*Y_TILES+30));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Random;

public class MineField extends Application {

    public int tileSize = 40;
    public int H = 800;
    public int W = 720;
    public int xTiles = (W / tileSize);
    public int yTiles = (H / tileSize);
    public int numMines = 20;
    public int minesLeft;
    public Cell[][] cellArray = new Cell[xTiles][yTiles];


    public void generateBoard() {
        for(int x = 0; x< xTiles; x++) {
            for(int y = 0; y< yTiles; y++) { //for every cell
                Cell newCell = new Cell();
                cellArray[x][y] = newCell;
            }
        }

        generateMines();
        calculateTileValues();
    }

    private void generateMines() {
        Random rnd = new Random();
        minesLeft = 0;
        while (minesLeft < numMines) {
            Cell randomCell = cellArray[rnd.nextInt(cellArray.length)][rnd.nextInt(cellArray[0].length)];
            randomCell.hasBomb = true;
            minesLeft++;
        }
    }

    private void calculateTileValues() {
        for(int x = 0; x< xTiles; x++) {
            for(int y = 0; y< yTiles; y++) { //for every cell
                Cell cell = cellArray[x][y];
                cell.addNeighbors(x, y, cellArray); //find neighbors
                for(Cell neighbor : cell.neighbors) { //for each neighbor
                    if (neighbor != null && neighbor.hasBomb) {
                        cell.neighboringMines++;
                    }
                }
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
        primaryStage.setScene(new Scene(root, 30* xTiles, 30* yTiles +30));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

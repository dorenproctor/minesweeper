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
    public int unexposedCells;
    public Cell[][] cellArray = new Cell[xTiles][yTiles];


    public void generateBoard() {
        //xTiles = (W / tileSize);
        //yTiles = (H / tileSize);
        //cellArray = new Cell[xTiles][yTiles];
        for(int x = 0; x< xTiles; x++) {
            for(int y = 0; y< yTiles; y++) { //for every cell
                Cell newCell = new Cell();
                cellArray[x][y] = newCell;
                newCell.x = x;
                newCell.y = y;
                unexposedCells++;
            }
        }
        generateMines();
        unexposedCells = unexposedCells - numMines; //safe tiles to click initial value
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

    public boolean mark(int column, int row) { //inverts cell.marked
        cellArray[column][row].marked = !cellArray[column][row].marked;
        return cellArray[column][row].marked;
    }


    public int expose(int column, int row) { //yay for recursion
        Cell cell = cellArray[column][row];
        if (cell.marked)
            return -2;
        if (cell.exposed)
            return -3;

        cell.exposed = true;
        unexposedCells--; //one less cell left to expose

        if (cell.hasBomb) //game over
            return -1;

        if (cell.neighboringMines == 0) { //next to no mines
            for(Cell neighbor : cell.neighbors) { //for each neighbor
                if (neighbor != null && neighbor.neighboringMines == 0) { //next to no mines
                    if (isExposed(neighbor.x,neighbor.y) == 0) //because guidelines
                        expose(neighbor.x,neighbor.y); //expose the neighbor!
                }
            }
            return 0;
        }

        return cell.neighboringMines; //should never happen
    }

    public int isExposed(int column, int row) {
        if (cellArray[column][row].exposed) return 1;
        else return 0; //returning int instead of boolean because of guidelines
    }

    public int unexposedCount() {
        return unexposedCells; //number of unexposed cells
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

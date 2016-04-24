package sample;

import javafx.scene.control.Button;

public class Cell {
    public boolean hasMine;
    public boolean exposed;
    public boolean marked;
    public Cell[] neighbors = new Cell[8]; //up to 8 neighbors
    public int numNeighbors;
    public int neighboringMines;
    public int x, y;
    public Button btn = new Button();


    /*** Find neighbors in a double Cell array based on its x and y position ***/
    public void addNeighbors( int x, int y, Cell[][] cellArray) {
        numNeighbors = 0;
        for (int i = -1; i <= 1; i++) {
            if ( (x+i > -1) && (x+i < cellArray.length) ) {
                for (int j = -1; j <= 1; j++) {
                    if ( (y+j > -1) && (y+j < cellArray[0].length) && !(i==0 && j==0)) {
                        neighbors[numNeighbors++] = cellArray[x+i][y+j];
                        if (cellArray[x+i][y+j].hasMine) {
                            neighboringMines++;
                        }
                    } //if (not out of bounds && not current cell)
                }           //add to neighbors array
            }               //if (neighbor has a mine)
        }                       //add a mine to neighboringMines
    }
}
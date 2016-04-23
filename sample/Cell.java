package sample;

import javafx.scene.control.Button;

public class Cell {
    public boolean hasBomb;
    public boolean exposed;
    public Cell[] neighbors = new Cell[8]; //up to 8 neighbors
    public int numNeighbors;
    public int neighboringMines;
    public Button btn = new Button();


    /*** Find neighbors in a double Cell array based on its x and y position ***/
    public void addNeighbors( int x, int y, Cell[][] cellArray) {
        numNeighbors = 0;
        System.out.println("CURRENT CELL X:"+x+"  Y:"+y);
        //System.out.println(cellArray.length + "   " + cellArray[0].length);
        for (int i = -1; i <= 1; i++) {
            //System.out.println("X:"+x+" Y:"+y+" x+i: "+(x+i));
            if ( (x+i > -1) && (x+i < cellArray.length) ) {
                for (int j = -1; j <= 1; j++) {
                    //System.out.println("X:"+x+" Y:"+y+" x+i: "+(x+i)+" y+j: "+(y+j));
                    if ( (y+j > -1) && (y+j < cellArray[0].length) && !(i==0 && j==0)) {
                        neighbors[numNeighbors++] = cellArray[x+i][y+j];
                        System.out.println("ADDING X:"+(x+i)+"  Y:"+(y+j));
                    } //if (not out of bounds && not current cell)
                }           //add to neighbor array
            }
        }
        System.out.println("numNeighbors:"+numNeighbors);
    }
}
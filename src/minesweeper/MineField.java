package minesweeper;

import java.util.Random;

public class MineField {

    public int H = 20;
    public int W = 20;
    public int numMines = 20;
    public int minesLeft;
    public int unexposedCells;
    public boolean exploded;
    public Cell[][] cellArray;


    public void generateBoard() {
        cellArray = new Cell[W][H];
        unexposedCells = 0;
        for(int x = 0; x< W; x++) {
            for(int y = 0; y< H; y++) { //for every cell
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
            if (!randomCell.hasMine) { //prevents the same cell from getting multiple mines
                randomCell.hasMine = true;
                minesLeft++;
            }
        }
    }


    private void calculateTileValues() {
        for(int x = 0; x< W; x++) {
            for(int y = 0; y< H; y++) { //for every cell
                Cell cell = cellArray[x][y];
                cell.searchNeighbors(x, y, cellArray); //find neighbors and neighbors' mines
            }
        }
    }


    public boolean mark(int column, int row) { //inverts cell.marked
        Cell cell = cellArray[column][row];
        if (cell.exposed) //can't mark exposed cells
            return false;

        cell.marked = !cell.marked; //invert marked
        if (cell.marked) {
            cell.btn.setText("X");
            minesLeft--;
        }else {
            cell.btn.setText("");
            minesLeft++;
        }
        return cellArray[column][row].marked;
    }


    public int expose(int column, int row) { //yay for recursion
        Cell cell = cellArray[column][row];
        if (cell.marked) //can't expose marked cells
            return -2;

        if (cell.hasMine) { //game over
            cell.btn.setText("!");
            cell.btn.setStyle("-fx-border-color: darkgray; -fx-background-color: red");
            exploded = true;
            return -1;
        }

        if (cell.exposed) { //if the proper amount of neighbors are marked around the
            int neighborsMarked = 0; //exposed cell then expose the neighboring cells
            for(Cell neighbor : cell.neighbors)
                if (neighbor != null && neighbor.marked)
                    neighborsMarked++;
            if (neighborsMarked == cell.neighboringMines)
                exposeNeighbors(cell);
        }

        if (!cell.exposed)
            unexposedCells--; //one less cell left to expose
        cell.exposed = true;
        cell.btn.setStyle("-fx-border-color: darkgray; -fx-background-color: lightgray");


        if (cell.neighboringMines == 0) { //next to no mines
            exposeNeighbors(cell);
            return 0;
        }

        cell.btn.setText(Integer.toString(cell.neighboringMines)); //show value
        return cell.neighboringMines;
    }


    private void exposeNeighbors(Cell cell) {
        for(Cell neighbor : cell.neighbors) //for each neighbor
            if (neighbor != null && !neighbor.exposed)
                expose(neighbor.x,neighbor.y); //expose the neighbor!
    }


    public int unexposedCount() {
        return unexposedCells; //number of unexposed cells
    }

}

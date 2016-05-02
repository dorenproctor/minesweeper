/* Author: Doren Proctor */
package minesweeper;

public class Timer {

    private long startTime;

    public void startOver() {
        startTime = System.currentTimeMillis();
    }


    public int timePassed() {
        double time = (System.currentTimeMillis() - startTime)/1000.0;
        return (int) time;
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle15;

/**
 *
 * @author rodrigo
 */
public class State implements Comparable<State> {
    
    public boolean opened;     
    public Puzzle puzzle;
    public String father; //P(n)
    private int startDistance; //g(n)
    private int finalDistance; //h(n)
    private int totalDistance; //f(n) = g(n) + h(n)

   
    
    public void setTotalDistance(int startDistance, int finalDistance) {
        this.totalDistance = startDistance + finalDistance;
    }

    public int getStartDistance() {
        return startDistance;
    }

    public int getFinalDistance() {
        return finalDistance;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    

    @Override
    public int compareTo(State s) {
        if (this.totalDistance < s.totalDistance) {
            return -1;
        }
        return 1;
    }
}

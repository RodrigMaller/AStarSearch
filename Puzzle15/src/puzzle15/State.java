/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle15;

/**
 *
 * @author rodrigo
 */
public class State implements Comparable<State>{
    
    public int puzzle[][] = new int[4][4];
    public Integer key;
    public boolean open;
    public Integer father; //P(n)
    public int distance; //g(n)

    @Override
    public int compareTo(State s) {
        if (this.distance < s.distance) {
            return -1;
        }
        return 1;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author rodrigo
 */
public class AStar {
    
    private PriorityQueue<State> OpenedStates;
    private HashMap<Integer,State> ClosedStates;
    private ArrayList<State> Sucessors;
}

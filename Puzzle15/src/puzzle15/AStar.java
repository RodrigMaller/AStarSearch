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
    
    private PriorityQueue<State> openedStates;
    private HashMap<Integer,State> closedStates;
    private ArrayList<State> sucessors;
    private int finalState[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
    
    public void executa(State s){
        this.openedStates = new PriorityQueue<State>();
        this.closedStates = new HashMap<Integer, State>();
        this.sucessors = new ArrayList<State>();
        int hLinha;
        
        this.openedStates.add(s);
        hLinha = calculaHLinha(s);
    }
    
    public int calculaHLinha(State m){
        int hLinha1 = calculaHLinha1(m);
        int hLinha2 = 0;
        int hLinha3 = 0;
        int hLinha;
        
        hLinha = Math.max(hLinha1, hLinha2);
        hLinha = Math.max(hLinha, hLinha3);
        
        return hLinha;
    }
    
    public int calculaHLinha1(State m){
        int i,j;
        int hLinha = 0;
        for (i=1; i<=this.finalState.length; i++){
            for (j=0; j<this.finalState[i].length; j++){
                if (this.finalState[i][j] != m.puzzle[i][j]){
                    hLinha++;
                }
            }
        }
        return hLinha;
    }
}

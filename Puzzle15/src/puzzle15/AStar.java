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
    private HashMap<String, State> closedStates;
    private ArrayList<State> successors;
    private State startState;
    private State finalState;
    private State actualState;
    private int times;

    public AStar(State startState, State finalState) {
        this.startState = startState;
        this.finalState = finalState;
        this.openedStates = new PriorityQueue<State>();
        this.closedStates = new HashMap<String, State>();
    }

    public void perform() throws CloneNotSupportedException {
        init();

        boolean out = solve();

        if (out) {
            System.out.println("Solved");
        } else {
            System.out.println("Fail");
        }

        System.out.println("times: " + times);

    }

    private void init() {
        times = 0;
        startState.father = null;
        startState.setTotalDistance(0, calculaHLinha(startState));
        this.openedStates.add(startState);
    }

    private boolean isFinalState(State s) {
        return (s.puzzle.compareTo(finalState.puzzle) == 0);
    }

    private State getMinState() {
        ArrayList<State> back = new ArrayList<State>();
        State min;
        min = openedStates.poll();
        while (!openedStates.isEmpty()
                && (min.getTotalDistance() == openedStates.peek().getTotalDistance())
                && !isFinalState(min)) {
            back.add(min);
            min = openedStates.poll();
        }
        for (State state : back) {
            openedStates.add(state);
        }
        return min;
    }

    private void addValidSuccessor(Puzzle moved) {
        if ((actualState.puzzle.compareTo(moved) != 0)
                && (actualState != null) && (actualState.puzzle.compareTo(moved) != 0)) {
            State m = new State();
            m.father = actualState.puzzle;
            m.puzzle = moved;
            //g(m)
            m.setTotalDistance(actualState.getStartDistance() + 1, calculaHLinha(m));
            successors.add(m);
            System.out.println("add valid successors");
        }
    }

    private void buildSuccessors() throws CloneNotSupportedException {
        this.successors = new ArrayList<State>();
        Puzzle moved = actualState.puzzle.moveDown();
        
        
        addValidSuccessor(moved);
        Puzzle moved1 = actualState.puzzle.moveLeft();
        addValidSuccessor(moved1);
        Puzzle moved2 = actualState.puzzle.moveRight();
        addValidSuccessor(moved2);
        Puzzle moved3 = actualState.puzzle.moveUp();
        addValidSuccessor(moved3);
        System.out.println("build successors");
    }

    private void updateStates() {
        State mi = null;
        for (State m : successors) {

            //eF
            mi = closedStates.get(m.puzzle.getKey());
            if (mi != null) {
                System.out.println("ef");
                if (m.getStartDistance() < mi.getStartDistance()) {
                    mi = m;
                }
                openedStates.add(mi);
            } else if (openedStates.contains(m)) {
                System.out.println("contains pq");

                ArrayList<State> back = new ArrayList<State>();
                mi = openedStates.poll();
                while (!openedStates.isEmpty()
                        && (!m.puzzle.getKey().equals(mi.puzzle.getKey()))) {
                    back.add(mi);
                    mi = openedStates.poll();
                }

                if ((m.getStartDistance() < mi.getStartDistance())) {
                    back.add(m);
                } else {
                    back.add(mi);
                }

                for (State state : back) {
                    openedStates.add(state);
                }

            } else {
                System.out.println("else");
                openedStates.add(m);
            }

        }

    }

    private boolean solve() throws CloneNotSupportedException {
        times++;
        System.out.println(">>> "+times);
        boolean out = false;
        if (!openedStates.isEmpty()) {
            actualState = this.getMinState();
            closedStates.put(actualState.puzzle.getKey(), actualState);
            if (isFinalState(actualState)) {
                out = true;
            } else {
                buildSuccessors();
                updateStates();
                return solve();
            }
        }
        /*
         if (v.puzzle == finalState) {
         System.out.println("Acabou");
         }*/

        return out;
    }

    public int calculaHLinha(State m) {
        int hLinha1 = calculaHLinha1(m);
        int hLinha2 = 0;
        int hLinha3 = 0;
        int hLinha;

        hLinha = Math.max(hLinha1, hLinha2);
        hLinha = Math.max(hLinha, hLinha3);

        System.out.println(hLinha1);
        return hLinha;
    }

    public int calculaHLinha1(State m) {
        int i, j;
        int hLinha = m.puzzle.compareTo(finalState.puzzle);
        return hLinha;
    }
}

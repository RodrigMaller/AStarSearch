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
    private State actualState;
    private int times;
    private State finalState;
    private String heu;

    public AStar(State startState, State finalState) {
        this.startState = startState;
        this.finalState = finalState;
        this.openedStates = new PriorityQueue<State>();
        this.closedStates = new HashMap<String, State>();
    }

    public void perform() throws CloneNotSupportedException {
        init();
        int out = 0;
        while (out == 0) {
            out = solve();
        }
        if (out == 1) {
            System.out.println("Solved");
        } else {
            System.out.println("Fail");
        }
        System.out.println("times: " + times);
    }

    private void init() {
        times = 0;
        startState.father = null;
        startState.setTotalDistance(0, Heuristics.valueOf(heu).run(startState, finalState));
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
            System.out.println("getmin");
            back.add(min);
            min = openedStates.poll();
        }
        for (State state : back) {
            openedStates.add(state);
        }
        return min;
    }

    private void addValidSuccessor(Puzzle moved) {
        
        System.out.println("times: " +times);
        if ((actualState != null)
                && (actualState.puzzle.compareTo(moved) != 0)
                && ((actualState.father == null)
                || (actualState.father.compareTo(moved) != 0))) {
            System.out.println("fuck");
            State m = new State();
            m.father = actualState.puzzle;
            m.puzzle = moved;
            //g(m)
            m.setTotalDistance(actualState.getStartDistance() + 1, Heuristics.valueOf(heu).run(m, finalState));
            successors.add(m);
        }
    }

    private void buildSuccessors() throws CloneNotSupportedException {
        this.successors = new ArrayList<State>();
        
        Puzzle moved = new Puzzle(actualState.puzzle.getHeight(), actualState.puzzle.getLength());
        actualState.puzzle.moveUp(moved);
        addValidSuccessor(moved);

        moved = new Puzzle(actualState.puzzle.getHeight(), actualState.puzzle.getLength());
        actualState.puzzle.moveDown(moved);
        addValidSuccessor(moved);

        moved = new Puzzle(actualState.puzzle.getHeight(), actualState.puzzle.getLength());
        actualState.puzzle.moveLeft(moved);
        addValidSuccessor(moved);

        moved = new Puzzle(actualState.puzzle.getHeight(), actualState.puzzle.getLength());
        actualState.puzzle.moveRight(moved);
        addValidSuccessor(moved);

    }

    private void updateStates() {
        State mi = null;
        for (State m : successors) {
            mi = closedStates.remove(m.puzzle.getKey());//O(n)
            if (mi != null) {
                if (m.getStartDistance() < mi.getStartDistance()) {
                    mi = m;
                }
                openedStates.add(mi);

            } else if (openedStates.contains(m)) {//O(n)

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
                openedStates.add(m);
            }

        }

    }

    private int solve() throws CloneNotSupportedException {
        times++;
        int out = -1;
        if (!openedStates.isEmpty()) {
            actualState = this.getMinState();
            closedStates.put(actualState.puzzle.getKey(), actualState);
            if (isFinalState(actualState)) {
                out = 1;
            } else {
                buildSuccessors();
                updateStates();
                out = 0;
            }
        }

        return out;
    }
}

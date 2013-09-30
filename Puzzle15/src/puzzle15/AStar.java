/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author rodrigo
 */
public class AStar {

    private PriorityQueue<State> openedStates;
    private HashMap<String, State> states;
    private ArrayList<State> successors;
    private State startState;
    private State finalState;
    private State actualState;
    private int moves, nodes;
    private String heu;

    public AStar(String heus, State startState, State finalState) {
        this.heu = heus;
        this.startState = startState;
        this.finalState = finalState;
        this.openedStates = new PriorityQueue<State>();
        this.states = new HashMap<String, State>();
    }

    public void perform(){
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
        System.out.println("Moves: "+moves);
        System.out.println("Nodes: "+nodes);
    }

    private void init() {
        nodes = 0;
        moves = 0;
        startState.father = null;
        startState.setTotalDistance(0, Heuristics.valueOf(heu).run(startState, finalState));
        this.startState.opened = true;
        this.openedStates.add(startState);
        this.states.put(startState.puzzle.getKey(), startState);
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
        if ((actualState != null)
                && (actualState.puzzle.compareTo(moved) != 0)
                && ((actualState.father == null)
                || (!actualState.father.equals(moved.getKey())))) {
            State m = new State();
            m.father = actualState.puzzle.getKey();
            m.puzzle = moved;
            //g(m)
            m.setTotalDistance(actualState.getStartDistance() + 1, Heuristics.valueOf(heu).run(m, finalState));
            successors.add(m);
        }
    }

    private void buildSuccessors(){
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
        State aux;
        for (State m : successors) {
            aux = states.get(m.puzzle.getKey());
            if (aux != null) {
                if (aux.opened) {
                    if (m.getStartDistance() < aux.getStartDistance()) {
                        openedStates.remove(aux);
                        aux.setTotalDistance(m.getStartDistance(), m.getFinalDistance());
                        aux.father = m.father;
                        m.opened = true;
                        openedStates.add(m);
                    }
                } else {
                    if (m.getStartDistance() < aux.getStartDistance()) {
                        aux.setTotalDistance(m.getStartDistance(), m.getFinalDistance());
                        aux.father = m.father;
                        aux.opened = true;
                    }   
                    openedStates.add(aux);
                }
            } else {
                m.opened = true;
                openedStates.add(m);
                states.put(m.puzzle.getKey(), m);
            }

        }

    }

  

    private void setClosed(State s) {
        s.opened = false;
        State aux = states.get(s.puzzle.getKey());
        if (aux != null) {
            aux.opened = false;
        } else {
            states.put(s.puzzle.getKey(), s);
        }
    }

    private int countMoves(State s){
        State aux = s;
        int out = 0;
        while(aux.father!=null){
            aux = states.get(aux.father);
            out++;
        }
        return out;
    }
    
    private int solve() {
        int out = -1;
        if (!openedStates.isEmpty()) {
            actualState = this.getMinState();
            setClosed(actualState);
            if (isFinalState(actualState)) {
                moves = countMoves(actualState);
                nodes = states.size();
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

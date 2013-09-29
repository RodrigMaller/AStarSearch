/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle15;

/**
 *
 * @author rodrigo
 */
public class Puzzle15 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CloneNotSupportedException {
     Integer finalPuzzle[][] = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};  
     Integer m1[][] = {{1, 6, 2, 3}, {5, 10, 7, 4}, {9, 14, 11, 8}, {13, 0, 15, 12}};   
     Puzzle p1 = new Puzzle(m1,3,1);
     Puzzle end = new Puzzle(finalPuzzle, 3,3);
     
     State s1 = new State();
     s1.puzzle = p1;
     State f = new State();
     f.puzzle = end;
     AStar alg = new AStar(s1, f);
     alg.perform();
     
        
    }
}

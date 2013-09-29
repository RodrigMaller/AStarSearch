/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle15;

/**
 *
 * @author lucas
 */
public class Puzzle implements Cloneable {

    private String key;
    private int map[][];
    public int line0;
    public int column0;

    public Puzzle(int[][] x, int l0, int c0) {
        map = x;
        line0 = l0;
        column0 = c0;
        this.key = "none";
    }

    public void copyMap(Puzzle p){
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getLength(); j++) {
                p.setValue(i, j, this.getValue(i, j));
            }
        }
    }
    public Puzzle(int l,int c) {
        map = new int[l][c];
        this.key = "none";
    }
    

    public String getKey() {
        if (this.key.equals("none")) {
            String strKey = "";
            for (int i = 0; i < this.getHeight(); i++) {
                for (int j = 0; j < this.getLength(); j++) {
                    if (this.getValue(i, j) < 10) {
                        strKey = strKey + "0";
                    }
                    strKey = strKey + this.getValue(i, j).toString();
                }
            }
            return strKey;
        }
        return this.key;
    }

    public int getHeight() {
        return map.length;
    }

    public int getLength() {
        return map.length;
    }

    public Integer getValue(int line, int column) {
        return map[line][column];
    }

    public void setValue(int line, int column, int value) {
        map[line][column] = value;
    }

    private void move(Puzzle moved,int line, int column) throws CloneNotSupportedException {
        
        this.copyMap(moved);
        
        if ((line >= 0) && (line < this.getHeight())
                && (column >= 0) && (column < this.getLength())) {
        
            moved.line0 = line;
            moved.column0 = column;
            int value = this.getValue(line, column);
            moved.setValue(this.line0, this.column0, value);
            moved.setValue(line, column, 0);

            
        }
    }

    public void printPuzzle() {
        for (int i = 0; i < this.getLength(); i++) {
            for (int j = 0; j < this.getLength(); j++) {
                System.out.print(this.getValue(i, j) + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    
    public void moveUp(Puzzle moved) throws CloneNotSupportedException {
        this.move(moved, line0 - 1, column0);
    }
    
    public void moveDown(Puzzle moved) throws CloneNotSupportedException {
         this.move(moved, line0 +1, column0);
    }
   
    public void moveLeft(Puzzle moved) throws CloneNotSupportedException {
         this.move(moved,line0, column0 -1 );
    }

    public void moveRight(Puzzle moved) throws CloneNotSupportedException {
         this.move(moved,line0, column0 + 1);
    }

    public void searchValue(int value, Integer line, Integer column) {
        line = 0;
        column = 0;
        while (line < this.getHeight()) {
            while (column < this.getLength() && this.getValue(line, column) != value) {
                column++;
            }
            line++;
        }
        line--;
    }
    
    public int compareTo(Puzzle other) {
        int out = 0;
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getLength(); j++) {
                if (this.getValue(i, j) != other.getValue(i, j)) {
                    out++;
                    //System.out.println("entro");
                }
            }
        }
        return out;
    }

    
}

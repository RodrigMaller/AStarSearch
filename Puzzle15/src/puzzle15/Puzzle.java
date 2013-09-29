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
    private Integer map[][];
    private int line0;
    private int column0;

    public Puzzle(Integer[][] x, int l0, int c0) {
        map = x;
        line0 = l0;
        column0 =c0;
        this.key = "none";
    }
    
    public String getKey() {
        if (this.key.equals("none")) {
            String strKey = "";
            for (int i = 0; i < this.getHeight(); i++) {
                for (int j = 0; j < this.getLength(); j++) {
                    if(this.getValue(i, j)<10){
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

    private Puzzle move(int line, int column) throws CloneNotSupportedException {
        Puzzle moved;
        moved = (Puzzle) this.clone();
        if ((line >= 0) && line < this.getHeight()
                && column >= 0 && column < this.getLength()) {
           
            this.printPuzzle();
            
            System.out.println(line+" "+column);
            
            int value = this.getValue(line, column);
            
            moved.setValue(this.line0, this.column0, value);
            moved.setValue(line, column, 0);
            moved.line0 = line;
            moved.column0 = column;
            moved.printPuzzle();
            
            
            return moved;

        }
        return moved;
    }

    public void printPuzzle(){
        for(int i=0; i<this.getLength(); i++){
            for(int j=0; j<this.getLength(); j++){
                System.out.print(this.getValue(i, j)+" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    public Puzzle moveDown() throws CloneNotSupportedException {
        return this.move(line0 - 1, column0);
    }

    public Puzzle moveUp() throws CloneNotSupportedException {
        return this.move(line0 + 1, column0);
    }

    public Puzzle moveLeft() throws CloneNotSupportedException {
        return this.move(line0, column0 - 1);
    }

    public Puzzle moveRight() throws CloneNotSupportedException {
        return this.move(line0, column0 + 1);
    }

    public int compareTo(Puzzle other) {
        int out = 0;
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getLength(); j++) {
                if (this.getValue(i, j) != other.getValue(i, j)) {
                    out++;
                }
            }
        }
        return out;
    }
}

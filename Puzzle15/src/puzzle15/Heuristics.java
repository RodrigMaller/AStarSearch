/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle15;

/**
 *
 * @author rodrigo
 */
public enum Heuristics {
    heu1 {
        @Override
        public int run(State m, State finalState) {
            int hLine = this.calcHLine1(m, finalState);
            return hLine;
        }
    }, heu2 {
        @Override
        public int run(State m, State finalStat) {
            int hLine = calcHLine2(m);
            return hLine;
        }
    }, heu3 {
        @Override
        public int run(State m, State finalState) {
            int hLine = calcHLine3(m, finalState);
            return hLine;
        }
    }, heuMax {
        @Override
        public int run(State m, State finalState) {
            int hLine = calcHLineMax(m, finalState);
            return hLine;
        }
    };

    public abstract int run(State m, State finalState);

    public int calcHLine1(State m, State finalState) {
        int i, j;
        int hLinha = m.puzzle.compareTo(finalState.puzzle);
        return hLinha;
    }

    public int calcHLine2(State m) {
        int line, column;
        int hLine = 0;

        for (line = 0; line < m.puzzle.getHeight(); line++) {
            for (column = 0; column < m.puzzle.getLength(); column++) {
                if (column == (m.puzzle.getLength() - 1)) {
                    if (m.puzzle.getValue(line, column) != (m.puzzle.getValue(line + 1, 0) - 1)) {
                        hLine++;
                    }
                } else {
                    if (m.puzzle.getValue(line, column) != (m.puzzle.getValue(line, column + 1) - 1)) {
                        hLine++;
                    }
                }
            }
        }
        return hLine;
    }

    public int calcHLine3(State m, State finalState) {
        int line, column;
        int hLine = 0;
        Integer finalLine, finalColumn;
        finalLine = new Integer(0);
        finalColumn = new Integer(0);
        for (line = 0; line < m.puzzle.getHeight(); line++) {
            for (column = 0; column < m.puzzle.getLength(); column++) {
                int actualValue = m.puzzle.getValue(line, column);

                if (m.puzzle.getValue(line, column) != finalState.puzzle.getValue(line, column)) {
                    finalState.puzzle.searchValue(m.puzzle.getValue(line, column), finalLine, finalColumn);
                    hLine = (finalLine + finalColumn) - (line + column);
                    hLine = Math.abs(hLine);
                }
            }
        }
        return hLine;
    }

    public int calcHLineMax(State m, State finalState) {
        int hLine, hLine1, hLine2, hLine3;

        hLine1 = this.calcHLine1(m, finalState);
        hLine2 = this.calcHLine2(m);
        hLine3 = this.calcHLine3(m, finalState);

        hLine = Math.max(hLine1, hLine2);
        hLine = Math.max(hLine, hLine3);

        return (hLine);
    }
}

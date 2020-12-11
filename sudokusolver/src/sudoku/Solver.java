package sudoku;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Den här klassen implementerar interfacet SudokuSolver och löser sudoku.
 */
public class Solver implements SudokuSolver {
    private int[][] board;

    public Solver() {
        board = new int[9][9];
    }

    private boolean inRange(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }

    private boolean inRange (int row, int col, int number) {
        return inRange(row, col) && number > 0 && number <= 9;
    }

    /**
     * Sätter siffran i rutan row, col
     *
     * @param row    raden
     * @param col    kolumnen
     * @param number Siffran som ska sättas in
     * @throws IllegalArgumentException om siffran är utanför [1..9]
     *                                  eller row eller col är utanför [0..8]
     */
    @Override
    public void setNumber(int row, int col, int number) {
        if (inRange(row, col, number)) {
            board[row][col] = number;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Kollar om siffran number kan sättas i raden row och kolumnen col, om det inte
     * går enligt spelreglerna returneras false
     *
     * @param row    raden
     * @param col    kolumnen
     * @param number Siffran som ska testas
     * @return True ifall det är ett tillåtet drag.
     * @throws IllegalArgumentException om siffran är utanför [1..9] eller om row
     *                                  eller col är utanför [0..8]
     */
    @Override
    public boolean trySetNumber(int row, int col, int number) {
        if (!inRange(row, col, number)) {
            throw new IllegalArgumentException();
        }

        boolean isValid = true;

        ArrayList<Integer> rowNum = new ArrayList<>(9);
        ArrayList<Integer> colNum = new ArrayList<>(9);

        // Kolla lodrätt och vågrätt men inte på ens egen ruta
        for (int i = 0; i < 9; i++) {
            if (i != col) rowNum.add(board[row][i]);
            if (i != row) colNum.add(board[i][col]);
        }
        rowNum.removeIf(e -> e == 0);
        colNum.removeIf(e -> e == 0);
        rowNum.add(number);
        colNum.add(number);

        // Kolla ifall någon siffra är upprepad
        if (rowNum.size() != (new HashSet<>(rowNum)).size() ||
            colNum.size() != (new HashSet<>(colNum)).size()) isValid = false;

        if (!checkSquare(row, col, number)) isValid = false;
        return isValid;
    }

    private boolean checkSquare(int row, int col, int number) {
        // Genom heltalsdivision så blir resultatet 0,1 eller 2.
        int rowQuad = row / 3;
        int colQuad = col / 3;

        // Skala upp igen
        rowQuad *= 3;
        colQuad *= 3;

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(number);
        // Varje ruta är 3x3
        for (int i = rowQuad; i < rowQuad + 3; i++) {
            for (int j = colQuad; j < colQuad + 3; j++) {
                if (row != i && col != j) {
                    numbers.add(board[i][j]);
                }
            }
        }

        numbers.removeIf(e -> e == 0);

        return numbers.size() == (new HashSet<>(numbers)).size();
    }

    /**
     * Returnerar siffran på raden row och kolumnen col.
     *
     * @param row    raden
     * @param col    kolumnen
     * @return Siffran i på raden row och kolumnen col.
     * @throws IllegalArgumentException om row eller col är utanför [0..8]
     */
    @Override
    public int getNumber(int row, int col) {
        if (inRange(row, col)) {
            return board[row][col];
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Tar bort siffran på raden row och kolumnen col.
     *
     * @param row    raden
     * @param col    kolumnen
     * @throws IllegalArgumentException om row eller col är utanför [0..8]
     */
    @Override
    public void removeNumber(int row, int col) {
        if (inRange(row, col)) {
            board[row][col] = 0;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Tömmer hela sudokut
     */
    @Override
    public void clear() {
        board = new int[9][9];
    }

    /**
     * Löser sudokut och returnerar true om sudokut går att lösa.
     * @return True om en lösning hittades.
     */
    @Override
    public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int r, int c) {
        boolean status = false;
        if (board[r][c] == 0) {
            for (int i = 1; i <= 9; i++) {
                if (trySetNumber(r, c, i)) {
                    setNumber(r, c, i);
                    status = nextCell(r, c);

                    // Stanna loopen
                    if (status)  break;
                }
            }
            if (!status) {
                board[r][c] = 0;
            }
        } else {
            if (trySetNumber(r, c, board[r][c])) {
                status = nextCell(r, c);
            }
        }
        return status;
    }

    private boolean nextCell(int r, int c) {
        if (c == 8 && r == 8) { // Sista rutan
            return  true;
        }
        else if (c == 8) {
            return solve(r+1, 0);
        } else {
            return solve(r, c+1);
        }
    }

    /**
     * Returnerar siffrorna i sudokut.
     * @return Siffrorna i sudokut.
     */
    @Override
    public int[][] getNumbers() {
        return board.clone();
    }

    /**
     * Fyller i siffrorna i numbers i sudokut.
     *
     * @param numbers Siffrorna som ska sättas in
     * @throws IllegalArgumentException om inte alla nummer är i [0..9]
     **/
    @Override
    public void setNumbers(int[][] numbers) {
        for (int[] row: numbers) {
            for (int col: row) {
                if (col > 9 || col < 0) throw new IllegalArgumentException();
            }
        }
        board = numbers;
    }
}

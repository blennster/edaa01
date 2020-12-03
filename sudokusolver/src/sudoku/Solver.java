package sudoku;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver implements SudokuSolver {
    private int[][] board;
    private int backtrackcnt = 0;

    public Solver() {
        board = new int[9][9];
    }

    private boolean inRange(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }

    private boolean inRange (int row, int col, int number) throws IllegalArgumentException {
        return inRange(row, col) && number > 0 && number <= 9;
    }

    @Override
    public void setNumber(int row, int col, int number) {
        if (inRange(row, col, number)) {
            board[row][col] = number;
        } else {
            throw new IllegalArgumentException();
        }
    }

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

        // Check if any value is duplicated
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

    @Override
    public int getNumber(int row, int col) {
        if (inRange(row, col)) {
            return board[row][col];
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeNumber(int row, int col) {
        if (inRange(row, col)) {
            board[row][col] = 0;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void clear() {
        board = new int[9][9];
    }

    @Override
    public boolean solve() {
        backtrackcnt = 0;
        return solve(0, 0);
    }

    private boolean solve(int r, int c) {
        boolean status = false;
        if (board[r][c] == 0) {
            for (int i = 1; i <= 9; i++) {
                if (trySetNumber(r, c, i)) {
                    setNumber(r, c, i);
                    if (c == 8 && r == 8) { // Sista rutan
                        status = true;
                    }
                    else if (c == 8) {
                        status = solve(r+1, 0);
                    } else {
                        status = solve(r, c+1);
                    }
                    if (status) {
                        break;
                    }
                }
            }
        } else {
            if (trySetNumber(r, c, board[r][c])) {
                if (c == 8 && r == 8) { // Sista rutan
                    status = true;
                }
                else if (c == 8) {
                    status = solve(r+1, 0);
                } else {
                    status = solve(r, c+1);
                }
            }
            if (!status) {
                return false;
            }
        }
        if (!status) {
            board[r][c] = 0;
            backtrackcnt++;
            System.out.printf("[BACKTRACK:%d] r: %d, c: %d\n", backtrackcnt, r, c);
        }
        return status;
    }

    @Override
    public int[][] getNumbers() {
        return board;
    }

    @Override
    public void setNumbers(int[][] numbers) {
        board = numbers;
    }

    public boolean preCheck() {
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] != 0) {
                        trySetNumber(i, j, board[i][j]);
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

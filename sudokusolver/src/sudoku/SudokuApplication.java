package sudoku;

public class SudokuApplication {
    public static void main(String[] args) {
        SudokuController c = new SudokuController(new Solver());
    }
}

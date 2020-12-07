package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sudoku.Solver;
import sudoku.SudokuSolver;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

public class TestSolver {
    private static SudokuSolver solver;

    @BeforeAll
    public static void setup() {
        solver = new Solver();
    }

    @Test
    public void testAdd() {
        assertThrows(IllegalArgumentException.class, () -> solver.setNumber(-1, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> solver.setNumber(0, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> solver.setNumber(0, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> solver.setNumber(0, 0, 10));
    }

    @Test
    public void testGet() {
        assertThrows(IllegalArgumentException.class, () -> solver.getNumber(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> solver.getNumber(0, -1));
        assertEquals(0, solver.getNumber(0, 0));
    }

    @Test
    public void testAddAndGet() {
        solver.setNumber(0,0,1);
        assertEquals(1, solver.getNumber(0,0));
        solver.setNumber(0,0,2);
        assertEquals(2, solver.getNumber(0,0));
        solver.setNumber(3,1,2);
        assertEquals(2, solver.getNumber(3,1));
    }

    @Test
    public void testTryAdd() {
        solver.setNumber(0,0, 1);
        solver.setNumber(1,1, 2);
        solver.setNumber(2,2, 4);

        assertFalse(solver.trySetNumber(0, 1, 1));
        assertFalse(solver.trySetNumber(0, 1, 2));
        assertTrue(solver.trySetNumber(0, 1, 3));
        assertFalse(solver.trySetNumber(0, 1, 4));
    }

    @Test
    public void testRemove() {
        solver.setNumber(0, 0, 5);
        solver.removeNumber(0, 0);

        assertEquals(0, solver.getNumber(0,0));

        assertThrows(IllegalArgumentException.class, () -> solver.removeNumber(-1, -1));
        assertThrows(IllegalArgumentException.class, () -> solver.removeNumber(10, 10));
    }

    @Test
    public void testSetNumbers() {
        int[][] numbers = new int[9][9];
        numbers[0][0] = -1;
        assertThrows(IllegalArgumentException.class, () -> solver.setNumbers(numbers));
        numbers[0][0] = 10;
        assertThrows(IllegalArgumentException.class, () -> solver.setNumbers(numbers));

        numbers[0][0] = 1;
        numbers[8][8] = 9;
        solver.setNumbers(numbers);
        assertEquals(1, solver.getNumber(0,0));
        assertEquals(9, solver.getNumber(8,8));
    }

    @Test
    public void testGetNumbers() {
        solver.setNumber(5,5, 5);
        int[][] numbers = solver.getNumbers();
        assertEquals(5, numbers[5][5]);
    }

    @Test
    public void testClear() {
        solver.setNumber(5,5,1);
        solver.setNumber(6,5,1);
        solver.setNumber(6,7,1);
        solver.setNumber(2,7,1);
        int[][] numbers = solver.getNumbers();

        assertTrue(checkNumbers(numbers));
        solver.clear();
        numbers = solver.getNumbers();
        assertFalse(checkNumbers(numbers));
    }

    // Loopa igenom och kolla ifall det finns något som inte är noll
    private boolean checkNumbers(int[][] numbers) {
        for (int[] row: numbers) {
            for (int col: row ) {
                if (col != 0) return true;
            }
        }
        return false;
    }

    @Test
    public void testSolve() {
        assertTrue(solver.solve());

        solver.clear();
        solver.setNumber(0,0, 9);
        solver.setNumber(0,1, 8);
        assertTrue(solver.solve());

        solver.clear();
        solver.setNumber(0,0, 9);
        solver.setNumber(2,2, 9);
        assertFalse(solver.solve());
    }
}

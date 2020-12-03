package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sudoku.Solver;

import static org.junit.jupiter.api.Assertions.*;

public class TestSolver {
    private static Solver solver;

    @BeforeAll
    public static void setup() {
        solver = new Solver();
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
}

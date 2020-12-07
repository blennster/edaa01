package sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuController {
    private JFrame frame;
    private final SudokuSolver solver;
    private SudokuBoard view;

    public SudokuController() {
        solver = new Solver();
        SwingUtilities.invokeLater(() -> createWindow("Sudoku", 300, 350));
    }

    private void createWindow(String name, int width, int height) {
        frame = new JFrame(name);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        view = new SudokuBoard();

        JPanel btnPane = new JPanel();
        btnPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            solver.clear();
            view.setNumbers(solver.getNumbers());
        });
        btnPane.add(clearBtn);
        JButton solveBtn = new JButton("Solve");
        solveBtn.addActionListener(event -> {
            try {
                solver.setNumbers(view.getNumbers());
                if (solver.solve()) {
                    view.setNumbers(solver.getNumbers());
                } else {
                    JOptionPane.showMessageDialog(frame, "No solution was found.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage());
            }
        });
        btnPane.add(solveBtn);

        pane.add(view);
        pane.add(btnPane, BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
    }
}

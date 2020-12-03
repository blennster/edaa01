package sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuController {
    private JFrame frame;
    private Solver solver;
    private SudokuView view;

    public SudokuController() {
        SwingUtilities.invokeLater(() -> createWindow("Sudoku", 300, 350));
        solver = new Solver();
    }

    private void createWindow(String name, int width, int height) {
        frame = new JFrame(name);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        view = new SudokuView(solver, frame);

        JPanel btnPane = new JPanel();
        btnPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            solver.clear();
            view.updateView(solver.getNumbers());
        });
        btnPane.add(clearBtn);
        JButton solveBtn = new JButton("Solve");
        solveBtn.addActionListener(e -> {
            solver.setNumbers(view.getNumbers());
            if (solver.preCheck() && solver.solve()) {
                view.updateView(solver.getNumbers());
            } else {
                JOptionPane.showMessageDialog(frame, "No solution was found.");
            }
        });
        btnPane.add(solveBtn);

        pane.add(view);
        pane.add(btnPane, BorderLayout.PAGE_END);
        solver.solve();
        view.updateView(solver.getNumbers());

        frame.pack();
        frame.setVisible(true);
    }
}

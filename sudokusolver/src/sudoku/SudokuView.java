package sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuView extends JPanel {
    final private JTextField[][] fields = new JTextField[9][9];
    private JFrame frame;

    public SudokuView(SudokuSolver solver, JFrame frame) {
        this.setLayout(new GridLayout(9, 9, 2, 2));
        this.frame = frame;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField field = new JTextField();
                int finalI = i;
                int finalJ = j;
                field.addActionListener(e -> {
                    // Fångar två möjliga exceptions, lite dåligt men men.
                    try {
                        int n = Integer.parseInt(field.getText());
                        solver.setNumber(finalI, finalJ, n);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid number");
                    }
                });
                fields[i][j] = field;
                this.add(field);

                if ((i / 3) % 2 == 0 && (j / 3) % 2 == 0) {
                    field.setBackground(Color.decode("#FF7F49"));
                }
                if (i / 3 == 1 && (j / 3) % 2 == 1){
                    field.setBackground(Color.decode("#FF7F49"));
                }
            }
        }
    }

    public void updateView(int[][] numbers) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (numbers[i][j] == 0) {
                    fields[i][j].setText("");
                } else {
                    fields[i][j].setText(String.valueOf(numbers[i][j]));
                }
            }
        }
    }

    public int[][] getNumbers() {
        int[][] numbers = new int[9][9];

        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String t = fields[i][j].getText();
                    t = t.equals("") ? "0" : t;
                    numbers[i][j] = Integer.parseInt(t);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "One or more inputs are invalid.");
        }
        return numbers;
    }
}

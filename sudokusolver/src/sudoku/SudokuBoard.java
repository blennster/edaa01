package sudoku;

import javax.swing.*;
import java.awt.*;

/**
 * SudokuBoard representerar ett 9x9 rutnät som ska vara sudokuspelplanen.
 */
public class SudokuBoard extends JPanel {
    final private JTextField[][] fields = new JTextField[9][9];

    public SudokuBoard() {
        this.setLayout(new GridLayout(9, 9, 2, 2));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(field.getFont().deriveFont(Font.BOLD, 13));
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

    /**
     * Sätter alla rutor i GUI:t till numbers. 0 tolkas som tom ruta
     * @param numbers De siffror som ska sättas.
     * @throws IllegalArgumentException om siffrorna inte är i [0..9]
     */
    public void setNumbers(int[][] numbers) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int number = numbers[i][j];
                if (number > 9 || number < 0) {
                    throw new IllegalArgumentException(
                            String.format("Rad %d, kolumn %d har ett ej tillåtet värde", i+1, j+1)
                    );
                }
                if (number == 0) {
                    fields[i][j].setText("");
                } else {
                    fields[i][j].setText(String.valueOf(number));
                }
            }
        }
    }

    /**
     * Läs alla siffror i GUI:t. Tom ruta tolkas som 0.
     * @return Alla siffror i en 9x9 matris.
     * @throws IllegalArgumentException ifall någon rutan inte är en siffra eller utanför [0..9]
     */
    public int[][] getNumbers() {
        int[][] numbers = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String t = fields[i][j].getText();
                t = t.equals("") ? "0" : t;
                int number = -1;
                try {
                    number = Integer.parseInt(t);
                    if (number > 9 || number < 0) throw new Exception();
                    numbers[i][j] = number;
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                        String.format("Rad %d, kolumn %d har ett ej tillåtet värde", i+1, j+1)
                    );
                }
            }
        }
        return numbers;
    }
}

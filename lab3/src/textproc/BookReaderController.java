package textproc;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BookReaderController {
    public BookReaderController(GeneralWordCounter counter) {
        SwingUtilities.invokeLater(() -> createWindow(counter, "BookReader", 100, 300));
    }

    private void createWindow(GeneralWordCounter counter, String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        //pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JList<Map.Entry<String, Integer>> list = new JList<>();
        SortedListModel<Map.Entry<String, Integer>> listModel = new SortedListModel<>(counter.getWordList());
        list.setModel(listModel);
        scrollPane.getViewport().setView(list);

        JPanel sorting = new JPanel();
        sorting.setLayout(new BoxLayout(sorting, BoxLayout.X_AXIS));
        /* Gammal soterting
        JButton alphabetic = new JButton("Alphabetic");
        alphabetic.addActionListener((e) -> listModel.sort((e1, e2) -> e1.getKey().compareTo(e2.getKey())));
        JButton frequency = new JButton("Frequency");
        frequency.addActionListener((e) -> listModel.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())));
        sorting.add(alphabetic);
        sorting.add(frequency);
         */
        ButtonGroup group = new ButtonGroup();
        JRadioButton alphabetic = new JRadioButton("Alphabetic");
        alphabetic.setSelected(true);
        JRadioButton frequency = new JRadioButton("Frequency");
        JButton sort = new JButton("Sort");
        sort.addActionListener((e) -> {
            listModel.sort((e1, e2) -> {
                if (alphabetic.isSelected())
                    return e1.getKey().compareTo(e2.getKey());
                return e2.getValue().compareTo(e1.getValue());
            });
        });

        group.add(alphabetic);
        group.add(frequency);

        sorting.add(alphabetic);
        sorting.add(frequency);
        sorting.add(sort);

        JPanel search = new JPanel();
        search.setLayout(new BoxLayout(search, BoxLayout.X_AXIS));
        JButton searchButton = new JButton("Search");
        JTextField searchField = new JTextField();
        searchField.addActionListener((e) -> searchButton.doClick());
        searchButton.addActionListener((e) -> {
            String searchString = searchField.getText();
            searchString = searchString.trim();
            searchString = searchString.toUpperCase();
            int idx = -1;
            for (int i = 0; i < listModel.getSize(); i++) {
                if (listModel.getElementAt(i).getKey().toUpperCase().equals(searchString))
                    idx = i;
            }
            if (idx == -1)
                JOptionPane.showMessageDialog(frame, "Detta ord fanns inte i boken");
            else
                list.setSelectedIndex(idx);
            //list.ensureIndexIsVisible(idx);
        });
        search.add(searchField);
        search.add(searchButton);

        bottom.add(sorting);
        bottom.add(search);

        pane.add(scrollPane);
        pane.add(bottom, BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
    }
}

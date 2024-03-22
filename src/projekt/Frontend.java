package projekt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
// import dla tabeli
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Frontend {
    private static final Logger logger = LogManager.getLogger(Frontend.class);

    private String[][] importTab;
    private String stronnica;

    public Frontend(String[][] importTab, String pergamin) {
        try {
            stronnica = pergamin;
            this.importTab = importTab;
            int size = importTab.length;

            logger.info("Tworzenie interfejsu użytkownika dla strony: {}", stronnica);

            SwingUtilities.invokeLater(() -> {
                JFrame frametab = new JFrame(stronnica);
                frametab.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                String[] columnNames = {"Nazwa", "Kupno", "Sprzedaż"};
                DefaultTableModel tableModel = new DefaultTableModel(importTab, columnNames);
                JTable table = new JTable(tableModel);

                JScrollPane scrollPane = new JScrollPane(table);
                frametab.add(scrollPane);
                frametab.pack();
                frametab.setLocationRelativeTo(null);
                frametab.setVisible(true);
            });

            logger.info("Tworzenie interfejsu użytkownika zakończone sukcesem.");

            // Test konca programu
            logger.debug("Ostatnia wartość tabeli: {}", importTab[size - 1][2]);

        } catch (Exception e) {
            logger.error("Niespodziewany błąd podczas tworzenia interfejsu użytkownika dla strony: {}", stronnica, e);
        }
    }
}

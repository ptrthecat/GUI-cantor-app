package projekt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CurFront {
    private static final Logger logger = LogManager.getLogger(CurFront.class);

    private String[][] importTab;
    private String stronnica;

    public CurFront(String[][] importTab, String pergamin) {
        stronnica = pergamin;
        this.importTab = importTab; // tablica z naszymi danymi
        int size = importTab.length;

        String sells = importTab[0][2];
        for (int i = 0; i < importTab.length; i++) {
            if (importTab[i][2].equals("brak")) {
                continue;
            }
            sells = importTab[i][2]; // szukamy pierwszej wartosci z sells
            break;
        }

        String buys = importTab[0][1];
        for (int i = 0; i < importTab.length; i++) {
            if (importTab[i][1].equals("brak")) {
                continue;
            }
            buys = importTab[i][1]; // szukamy min z sells
            break;
        }

        // szukanie najelpszej ceny
        for (int i = 0; i < importTab.length; i++) {
            if (importTab[i][1].equals("brak")) {
                continue;
            }
            if (Double.parseDouble(importTab[i][1]) > Double.parseDouble(buys)) { // petla szukajaca max z buys
                buys = importTab[i][1];
            }
        }

        for (int i = 0; i < importTab.length; i++) {
            if (importTab[i][2].equals("brak")) {
                continue;
            }
            if (Double.parseDouble(importTab[i][2]) < Double.parseDouble(sells)) { // petla szukajaca min z sells
                sells = importTab[i][2];
            }
        }

        final String sell = sells; // bez finala program sie nie kompilowal
        final String buy = buys;

        SwingUtilities.invokeLater(() -> { // funkcja od UI
            JFrame frametab = new JFrame(stronnica);
            frametab.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            String[] columnNames = {"Kantor", "Kupno", "Sprzedaż"};
            DefaultTableModel tableModel = new DefaultTableModel(importTab, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Przekazanie buys i sells do konstruktora CustomCellRenderer
            CustomCellRenderer cellRenderer = new CustomCellRenderer(buy, sell); // customowe renderowanie pól, funkcja na dole
            JTable table = new JTable(tableModel);
            table.setDefaultRenderer(Object.class, cellRenderer);

            JScrollPane scrollPane = new JScrollPane(table); // scroll
            frametab.add(scrollPane);
            frametab.pack();
            frametab.setLocationRelativeTo(null);
            frametab.setVisible(true);

            logger.info("Wyświetlono tabelę na stronie: {}", stronnica);
        });
    }

    // Niestandardowy renderer komórek z argumentami buys i sells
    static class CustomCellRenderer extends DefaultTableCellRenderer {
        private final String buy;
        private final String sell;

        public CustomCellRenderer(String buy, String sell) {
            this.buy = buy;
            this.sell = sell;
        }

        // poniżej funkcja od customowego renderowania tabeli
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            Color nullColor = new Color(200, 200, 200); // kolorki
            Color defaultColor = new Color(252, 243, 136);

            // Ustawienia dla konkretnych komórek w kolumnie 1 i 2
            if (column == 1 && value != null && value.toString().equals(buy)) {
                ((JComponent) cellComponent).setBorder(BorderFactory.createLineBorder(Color.RED));
                cellComponent.setBackground(Color.WHITE);
            } else if (column == 2 && value != null && value.toString().equals(sell)) {
                ((JComponent) cellComponent).setBorder(BorderFactory.createLineBorder(Color.BLUE));
                cellComponent.setBackground(Color.WHITE);
            } else {
                // Domyślne ustawienia dla innych komórek
                if (value != null && value.toString().equals("brak")) {
                    cellComponent.setBackground(nullColor);
                    ((JComponent) cellComponent).setBorder(null);
                } else {
                    cellComponent.setBackground(Color.WHITE);
                }
            }

            return cellComponent;
        }
    }
}

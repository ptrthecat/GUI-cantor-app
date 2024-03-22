package projekt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class CompUI {
    private static final Logger logger = LogManager.getLogger(CompUI.class);

    String[][] linki;

    public CompUI(String[][] linki) {
        this.linki = linki;

        JFrame comp_frame = new JFrame("Porównywarka cen"); // utworzenie ramki (calego okna de facto)

        Font font_hello = new Font("Arial", Font.BOLD, 16); // czcionka naglowka

        // górny panel
        // wszystkie labele "hello" są dla gornego napisu zeby to ladnie sie ukladalo

        JPanel entry_panel = new JPanel();
        comp_frame.getContentPane().add(entry_panel, BorderLayout.NORTH);
        entry_panel.setLayout(new GridLayout(5, 1));
        entry_panel.setFont(font_hello);

        JLabel hello_panel_ = new JLabel(""); // panel dolny z informacją
        entry_panel.add(hello_panel_);

        JLabel hello_panel_0 = new JLabel(""); // panel dolny z informacją
        entry_panel.add(hello_panel_0);

        JLabel hello_panel_1 = new JLabel("Wprowadź 3-literowy skrót waluty:"); // panel dolny z informacją
        entry_panel.add(hello_panel_1);
        hello_panel_1.setHorizontalAlignment(SwingConstants.CENTER);
        hello_panel_1.setFont(font_hello);

        JLabel hello_panel_2 = new JLabel(""); // panel dolny z informacją
        entry_panel.add(hello_panel_2);

        JLabel hello_panel_3 = new JLabel(""); // panel dolny z informacją
        entry_panel.add(hello_panel_3);

        // wszystkie labele "hello" są dla gornego napisu zeby to ladnie sie ukladalo

        JPanel text_panel = new JPanel(); // panel od naszeo tekstu
        text_panel.setLayout(new GridLayout(1, 3));// 3 pola, tylko srodkowe jest do wpisywania,
        text_panel.setFont(font_hello); // aby wpisywana wartosc byla na srodku
        entry_panel.add(text_panel);

        text_panel.add(hello_panel_0);

        JTextArea textArea = new JTextArea(1, 3);
        text_panel.add(textArea);
        textArea.setFont(font_hello);
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Ustawienie wyrównania tekstu na środku
        text_panel.add(hello_panel_2);

        // ponizej panel od przycisku "zapisz"

        JButton saveButton = new JButton("Zapisz");
        comp_frame.getContentPane().add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textArea.getText().length() == 3) {
                    String curr = textArea.getText(); // Zapisanie treści wpisywanej do zmiennej
                    logger.info("Zapisano: {}", curr);
                    curr = curr.toUpperCase();
                    textArea.setText("");
                    Compare compare = new Compare(curr, linki);
                } else {
                    // komunikat errorowy
                    textArea.setText("");
                    ErrorThree blad = new ErrorThree();
                    logger.error("Błąd: Wprowadź dokładnie 3-literowy skrót waluty.");
                }
            }
        });

        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getModifiers() == 0) {
                    if (textArea.getText().length() == 3) {
                        String curr = textArea.getText();
                        logger.info("Zapisano: {}", curr);
                        curr = curr.toUpperCase();
                        e.consume(); // Uniemożliwienie przekazania zdarzenia dalej (np. na kolejny wiersz)
                        textArea.setText("");
                        Compare compare = new Compare(curr, linki);
                    } else {
                        e.consume();
                        textArea.setText("");
                        ErrorThree blad = new ErrorThree();
                        logger.error("Błąd: Wprowadź dokładnie 3-literowy skrót waluty.");
                    }
                }
            }
        });

        // Zablokowanie zmiany rozmiaru okna
        comp_frame.setResizable(false);
        comp_frame.setSize(400, 180);
        // Umieszczenie okna na środku ekranu
        comp_frame.setLocationRelativeTo(null);
        comp_frame.setVisible(true);
        comp_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

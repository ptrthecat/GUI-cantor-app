package projekt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private static final Logger logger = LogManager.getLogger(Menu.class);

    public Menu(String[][] linki) {
        try {
            String[][] urls = linki;

            JFrame frame = new JFrame("Projekt PO"); // utworzenie ramki

            Font font_kantor = new Font("Arial", Font.BOLD, 20);
            Font font_hello = new Font("Arial", Font.BOLD, 16); // czcionki
            Font font_hello2 = new Font("Arial", Font.BOLD, 14);
            Font font_autors = new Font("Arial", Font.PLAIN, 11);
            Font font_compare = new Font("Arial", Font.PLAIN, 14);

            JPanel entry_panel = new JPanel();
            frame.getContentPane().add(entry_panel, BorderLayout.NORTH);
            entry_panel.setLayout(new GridLayout(3, 1));
            entry_panel.setFont(font_kantor);

            JLabel hello_panel_0 = new JLabel(" "); // panel dolny z informacją 
            entry_panel.add(hello_panel_0);
            hello_panel_0.setHorizontalAlignment(SwingConstants.RIGHT);
            hello_panel_0.setFont(font_autors);

            JLabel hello_panel_1 = new JLabel("Witamy w naszej aplikacji porównującej kantory w Krakowie"); // panel dolny z informacją 
            entry_panel.add(hello_panel_1);
            hello_panel_1.setHorizontalAlignment(SwingConstants.CENTER);
            hello_panel_1.setFont(font_hello);

            JLabel hello_panel_2 = new JLabel(""); // panel dolny z informacją 
            entry_panel.add(hello_panel_2);
            hello_panel_2.setHorizontalAlignment(SwingConstants.CENTER);
            hello_panel_2.setFont(font_hello);

            JPanel main_panel = new JPanel(); // panel z lista kantorow
            main_panel.setLayout(new GridLayout(urls.length, 1));
            main_panel.setFont(font_kantor);

            frame.getContentPane().add(main_panel);

            // tekst na przyciskach
            String[] button_text = new String[urls.length];

            for (int i = 0; i < urls.length; i++) {
                button_text[i] = urls[i][1]; // przydzielenie tekstu na przyciskach
            }

            int i;
            for (i = 0; i < urls.length; i++) {

                JButton jb = new JButton(button_text[i]);
                main_panel.add(jb); // utworzenie paneli z kantorami
                jb.setFont(font_kantor);
                final int index = i; // naprawia problem z odpalaniem tablicy kursow

                jb.addActionListener(new ActionListener() { // sprawienie zeby reagowaly na klikniecie
                    public void actionPerformed(ActionEvent e) {
                        ApiKantor api = new ApiKantor(urls[index][0], urls[index][1]); // pobranie walut, przejscie do APIKANTOR
                    }
                });
            }

            // porownywarka+panel dolny
            // potrojny panel:
            JPanel compare_panel = new JPanel();
            compare_panel.setLayout(new GridLayout(3, 1));
            compare_panel.setFont(font_kantor);

            // odseparowanie:
            JLabel end_panel_1 = new JLabel(" "); // panel dolny z informacją 
            compare_panel.add(end_panel_1);
            end_panel_1.setHorizontalAlignment(SwingConstants.RIGHT);
            end_panel_1.setFont(font_autors);

            // Dodanie przycisku porównywarki na panel
            JButton compare_button = new JButton("Porównywarka cen");
            compare_panel.add(compare_button);
            compare_button.setFont(font_compare);

            frame.getContentPane().add(compare_panel, BorderLayout.SOUTH);

            // panel z autorami
            JLabel end_panel_2 = new JLabel("Autorzy: Karol Słowiak & Piotr Kondziołka "); // panel dolny z informacją 
            compare_panel.add(end_panel_2);
            end_panel_2.setHorizontalAlignment(SwingConstants.RIGHT);
            end_panel_2.setFont(font_autors);

            compare_button.addActionListener(new ActionListener() { // sprawienie zeby reagowaly na klikniecie
                public void actionPerformed(ActionEvent e) {

                    CompUI porownanie = new CompUI(urls); // porownarka odpalona

                }
            });

            frame.pack();

            // Zablokowanie zmiany rozmiaru okna
            frame.setResizable(false);
            frame.setSize(600, 600);
            // Umieszczenie okna na środku ekranu
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            logger.info("Aplikacja uruchomiona pomyślnie.");

        } catch (Exception e) {
            logger.error("Niespodziewany błąd podczas uruchamiania aplikacji.", e);
        }
    }
}

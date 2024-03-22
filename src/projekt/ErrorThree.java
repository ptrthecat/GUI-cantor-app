package projekt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ErrorThree {
    private static final Logger logger = LogManager.getLogger(ErrorThree.class);

    public ErrorThree() {
        try {
            logger.error("Błąd: Skróty walut powinny być 3-znakowe (np: PLN, USD, EUR)");

            JFrame error_frame = new JFrame("ERROR");
            Font error_font = new Font("Arial", Font.BOLD, 20);

            JLabel error_label = new JLabel("Skróty walut są 3-znakowe (np: PLN, USD, EUR)");
            error_frame.getContentPane().add(error_label);
            error_label.setForeground(Color.WHITE);
            error_label.setHorizontalAlignment(SwingConstants.CENTER);
            error_label.setFont(error_font);

            // Ustawienie koloru tła okna na czerwony
            error_frame.getContentPane().setBackground(Color.RED);

            // Zablokowanie zmiany rozmiaru okna
            error_frame.setResizable(false);
            error_frame.setSize(620, 150);
            // Umieszczenie okna na środku ekranu
            error_frame.setLocationRelativeTo(null);
            error_frame.setVisible(true);
            error_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (Exception e) {
            logger.error("Niespodziewany błąd podczas wyświetlania komunikatu o błędzie", e);
        }
    }
}

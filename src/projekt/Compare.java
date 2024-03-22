package projekt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Compare {
    private static final Logger logger = LogManager.getLogger(Compare.class);

    String curr = "";
    String[][] linki;

    public Compare(String curr, String[][] linki) {
        this.curr = curr;
        this.linki = linki;

        try {
            logger.info("Rozpoczęcie porównywania cen dla waluty: {}", curr);

            String[][] wyniki = new String[linki.length][3]; // tablica z cenami wybranej waluty

            for (int i = 0; i < linki.length; i++) {
                ApiCur api = new ApiCur(); // Tworzenie obiektu klasy ApiCur
                String[] wynik = api.ApiCurrr(linki[i][0], linki[i][1], curr); // Wywołanie funkcji

                wyniki[i][0] = wynik[0];
                wyniki[i][1] = wynik[1];
                wyniki[i][2] = wynik[2];
            }

            logger.info("Wyniki porównania cen dla waluty {}:", curr);

            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < wyniki.length; i++) {
                    logger.info("{}", wyniki[i][j]);
                }
            }

            CurFront cur_tab = new CurFront(wyniki, curr);

            logger.info("Porównywanie cen zakończone sukcesem.");

        } catch (Exception e) {
            logger.fatal("Niespodziewany błąd podczas porównywania cen dla waluty: {}", curr, e);
        }
    }
}


package projekt;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiCur {
    private static final Logger logger = LogManager.getLogger(ApiCur.class);

    private String linka;
    private String stronka;
    private String cur;

    public String[] ApiCurrr(String linka, String stronka, String cur) {
        try {
            logger.info("Rozpoczęcie pobierania danych z URL: {}", linka);

            String curr = cur;
            String url = linka; // nasz url
            String strona = stronka;

            Document doc = Jsoup.connect(url).get();
            Elements currencies = doc.select(".nowrap");

            String[][] main_tab = new String[currencies.size()][3]; // tablica z wartosciami
            // wartosci pomocnicze
            int i = 0, j = 0, n = 0, m = 0;

            // for parsujacy dane
            for (Element currency : currencies) {
                if (currency.select(".me-2").text().length() > 1) { // nazwa waluty

                    String title = currency.select(".me-2").text();
                    main_tab[i][0] = currency.select(".me-2").text();
                    i++;

                }
                if (currency.select(".h5").text().length() > 0) { // koszt waluty

                    String cost = currency.select(".h5").text();

                    if (j % 2 == 1) { // sprzedaz
                        main_tab[n][2] = currency.select(".h5").text();
                        n++;
                    } else if (j % 2 == 0) { // kupno
                        main_tab[n][1] = currency.select(".h5").text();
                        m++;
                    }
                    j++;
                }

            }

            // sprawdzanie liczby walut
            int num_of_cur = 0;
            i = 0;
            j = 0;
            for (i = 0; i < currencies.size(); i++) {
                if (main_tab[i][1] != null) {
                    num_of_cur = i + 1;
                }
            }

            // tablica pomocnicza bez null
            i = 0;
            j = 0;
            String[][] fin_tab = new String[num_of_cur][3];

            for (i = 0; i < num_of_cur; i++) {
                for (j = 0; j < 3; j++) {
                    fin_tab[i][j] = main_tab[i][j];
                }
            }

            // Usuwanie dwóch pierwszych znaków z każdego stringa w pierwszej kolumnie
            for (i = 0; i < fin_tab.length; i++) {
                if (fin_tab[i].length > 0) {
                    // Sprawdzamy, czy istnieje co najmniej jeden element w danym wierszu
                    String aktualnyString = fin_tab[i][0];

                    if (aktualnyString.length() >= 2) {
                        // Sprawdzamy, czy string ma co najmniej dwa znaki
                        fin_tab[i][0] = aktualnyString.substring(2);
                    } else {
                        // Jeśli string ma mniej niż dwa znaki, ustawiamy go na pusty string
                        fin_tab[i][0] = "";
                        logger.warn("Pusty string w pierwszej kolumnie dla waluty na pozycji: {}", i);
                    }
                }
            }

            // Sortowanie tablicy po wierszach alfabetycznie
            Arrays.sort(fin_tab, Comparator.comparing(a -> a[0]));

            String[] curr_tab = new String[3]; // ustawienie tablicy z nazwa naszej waluty oraz cenami
            curr_tab[0] = stronka; // wartosci curr_tab beda wyswietlane przez CurFront.java
            curr_tab[1] = "brak";
            curr_tab[2] = "brak";

            for (i = 0; i < fin_tab.length; i++) {
                logger.info("pobrano dane waluty {}: Kupno: {}, Sprzedaż: {}", fin_tab[i][0], fin_tab[i][1],
                        fin_tab[i][2]);

                if (fin_tab[i][0].equals(curr)) {
                    curr_tab[1] = fin_tab[i][1];
                    curr_tab[2] = fin_tab[i][2];
                }
            }

            logger.info("Pobieranie danych zakończone sukcesem.");

            return curr_tab; // wyswietlenie tablicy z cenami danej waluty

        } catch (IOException e) {
            logger.error("Błąd podczas pobierania danych z URL: {}", linka, e);
            return null;
        }
    }
}

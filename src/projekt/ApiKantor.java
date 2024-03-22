package projekt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Arrays;
import java.util.Comparator;


public class ApiKantor {
    private static final Logger logger = LogManager.getLogger(ApiKantor.class);

    private String linka;
    private String stronka;

    public ApiKantor(String linka, String stronka) {
        this.linka = linka;
        this.stronka = stronka;

        try {
            logger.info("Rozpoczęcie pobierania danych z URL: {}", linka);

            Document doc = Jsoup.connect(linka).timeout(5000).get();
            Elements currencies = doc.select(".nowrap");
            
            if (currencies.isEmpty()) {
                logger.fatal("Brak danych do przetworzenia na stronie: {}", linka);
                return;
            }
            
            //tablica z wartosciami
            String[][] main_tab = new String[currencies.size()][3];
            
            //wartosci pomocnicze
            int i = 0, j = 0, n = 0, m = 0;
            
            //for parsujacy dane
            for (Element currency : currencies) {
                if (currency.select(".me-2").text().length() > 1) { //nazwa waluty
                    String title = currency.select(".me-2").text();
                    main_tab[i][0] = currency.select(".me-2").text();
                    i++;
                }
                if (currency.select(".h5").text().length() > 0) { //koszt waluty
                    String cost = currency.select(".h5").text();
                    if (j % 2 == 1) { //sprzedaz
                        main_tab[n][2] = currency.select(".h5").text();
                        n++;
                    } else if (j % 2 == 0) { //kupno
                        main_tab[n][1] = currency.select(".h5").text();
                        m++;
                    }
                    j++;
                }
            }
            
            if (i == 0 || n == 0 || m == 0 || j == 0) {
                logger.fatal("Nieprawidłowe dane na stronie: {}", linka);
                return;
            }
            
            //sprawdzanie liczby walut
            int num_of_cur = 0;
            i = 0;
            j = 0;

            for (i = 0; i < currencies.size(); i++) {
                if (main_tab[i][1] != null) {
                    num_of_cur = i + 1;
                }
            }
            
            if (num_of_cur == 0) {
                logger.fatal("Brak danych do przetworzenia na stronie: {}", linka);
                return;
            }
            
            //tablica pomocnicza bez null
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
            
            //print tablicy pomocniczej bez null
            i = 0;
            j = 0;
            for (i = 0; i < 3; i++) {
                for (j = 0; j < num_of_cur; j++) {
                    logger.info("pobrano dane waluty " + fin_tab[j][i] + "\t");
                }
            }
            
            // podpiecie frontendu
            Frontend frontend = new Frontend(fin_tab, stronka);

            logger.info("Pobieranie danych zakończone sukcesem.");

        } catch (SocketTimeoutException e) {
            logger.error("Timeout podczas pobierania danych z URL: {}", linka);
        } catch (HttpStatusException e) {
            logger.error("Błąd HTTP podczas pobierania danych z URL: {} - Kod statusu: {}", linka, e.getStatusCode());
        } catch (UnknownHostException e) {
            logger.error("Nieznany host podczas pobierania danych z URL: {}", linka);
        } catch (SocketException e) {
            logger.error("Błąd Socket podczas pobierania danych z URL: {}", linka);
        } catch (IOException e) {
            logger.error("Błąd podczas pobierania danych z URL: {}", linka, e);
        } catch (Exception e) {
            logger.error("Niespodziewany błąd podczas przetwarzania danych z URL: {}", linka, e);
        }
    }
}

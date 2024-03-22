package projekt;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Projekt {
	public static void main(String[] args) {
		
		
		//linki
		String[][] urls = {{"https://kantor.live/kantory/krakow/564840-kantor-kardinal", "Kantor Kardinal"}, //0
						{"https://kantor.live/kantory/krakow/876368-kantor-pr", "Kantor P&R"}, //1
						{"https://kantor.live/kantory/krakow/652480-kantor-house", "Kantor House"},//2
						{"https://kantor.live/kantory/krakow/707917-kantor-centrum", "Kantor Centrum"},//3
						{"https://kantor.live/kantory/krakow/643512-kantor-agatka", "Kantor Agatka"},
						{"https://kantor.live/kantory/krakow/466986-kantor-baksy", "Kantor Baksy"}};//4
						

		Menu menu = new Menu(urls);
      	
    }
}
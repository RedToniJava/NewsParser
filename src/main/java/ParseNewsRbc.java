import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;


public class ParseNewsRbc extends ParseNewsNsn implements Runnable {

    private final ArrayList<String> lastNews = new ArrayList<>();
    private final ArrayList<String> lastNewsUrl = new ArrayList<>();
    private final ArrayList<String> textsNews = new ArrayList<>();
    private static Document doc;
    private static Document docTextNews;
    private boolean startParse = true;


    //parse HTML by RBC
    @Override
    public void run() {

        while (startParse) {
            try {
                String urlNsn = "https://nn.rbc.ru/";
                doc = Jsoup.connect(urlNsn).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements elements = doc.select("div.main__feed");

            elements.forEach(e -> {
                lastNews.add(e.text());
                lastNewsUrl.add(newsURL(e.toString()));
                textsNews.add(newsText(newsURL(e.toString())));

            });

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastNews.clear();
            textsNews.clear();
            lastNewsUrl.clear();

        }

    }

    //get URL page news
    private String newsURL(String code) {
        String startParse = " href=\"";
        int start = code.indexOf(startParse) + startParse.length();
        int end = code.indexOf('"', start);
        String url = code.substring(start, end);
        return url;
    }

    //parse Text
    private String newsText(String url) {
        {
            try {
                docTextNews = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Elements elements = docTextNews.select("div.article__text");
        return elements.get(0).text();

    }

    public ArrayList<String> getLastNews() {
        return lastNews;
    }

    public ArrayList<String> getTextsNews() {
        return textsNews;
    }

    public ArrayList<String> getLastNewsUrl() {
        return lastNewsUrl;
    }
    public boolean getStartParse(){
        return startParse;
    }

    public void stopParse() {
        startParse = false;
    }


}

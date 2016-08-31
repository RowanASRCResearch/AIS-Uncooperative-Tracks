package gathering; /**
 * Created by Bob S on 6/29/2016.
 */
import java.net.*;
import java.io.*;
import java.awt.Desktop;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlConn {

    private static URL base;
    private static URL url;
    private static final int TIMEOUT_LIM = 0;   // integer value for limit on timeout of jsoup.connect. NOTE 0 is infinity

    public static void main(String[] args) {

        try {
            base = new URL("http://tidesandcurrents.noaa.gov/");
            url = new URL(base, "stations.html");

            // Open url connection to static url, Read in html file for local storage as doc.
            Document doc = Jsoup.connect(url.toString()).timeout(TIMEOUT_LIM).get();

            //Open local resource for browser viewing
            //openHtmlSource(htmlSource);

            //Extract each href with ID, convert each id to viable URL, store URLS in csv.
            writeToFile(obtainUrls(doc), new File(System.getProperty("user.dir") + "\\resources\\station_urls.csv"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T extends Object> void writeToFile(ArrayList<T> ar, File f) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(f));
            StringBuilder sb = new StringBuilder();
            for (Object element : ar) {
                sb.append(element.toString());
                sb.append(",");
            }
            br.write(sb.toString());
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        print("Successfully wrote to, " + f.getPath());
    }

    private static void openHtmlURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        print("Successfully opening, " + url);
    }

    private static ArrayList<URL> obtainUrls(Document doc) {
        ArrayList<URL> ret = new ArrayList<URL>();
        try {
            Elements hrefs = doc.select("span:matchesOwn(present)");
            for(Element url: hrefs) {
                String href = url.firstElementSibling().attr("href");
                ret.add(new URL(base, href));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        print("Successfully extracted ID's ");
        return ret;
    }

    public static void print(Object o) {
        System.out.println(o.toString());
    }
}

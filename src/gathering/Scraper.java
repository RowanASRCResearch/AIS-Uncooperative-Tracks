package gathering;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Research on 6/29/2016.
 */
//TODO: make tide info into JSON
public class Scraper {
    File file;
    String fileName;

    public Scraper(String url, String fileName) throws IOException, InterruptedException {
        download(url, fileName);
        file = new File(fileName);
    }

    /**
     * main.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Scraper scraper = new Scraper("http://tidesandcurrents.noaa.gov/stationhome.html?id=9455090", "tide.html");
        System.out.println(scraper.getTagContent("div class=\"row-fluid\"", "div"));
        //scraper.download("http://www.ndbc.noaa.gov/kml/marineobs_as_kml.php?sort=owner","testfile.kml");   scraper.download();

    }

    /**
     * This method takes in an address and a filename-
     * then downloads the page and saves that page as a file with the name specified.
     *
     * @param address the address
     * @param fName   the f name
     * @return the boolean
     * @throws IOException the io exception
     */
    boolean download(String address, String fName) throws IOException, InterruptedException {

        URL website = new URL(address);
        InputStream stream = website.openStream();
        ReadableByteChannel rbc = Channels.newChannel(stream);
        FileOutputStream fos = new FileOutputStream(fName);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        return true;
    }

    String getTagContent(String tag, String endTag) throws FileNotFoundException {
        String str = "";
        boolean check = false;
        String lineFromFile = "";
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            lineFromFile = scanner.nextLine(); //puts line in String
            if (lineFromFile.contains("<" + tag + ">")) { //check if line contains tag
                str += "\n" + lineFromFile;
                check = true;
                while (scanner.hasNextLine()) {
                    lineFromFile = scanner.nextLine();
                    if (lineFromFile.contains("</"+endTag+">")) {
                        str += "\n" + lineFromFile;
                        break;
                    } else {

                        str += "\n" + lineFromFile;
                    }
                }



            }


        }
        return str;
    }


}

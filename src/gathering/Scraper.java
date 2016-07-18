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

/**
 * Created by Research on 6/29/2016.
 */
//TODO: make tide info into JSON
public class Scraper {
    /**
     * main.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        Scraper scraper = new Scraper("http://tidesandcurrents.noaa.gov/stationhome.html?id=9455090", "tide.html");
        System.out.println(scraper.getTagContent("div", "div"));
        //scraper.download("http://www.ndbc.noaa.gov/kml/marineobs_as_kml.php?sort=owner","testfile.kml");   scraper.download();

    }

    File file ;
    String fileName;
    public Scraper(String url, String fileName) throws IOException {
        download(url, fileName);
        file = new File(fileName);
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
    boolean download(String address, String fName) throws IOException {

        URL website = new URL(address);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
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
            if (lineFromFile.contains("<"+tag)) { //check if line contains tag
                check = true;
                while (scanner.hasNextLine()) {
                    lineFromFile = scanner.nextLine();
                    if (lineFromFile.contains("</"+endTag+">")) {
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

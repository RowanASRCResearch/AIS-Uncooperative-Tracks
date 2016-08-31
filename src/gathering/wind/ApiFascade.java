package gathering.wind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import prediction.Point;

/**
 * Created by Eliakah Kakou
 * This Class is a fascade that allows user to get the result from an NOA API call
 * by specifying the Station ID number and the to & from date
 * it returns a station object containing the 's' and 'd' values form the specified station for the specified date
 */
public class ApiFascade {

    String station = ""; //station id
    String from = ""; //start date
    String to = ""; //end date
    String datum = "MSL"; //default datum value
    String baseUrl = "http://tidesandcurrents.noaa.gov/api/datagetter?"; //base url
    String endUrl = "&units=metric&time_zone=gmt&format=json"; //other half of url


    /**
     * @param station
     * @param to
     * @param from    Constructor
     */
    public ApiFascade(String station, String to, String from) {

        this.station = station;
        this.to = to;
        this.from = from;

    }

    public static void main(String args[]) throws IOException {

        ApiFascade fascade = new ApiFascade("8454000", "20130808%2015:00", "20130808%2015:00");
        String text = fascade.urlBuilder();
        System.out.print(text);
        String scan = fascade.scanPage(text);
        System.out.println(scan);
        System.out.print(Arrays.asList(fascade.formatData(scan)));
    }

    /**
     * This builds a url using the in inputed information and returns the completed url as a string
     * @return url
     */
    String urlBuilder() {
        String url = "";

        url = baseUrl + "begin_date=" + from + "&end_date=" + to + "&station=" + station + "&product=wind&datum=" + datum + endUrl;

        return url;
    }


    /**
     * This method scrapes the page where the result is displayed and returns it as a string
     * @param pageUrl
     * @return text
     * @throws IOException
     */
    String scanPage(String pageUrl) throws IOException {
        URL url = new URL(pageUrl); //creates url object
        String text = "";
        URLConnection con = url.openConnection(); //runs the url
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) { //stream scans page
            text += "\n" + line; //concatenates page into one string
        }

        return text; //returns result as string
    }


    /**
     * This method formats the information gathered from page and outputs an object array
     * containing information of differnt types
     * @param str
     * @return data
     */
    public ArrayList<Object> formatData(String str) {

        ArrayList<Object> data = new ArrayList<>();
        int id = 0;
        Point station = new Point(0, 0);
        float s = 0;
        float d = 0;

        String[] list = str.split(",");
        for (int i = 0; i < list.length; i++) {

            if (list[i].contains("\"lat\":")) {
                list[i] = rmString(list[i], "\"");
                list[i] = rmString(list[i], "lat:");
                list[i] = rmString(list[i], "}");
                station.setLatitude(Float.parseFloat(list[i]));
            }

            if (list[i].contains("\"lon\":")) {
                list[i] = rmString(list[i], "\"}");
                list[i] = rmString(list[i], "\"");
                list[i] = rmString(list[i], "lon:");
                station.setLongitude(Float.parseFloat(list[i]));

            }

            if (list[i].contains("\"s\":")) {
                list[i] = rmString(list[i], "\"");
                list[i] = rmString(list[i], "s:");
                list[i] = rmString(list[i], "}");
                s = Float.parseFloat(list[i]);
            }

            if (list[i].contains("\"d\":")) {
                list[i] = rmString(list[i], "\"");
                list[i] = rmString(list[i], "d:");
                list[i] = rmString(list[i], "}");
                d = Float.parseFloat(list[i]);
            }

        }

        data.add(station);
        data.add(s);
        data.add(d);


        return data;




    }

    /**
     * This method takes in a string and removes the substring specified
     *
     * @param ogString
     * @param subString
     * @return
     */
    private String rmString(String ogString, String subString) {
        ogString = ogString.replace(subString, "");
        return ogString;
    }

}
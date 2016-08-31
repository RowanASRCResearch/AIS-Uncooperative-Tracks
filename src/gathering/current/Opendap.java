package gathering.current; /**
 * Created by Bob S on 6/30/2016.
 */

import java.net.*;
import java.io.*;
import java.awt.Desktop;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Opendap {

    private static final String baseUrl = "http://podaac-opendap.jpl.nasa.gov/opendap/allData/oscar/preview/L4/oscar_third_deg/";
    private static final String beginningDate = "5.10.1992";
    private static int day;
    private static int mo;
    private static int year;
    private static double lat;
    private static double lon;
    private static double U;
    private static double V;

    public Opendap(String[] args) {
        try {
            //Take in args
            parseArgs(args);

            //Construct URLS for path to directory and file
            URL base = new URL(baseUrl);
            URL url = new URL(base, "oscar_vel" + year + ".nc.gz.html");

            //Save url, decapitating the '.html' extension
            String urlWithoutDotHtml = url.toString().substring(0, url.toString().length()-4);
            String[] intervals = findTimeIntervals(urlWithoutDotHtml);

            //Collect value for time prediction.limit, lat prediction.limit and lon prediction.limit
            String timeVal = findTimeVal(intervals, day, mo, year);
            String latVal = findLatVal(lat);
            String lonVal = findLonVal(lon);

            //Discover U and V
            U = findUV(urlWithoutDotHtml + "ascii?u" + timeVal + "[0:1:0]" + latVal + lonVal);
            V = findUV(urlWithoutDotHtml + "ascii?v" + timeVal + "[0:1:0]" + latVal + lonVal);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Run-me
        Opendap odap = new Opendap(args);
        print(odap.getU());
        print(odap.getV());
    }

    private static void parseArgs(String[] args) {
        day = Integer.parseInt(args[0]);
        mo = Integer.parseInt(args[1]);
        year = Integer.parseInt(args[2]);
        lat = Double.parseDouble(args[3]);
        lon = Double.parseDouble(args[4]);
    }

    private static String findLatVal(double lat) {
        if(lat < -80.0 || lat > 80.0) {
            throw new IllegalArgumentException("Cannot accept latitude beyond -80.0 or 80.0");
        }
        lat  += 80.0;
        lat *= 3;        print("LONG:" + lat);
        return "[" + (int) lat + ":1:" + (int) lat + "]";
    }

    private static String findLonVal(double lon) {
        if(lon < 20.0) {
            lon += 340.0;
        }
        else {
            lon -= 20.0;
        }
        lon *= 3;
        print("LONG:" + lon);
        return "[" + (int) lon + ":1:" + (int) lon + "]";
    }

    private static double findUV(String path) {
        double ret = 0.0;
        try {
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlConnection;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            String body = "";

            while ((line = in.readLine()) != null) {
                body += line;
            }
           ret = Double.parseDouble(body.split(", ")[2]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static String findTimeVal(String[] intervals, int day, int mo, int year) {

        int daysSinceStart = findDaysBetween(beginningDate, day + "." + mo + "." + year);

        for(int i = 0; i < intervals.length-1; i++) {
            if(daysSinceStart >= Integer.parseInt(intervals[i]) && daysSinceStart < Integer.parseInt(intervals[i+1])) {
                return "[" + i + ":1:" + i + "]";
            }
        }
        return "[71:1:71]";
    }

    private static String[] findTimeIntervals(String urlWithoutDotHtml) {

        String[] ret = null;
        String path = urlWithoutDotHtml + "ascii?time[0:1:71]";
        try {
            URL url = new URL(path);

            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlConnection;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            String body = "";

            while ((line = in.readLine()) != null) {
                body += line;
            }

            String[] intervals = body.split(", ");
            ret = Arrays.copyOfRange(intervals, 1, intervals.length);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static int findDaysBetween(String start, String end) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        int diff = -1;
        try {
            Date dateStart = simpleDateFormat.parse(start);
            Date dateEnd = simpleDateFormat.parse(end);
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / 86400000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }
    public double getU() {
        return U;
    }

    public double getV() {
        return V;
    }

    private static void openHtmlURL(URL url) {
        try {
            Desktop.getDesktop().browse(url.toURI());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        print("Successfully opening, " + url);
    }

    public static void print(Object o) {
        System.out.println(o.toString());
    }

}

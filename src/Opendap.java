/**
 * Created by Bob S on 6/30/2016.
 */

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.awt.Desktop;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.IntSummaryStatistics;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Opendap {

    private static String beginningDate = "5.10.1992";

    public static void main(String[] args) {

        try {
            URL base = new URL("http://podaac-opendap.jpl.nasa.gov/opendap/allData/oscar/preview/L4/oscar_third_deg/");
            URL url = new URL(base, buildFileName(args[2]));

            //openHtmlURL(url.toString());

            int day = Integer.parseInt(args[0]);
            int mo = Integer.parseInt(args[1]);
            int year = Integer.parseInt(args[2]);

            String urlWithoughDotHtml = url.toString().substring(0, url.toString().length()-4);

            String[] intervals = findTimeIntervals(urlWithoughDotHtml);
            for(int i = 0; i<intervals.length;i++) {
                print(intervals[i]);
            }

            String timeVal = findTimeStringVal(intervals, day, mo, year);
            print(timeVal);

            String concatUrl = urlWithoughDotHtml + "ascii?u" + timeVal + "[0:1:0][0:1:480][0:1:1200]";
            openHtmlURL(concatUrl);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildFileName(String name) {
        return "oscar_vel" + name + ".nc.gz.html";
    }

    private static String findTimeStringVal(String[] intervals, int day, int mo, int year) {

        int daysSinceStart = findDaysBetween(beginningDate, day + "." + mo + "." + year);

        for(int i = 0; i < intervals.length-1; i++) {
            if(daysSinceStart >= Integer.parseInt(intervals[i]) && daysSinceStart < Integer.parseInt(intervals[i+1])) {
                return "[" + i + ":1:71]";
            }
        }
        return "[71:1:71]";
    }

    private static String[] findTimeIntervals(String urlWithoughDotHtml) {

        String[] ret = null;
        String path = urlWithoughDotHtml + "ascii?time[0:1:71]";
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

    public static int findDaysBetween(String start, String end) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        int diff = -1;
        try {
            Date dateStart = simpleDateFormat.parse(start);
            Date dateEnd = simpleDateFormat.parse(end);
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) /  86400000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    private static ArrayList getU(int mo, int day) {
        ArrayList ret = new ArrayList();
        return ret;
    }

    private static ArrayList getV(int mo, int day) {
        ArrayList ret = new ArrayList();
        return ret;
    }

    private static void openHtmlSource(File f) {
        try {
            Desktop.getDesktop().browse(f.toURI());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        print("Successfully opening, " + f.getPath());
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

    public static void print(Object o) {
        System.out.println(o.toString());
    }

}

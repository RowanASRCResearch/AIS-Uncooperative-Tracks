/**
 * Created by Bob S on 6/30/2016.
 */

import java.net.*;
import java.io.*;
import java.awt.Desktop;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Opendap {

    public static void main(String[] args) {

        try {
            URL base = new URL("http://podaac-opendap.jpl.nasa.gov/opendap/allData/oscar/preview/L4/oscar_third_deg/");
            URL url = new URL(base, buildFileName(args[2]));

            //openHtmlURL(url.toString());

            int day = Integer.parseInt(args[0]);
            int mo = Integer.parseInt(args[1]);
            int year = Integer.parseInt(args[2]);

            String timeVal = findTimeVal(day, mo, year);
            print(timeVal);

            String concatUrl = url.toString().substring(0, url.toString().length()-4) + "ascii?u" + timeVal + "[0:1:0][0:1:480][0:1:1200]";
            openHtmlURL(concatUrl);

            print(findDaysBetween("5.10.1992", "" + day + "." + mo + "." + year));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildFileName(String name) {
        return "oscar_vel" + name + ".nc.gz.html";
    }

    private static String findTimeVal(int day, int mo, int year) {
        int daysBewteen = findDaysBetween("1.1." + year, "" + day + "." + mo + "." + year);
        return "[" + (daysBewteen / 5) + ":1:71]";
    }

    public static int findDaysBetween(String start, String end) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        long diff = -1;
        try {
            Date dateStart = simpleDateFormat.parse(start);
            Date dateEnd = simpleDateFormat.parse(end);

            //time is always 00:00:00 so rounding should help to ignore the missing hour when going from winter to summer time as well as the extra hour in the other direction
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return (int) diff;
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

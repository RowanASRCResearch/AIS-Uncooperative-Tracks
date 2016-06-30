/**
 * Created by Bob S on 6/30/2016.
 */

import java.net.*;
import java.io.*;
import java.awt.Desktop;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Opendap {

    public static void main(String[] args) {

        try {
            URL base = new URL("http://podaac-opendap.jpl.nasa.gov/opendap/allData/oscar/preview/L4/oscar_third_deg/");
            URL url = new URL(base, buildFileName(args[2]));

            openHtmlURL(url.toString());

            getU(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            getV(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildFileName(String name) {
        return "oscar_vel" + name + ".nc.gz.html";
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

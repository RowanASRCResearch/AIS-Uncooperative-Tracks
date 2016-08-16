package gathering;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Research on 8/1/2016.
 */
public class ApiFascade {

    String station = "";
    String from = "";
    String to = "";
    String datum = "MSL";
    String baseUrl = "http://tidesandcurrents.noaa.gov/api/datagetter?";
    String endUrl = "&units=metric&time_zone=gmt&format=json";

    String[] options = {"wind"};

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

    String urlBuilder() {
        String url = "";
        for (String option : options) {

            url = baseUrl + "begin_date=" + from + "&end_date=" + to + "&station=" + station + "&product=" + option + "&datum=" + datum + endUrl;
        }

        return url;
    }

    String scanPage(String pageUrl) throws IOException {
        URL url = new URL(pageUrl);
        String text = "";
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            text += "\n" + line;
        }

        return text;
    }

    String grabRecord(String item) {
        String text = "";


        return text;

    }


    public Object[] formatData(String str) {

        Object[] data = new Object[3];
        int id = 0;
        Point station = new Point(0, 0);
        float s = 0;
        float d = 0;

        String[] list = str.split(",");
        for (int i = 0; i < list.length; i++) {

            if (list[i].contains("\"lat\":")) {
                list[i] = rmString(list[i], "\"");
                list[i] = rmString(list[i], "lat:");
                System.out.println(list[i]);
                list[i] = rmString(list[i], "}");
                station.latitude = Float.parseFloat(list[i]);
            }

            if (list[i].contains("\"lon\":")) {
                list[i] = rmString(list[i], "\"}");
                list[i] = rmString(list[i], "\"");
                list[i] = rmString(list[i], "lon:");
                System.out.println(list[i]);
                station.longitude = Float.parseFloat(list[i]);

            }

            if (list[i].contains("\"s\":")) {
                list[i] = rmString(list[i], "\"");
                list[i] = rmString(list[i], "s:");
                System.out.println(list[i]);
                list[i] = rmString(list[i], "}");
                s = Float.parseFloat(list[i]);
            }

            if (list[i].contains("\"d\":")) {
                list[i] = rmString(list[i], "\"");
                list[i] = rmString(list[i], "d:");
                System.out.println(list[i]);
                list[i] = rmString(list[i], "}");
                d = Float.parseFloat(list[i]);
            }

        }

        data[0] = station;
        data[1] = s;
        data[2] = d;


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
package gathering;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Research on 8/1/2016.
 */
public class ApiFascade {

    String station = "";
    String from = "";
    String to = "";
    String datum = "";
    String baseUrl = "http://tidesandcurrents.noaa.gov/api/datagetter?";
    String endUrl = "&units=metric&time_zone=gmt&format=json";

    String[] options = {"wind"};

    public ApiFascade(String[] args) {

        Boolean print = false;
        switch (args.length) {
            case 4:
                //Statements
                station = args[0];
                from = args[1];
                to = args[2];
                datum = args[3];
                print = true;
                break; //optional
            case 3:
                //Statements
                station = args[0];
                from = args[1];
                datum = args[2];
                print = true;
                break; //optional
            //You can have any number of case statements.
            default: //Optional
                //Statements
                System.out.print("Please Enter 3 or 4 arguments!");
        }

        if (print) {
            System.out.println("0: " + station);
            System.out.println("1: " + from);
            System.out.println("2: " + to);
            System.out.println("4: " + datum);

        }


    }

    public static void main(String args[]) throws IOException {

        ApiFascade fascade = new ApiFascade(args);
        String text = fascade.urlBuilder();
        System.out.print(text);
        System.out.print(fascade.scanPage(text));
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


    public class data {
        String t = "";
        String s = "";
        String d = "";
        String dr = "";
        String g = "";
        String f = "";

        public data(String t, String s, String d, String dr, String g, String f) {
            this.t = t;
            this.s = s;
            this.d = d;

            this.dr = dr;
            this.g = g;
            this.f = f;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getDr() {
            return dr;
        }

        public void setDr(String dr) {
            this.dr = dr;
        }

        public String getG() {
            return g;
        }

        public void setG(String g) {
            this.g = g;
        }

        public String getF() {
            return f;
        }

        public void setF(String f) {
            this.f = f;
        }
    }

}

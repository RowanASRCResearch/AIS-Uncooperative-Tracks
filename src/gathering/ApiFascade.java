package gathering;

import com.sun.org.apache.xpath.internal.operations.Bool;

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

    String[] options = {"water_level", "air_temperature", "water_temperature", "wind", "air_pressure", "air_gap", "conductivity", "visibility", "humidity", "salinity",
            "hourly_height", "high_low", "daily_mean", "monthly_mean", "one_minute_water_level", "predictions", "datums", "currents"};

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

    public static void main(String args[]) {

        ApiFascade fascade = new ApiFascade(args);
        fascade.urlBuilder();
    }

    void urlBuilder() {
        for (String option : options) {

            System.out.println(baseUrl + "begin_date=" + from + "&end_date=" + to + "&station=" + station + "&product=" + option + "&datum=" + datum + endUrl);
        }

    }


}

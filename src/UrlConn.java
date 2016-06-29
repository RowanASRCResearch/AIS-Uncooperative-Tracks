/**
 * Created by Bob S on 6/29/2016.
 */
import java.net.*;
import java.io.*;
import java.awt.Desktop;

class URLConnDemo {

    public static void main(String[] args) {
        try {
            // run on arg: http://tidesandcurrents.noaa.gov/stations.html
            URL url = new URL(args[0]);
            File htmlSource = new File("C:\\Users\\Bob S\\IdeaProjects\\AIS-Uncooperative-Tracks\\out\\source.html");

            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;

            connection = (HttpURLConnection) urlConnection;

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String filetext = buildUrlString(in);

            writeToFile(filetext, htmlSource);

            openHtml(htmlSource);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildUrlString(BufferedReader in) {
        String urlString = "";
        try {

            String current;
            while ((current = in.readLine()) != null) {
                urlString += current;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlString;
    }

    private static void writeToFile(String text, File f) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(text);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        print("Succesfully wrote to, " + f.getPath());
    }

    private static void openHtml(File f) {
        try {
            Desktop.getDesktop().browse(f.toURI());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        print("Successfully opening, " + f.getPath());
    }

    public static void print(Object o) {
        System.out.println(o.toString());
    }
}

package fasade;

import java.io.*;

// Gson imports for JSON conversion
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * Created by lapost48 on 6/23/2016.
 */
public class JsonTest {

    public static void main(String[] args) {
        Gson gson = new Gson();
        DatabaseInformation db = new DatabaseInformation();
        JsonReader jr;
        try {
            jr = new JsonReader(new FileReader(new File("JSONTEST.json")));
            db = gson.fromJson(jr, DatabaseInformation.class);
            jr.close();
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }
    }

}

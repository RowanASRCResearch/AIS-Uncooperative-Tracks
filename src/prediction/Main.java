package prediction;

import io.CSVParserException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by lapost48 on 7/14/2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException, IOException, CSVParserException {

        new Controller("new.csv", "244790009", "2016-04-17", "330");
    }
}

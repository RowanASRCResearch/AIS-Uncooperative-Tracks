package fasade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lapost48 on 7/1/2016.
 */
public class AisDatabaseFasade extends DatabaseFasade {

    public AisDatabaseFasade() {
        super();
    }

    public ResultSet getSpeeds(String id) {
        String col = columnNames.get("speed");
        String query = "SELECT " + columnNames.get("time") + "," + col + " FROM " + tableName + " where " + columnNames.get("id") + "=" + id;

        return runQuery(query);
    }

}

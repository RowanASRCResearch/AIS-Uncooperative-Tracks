package fasade;

/**
 * Created by lapost48 on 6/23/2016.
 */
public class DatabaseInformation {

    String databaseName;
    String tableName;
    String[] columnNames;
    String user;
    String password;
    int numberOfColumns;

    public DatabaseInformation() {}

    /*
    public void printAll() {
        System.out.println(databaseName);
        System.out.println(tableName);
        for(String s:columnNames)
            System.out.print(s + " ");
        System.out.println("\n" + user);
        System.out.println(password);
    }
    */

}

package fasade;

/**
 * Created by lapost48 on 6/23/2016.
 */
public class DatabaseInformation {

    String dbName;
    String tblName;
    String[] colNames;
    String user;
    String password;

    public DatabaseInformation() {}

    /*
    public void printAll() {
        System.out.println(dbName);
        System.out.println(tblName);
        for(String s:colNames)
            System.out.print(s + " ");
        System.out.println("\n" + user);
        System.out.println(password);
    }
    */

}

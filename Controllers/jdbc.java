package isaiah.jdbc;

import java.sql.*;

/** establish connection
 *
 */
public abstract class jdbc {

private  static final String protocol = "jdbc";
private static final String vendor = ":mysql:";
private static final String location = "//localhost/";
private static final String databasename = "client_schedule";
private static final String jdburl = protocol + vendor + location + databasename  + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /** establish sql connection
     *
     * @throws ClassNotFoundException  establish sql connection
     * @throws SQLException  establish sql connection
     */
    public static void openconnection() throws ClassNotFoundException, SQLException {

            Class.forName(driver);
            connection = DriverManager.getConnection(jdburl,username,password);
            System.out.println("connection good");


    }

    /** close sql connection
     *
     * @throws SQLException close sql connection
     */
    public static void closeconnection() throws SQLException {
        connection.close();
        System.out.println("connection closed");

    }

}

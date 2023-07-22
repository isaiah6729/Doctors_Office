package isaiah.jdbc;


import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * users objects
 *
 * */
public class users {

    private static int users_ID;
    private static String usersname;
    private static String password;


    /** constructor
     *
     * @param users_ID constructor
     * @param usersname constructor
     */
    public users(int users_ID, String usersname) {
        users.users_ID = users_ID;
        users.usersname = usersname;
    }

    /** getters
     *
     * @return getters
     */
    public int getusers_ID() {
        return users_ID;
    }

    public  String getusersname() {
        return usersname;
    }

    public static String getpassword() {
        return password;
    }

}

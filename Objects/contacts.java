package isaiah.jdbc;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

/**
 *   this class implements the contact objects
 */
public class contacts  {

    private int contacts_ID;
    private String contacts_name;
    private String email;

    /** constructor
     *
     * @param contacts_ID constructor
     * @param contacts_name constructor
     * @param email constructor
     */
    contacts(int contacts_ID, String contacts_name, String email) {
        this.contacts_ID = contacts_ID;
        this.contacts_name = contacts_name;
        this.email = email;
    }

    /** getters
     *
     * @return getters
     */
    public int getcontacts_ID() {
        return this.contacts_ID;
    }

    public String getcontactsname() {
        return this.contacts_name;
    }

    public String getEmail() {
        return this.email;
    }

    /** set contact boxes */
    private static ObservableList<contacts> allcontacts = FXCollections.observableArrayList();

    /** set contact boxes
     *
     * @param contacts set contact boxes
     */
    public static void addcontacts(contacts contacts) {
        allcontacts.add(contacts);
    }

    /** set contact boxes */
    public static void deletecontacts() {
        allcontacts.clear();
    }

    /** set contact boxes
     *
     * @return  set contact boxes
     */
    public static ObservableList<contacts> getallcontacts() {
        return allcontacts;
    }

    /** set to string contact boxes
     *
     * @return set to string contact boxes
     */
    public String toString() { return  this.contacts_name + " | " + this.email; }

}


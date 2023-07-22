package isaiah.jdbc;


import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

/** division objects
 *
 */
public class divisions  {

    private int divisions_ID;
    private String Divisions;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int country_ID;

    /** constructor
     *
     * @param id  constructor
     * @param division constructor
     */
    public divisions (int id, String division) {
        this.Divisions = division;
        this.divisions_ID = id;
    }

    /** getters
     *
     * @return getters
     */
    public int getdivisions_ID() {
        return this.divisions_ID;
    }

    public String getdivisions() {
        return this.Divisions;
    }

    public int getCountry_ID() {
        return this.country_ID;
    }

    public LocalDateTime getCreate_Date() {
        return this.Create_Date;
    }

    public String getCreated_By() {
        return this.Created_By;
    }

    public Timestamp getLast_Update() {
        return this.Last_Update;
    }

    public String getLast_Updated_By() {
        return this.Last_Updated_By;
    }

    /** set division boxes */
    private static ObservableList<divisions> alldivisions = FXCollections.observableArrayList();

    /** set division boxes
     *
     * @param divisions  set division boxes
     */
    public static void adddivisions(divisions divisions) {
        alldivisions.add(divisions);
    }

    /** set division boxes */
    public static void deletedivisions() {
        alldivisions.clear();
    }

    /** set division boxes
     *
     * @return set division boxes
     */
    public static ObservableList<divisions> getalldivisions() {
        return alldivisions;
    }

    /** set to string division boxes
     *
     * @return set to string division boxes
     */
    public String toString() {
        return this.Divisions;
    }

}

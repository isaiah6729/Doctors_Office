package isaiah.jdbc;



import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * countries object for the combo boxes
 */
public class countries {

    private static ObservableList<countries> allcountries = FXCollections.observableArrayList();
    private int Country_ID;
    private String Country;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;

    /** constructor
     *
     * @param id constructor
     * @param country constructor
     */
    public countries (int id, String country) {
        this.Country = country;
        this.Country_ID = id;
    }

    /** getters
     *
     * @return getters
     */
    public int getCountry_ID() {
        return this.Country_ID;
    }

    public String getCountry() {
        return this.Country;
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

    /** for the combox boxes
     *
     * @param countries for the combox boxes
     */
    public static void addcountries(countries countries) {
        allcountries.add(countries);
    }

    /** delete from the combox boxes
     *
     */
    public static void deletecountries() {
        allcountries.clear();
    }

    /**
     *
     * @return get all the countries
     */
    public static ObservableList<countries> getallcountries() {
        return allcountries;
    }

    /**
     *
     * @return string the countries
     */
 public String toString() {
        return this.Country;
    }

}

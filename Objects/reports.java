package isaiah.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * reports objects
 *
 * */
public class reports {

    private int reports_ID;
    private String reports_name;

    /** constructor
     *
     * @param reports_ID constructor
     * @param reports_name constructor
     */
    reports(int reports_ID, String reports_name) {
        this.reports_ID = reports_ID;
        this.reports_name = reports_name;
    }

    /**    getters
     *
     * @return getters
     */
    public int getReports_ID() {
        return this.reports_ID;
    }

    public String getReports_name() {
        return this.reports_name;
    }

    /** set report boxes */
    private static ObservableList<reports> allreports = FXCollections.observableArrayList();

    /** set report boxes */
    public static void addreports() {
        allreports.add(new reports(1,"Type and Month"));
        allreports.add(new reports(2,"Contact Schedule"));
    }

    /** set report boxes */
    public static void deleteallreports() {
        allreports.clear();
    }

    /** set report boxes */
    public static ObservableList<reports> getAllreports() {            return allreports;
    }

    /** set report boxes to string
     *
     * @return set report boxes to string
     */
    public String toString() { return  this.reports_name; }

    /** lambda interface */
    public interface makingreport {
        boolean report(int reporting);
    }

}





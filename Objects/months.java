package isaiah.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * gather the months objects
 */
public class months {

    private int months_ID;
    private String month_name;

    /** constructor
     *
     * @param months_ID constructor
     * @param month_name constructor
     */
    months(int months_ID, String month_name) {
        this.months_ID = months_ID;
        this.month_name = month_name;
    }

    /** getters
     *
     * @return getters
     */
    public int getMonths_ID() {
        return this.months_ID;
    }

    public String getMonth_name() {
        return this.month_name;
    }

    /** set month boxes */
    private static ObservableList<months> allmonths = FXCollections.observableArrayList();

    /** set month boxes */
    public static void addmonths() {

        allmonths.add(new months(1,"January"));
        allmonths.add(new months(2,"February"));
        allmonths.add(new months(3,"March"));
        allmonths.add(new months(4,"April"));
        allmonths.add(new months(5,"May"));
        allmonths.add(new months(6,"June"));
        allmonths.add(new months(7,"July"));
        allmonths.add(new months(8,"August"));
        allmonths.add(new months(9,"September"));
        allmonths.add(new months(10,"October"));
        allmonths.add(new months(11,"November"));
        allmonths.add(new months(12,"December"));
    }

    /** set month boxes */
    public static void deleteallmonths() {
        allmonths.clear();
    }

    /** set month boxes
     *
     * @return set month boxes
     */
    public static ObservableList<months> getallmonths() {
        return allmonths;
    }


    /** set month boxes to string
     *
     * @return  set month boxes to string
     */
    public String toString() { return  this.month_name; }

}


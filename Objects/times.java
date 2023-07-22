package isaiah.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 *
 * times objects
 *
 * */
public class times {

    private int times_ID;
    private LocalTime times_name;

    /** constructor
     *
     * @param times_ID constructor
     * @param times_name constructor
     */
    times(int times_ID, LocalTime times_name) {
        this.times_ID = times_ID;
        this.times_name = times_name;
    }

    /** getters
     *
     * @return getters
     */
    public int getTimes_ID() {
        return this.times_ID;
    }

    public LocalTime getTimes_name() {
        return this.times_name;
    }

    /** set time boxes  1 */
    private static ObservableList<times> alltimes = FXCollections.observableArrayList();
    /** set time boxes  2 */
    private static ObservableList<times> alltimes2 = FXCollections.observableArrayList();

    /** set time boxes  1
     *
     * @param times set time boxes  1
     */
    public static void addtimes(times times) {
        alltimes.add(times);
    }

    /** set time boxes  1
     *
     * @param times  set time boxes  1
     */
    public static void deletetimes(times times) {alltimes.remove(times);}

    /** set time boxes  1
     *
     * @return set time boxes  1
     */
    public static ObservableList<times> getAlltimesstart() {

        int timesid = 1;

        LocalTime start = LocalTime.from(LocalTime.of(8, 0).atOffset(ZoneOffset.of("-0500")));
        LocalTime end = LocalTime.from(LocalTime.of(21, 00).atOffset(ZoneOffset.of("-0500")));

        // add by 30 minutes
        while (start.isBefore(end)) {
            alltimes.add(new times(timesid, start));
            start = start.plusMinutes(30);
            timesid++;
        }
        return alltimes;
    }

    /** set time boxes  2
     *
     * @return set time boxes  2
     */
    public static ObservableList<times> getAlltimesend() {

        // get the id # to get time object
        int timesid = 1;

        LocalTime start = LocalTime.from(LocalTime.of(8, 0).atOffset(ZoneOffset.of("-0500")));
        LocalTime end = LocalTime.from(LocalTime.of(21, 30).atOffset(ZoneOffset.of("-0500")));

        // add 30 minutes
        while (start.isBefore(end)) {
            alltimes2.add(new times(timesid, start));
            start = start.plusMinutes(30);
            timesid++;
        }

        return alltimes2;
    }

    /**     set time boxes  to string
     *
     * @return   set time boxes  to string
     */
    public String toString() { return  String.valueOf(times_name); }

}




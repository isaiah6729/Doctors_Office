package isaiah.jdbc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  this class implements the appointment objects
 */
public class appointmentinfo {

    private int appointmentid;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerid;
    private int userid;
    private int contactid;

    /** contact report appointment constructor
     *
     * @param Contact_ID  contact report appointment constructor
     * @param appointmentid  contact report appointment constructor
     * @param Title contact report appointment constructor
     * @param Description  contact report appointment constructor
     * @param Type  contact report appointment constructor
     * @param Start  contact report appointment constructor
     * @param end  contact report appointment constructor
     * @param Customer_ID  contact report appointment constructor
     */
    public appointmentinfo(int Contact_ID, int appointmentid, String Title, String Description, String Type,
                           LocalDateTime Start, LocalDateTime end, int Customer_ID) {

        this.contactid = Contact_ID;
        this.appointmentid = appointmentid;
        this.title = Title;
        this.description = Description;
        this.type = Type;
        this.start = Start;
        this.end = end;
        this.customerid = Customer_ID;

    }

    /** full appointment constructor
     *
     * @param Appointment_id full appointment constructor
     * @param Title full appointment constructor
     * @param Description full appointment constructor
     * @param Location full appointment constructor
     * @param Contact_ID full appointment constructor
     * @param Type full appointment constructor
     * @param Start full appointment constructor
     * @param end full appointment constructor
     * @param Customer_ID full appointment constructor
     * @param User_ID full appointment constructor
     */
    public appointmentinfo(int Appointment_id, String Title, String Description, String Location, int Contact_ID, String Type,
                           LocalDateTime Start, LocalDateTime end, int Customer_ID, int User_ID) {

        this.appointmentid = Appointment_id;
        this.title = Title;
        this.description = Description;
        this.location = Location;
        this.type = Type;
        this.start = Start;
        this.end = end;
        this.customerid = Customer_ID;
        this.userid = User_ID;
        this.contactid = Contact_ID;
    }

    /* ************************************* getters *************************************************/

    public int getAppointmentid() {
        return this.appointmentid;
    }

    public int getContactid() {
        return this.contactid;
    }

    public int getCustomerid() {
        return this.customerid;
    }

    public int getUserid() {
        return this.userid;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLocation() {
        return this.location;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    /* ************************************* all types *************************************************/

    /** set type combo boxes  */
    private static ObservableList<String> alltypes = FXCollections.observableArrayList();

    /** set type combo boxes
     *
     * @param type set type combo boxes
     */
    public static void addtypes(String type) {
        alltypes.add(type);
    }

    /** set monthly report  */
    public static void deletealltypes() {
        alltypes.clear();
    }

    /** set type report
     *
     * @return set type report
     */
    public static ObservableList<String> getalltypes() {

        appointmentinfo.addtypes("Follow up");
        appointmentinfo.addtypes("Planning Session");
        appointmentinfo.addtypes("First Session");
        appointmentinfo.addtypes("Second Session");
        appointmentinfo.addtypes("Final Session");

        return alltypes;
    }

    /** turn type to string
     *
     * @return turn type to string
     */
    public String toString() {
        return this.type;
    }

    /* ************************************* all appointments *************************************************/

    /** set appointments */
    private static ObservableList<appointmentinfo> allappointments = FXCollections.observableArrayList();

    /** set appointments
     *
     * @param appointment set appointments
     */
    public static void addappointments(appointmentinfo appointment) {
        allappointments.add(appointment);
    }

    /** set appointments
     *
     * @param selectedappointment  set appointments
     */
    public static void deleteappointments(appointmentinfo selectedappointment) {
        allappointments.remove(selectedappointment);
    }

    /** set appointments */
    public static void deleteappointments2() {

        allappointments.clear();
    }

    /** set appointments
     *
     * @return  set appointments
     */
    public static ObservableList<appointmentinfo> getAllappointments() {
        return allappointments;
    }

                                                        ///////////////////////////////////////////////
    /* ************************************* type month report *************************************************/

    private static ObservableList<appointmentinfo> alltype_month_report = FXCollections.observableArrayList();

    public static void addtypesmonths(appointmentinfo typesmonths) {
        alltype_month_report.add(typesmonths);
    }

    public static ObservableList<appointmentinfo> getalltypes_months() {
        return alltype_month_report;
    }

                                        ////////////////////////////////////////
    /* ************************************* contact report *************************************************/

    /** set contact report  */
    private static ObservableList<appointmentinfo> allcontacts_report = FXCollections.observableArrayList();

    /** set contact report
     *
     * @param contacts set contact report
     */
    public static void addallcontacts_report(appointmentinfo contacts) {
        allcontacts_report.add(contacts);
    }

    public  static  void deletecontactReport() { allcontacts_report.clear();}

    /** set contact report
     *
     * @return  set contact report
     */
    public static ObservableList<appointmentinfo> getallallcontacts_report() {
        return allcontacts_report;
    }


    /* ************************************* monthly weekly report *************************************************/

    /** set monthly report  */
    private static ObservableList<appointmentinfo> alldatetimes = FXCollections.observableArrayList();

    /** set weekly report  */
    private static ObservableList<appointmentinfo> alldatetimes2 = FXCollections.observableArrayList();

    /** set monthly report
     *
     * @param dates set monthly report
     */
    public static void addalldates_report(appointmentinfo dates) {
        alldatetimes.add(dates);
    }

    /** set weekly report
     *
     * @param dates set weekly report
     */
    public static void addalldates_report2(appointmentinfo dates) {
        alldatetimes2.add(dates);
    }

    /** set monthly report  */
    public static void deletealldatetimes() {
        alldatetimes.clear();
    }

    /** set weekly report  */
    public static void deletealldatetimes2() {
        alldatetimes2.clear();
    }

    /** set monthly report
     *
     * @return  set monthly report
     */
    public static ObservableList<appointmentinfo> getAlldatetimes() {
        return alldatetimes;
    }

    /** set weekly report
     *
     * @return  set weekly report
     */
    public static ObservableList<appointmentinfo> getAlldatetimes2() {
        return alldatetimes2;
    }

}


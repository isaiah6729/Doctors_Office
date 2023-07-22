package isaiah.jdbc;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.sql.*;
import java.text.SimpleDateFormat;

import static isaiah.jdbc.appointmentinfo.addallcontacts_report;
import static isaiah.jdbc.appointmentinfo.addappointments;
import static isaiah.jdbc.countries.addcountries;
import static isaiah.jdbc.divisions.adddivisions;

/**
 *
 * commands for SQL database
 *
 * */
public class jdbccommands  {

    // user object for logged in peerson
    private static users loggedin;

    // function to get user logged in
    public static users getloggedin() {
        return loggedin;
    }

    /* **************************************** select all customers appointments ******************************************************** */

    /** select all customers
     *
     * @return  /** select all customers
     * @throws SQLException  /** select all customers
     */
    public static ObservableList<customerinfo> selectallcustomers () throws SQLException {

        String allcustomers = "select * from customers";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(allcustomers);
        ResultSet resultSet = preparedStatement.executeQuery();

        customerinfo customer = null;
        while (resultSet.next()) {

            int customer_ID = resultSet.getInt("Customer_ID");
            String name = resultSet.getString("customer_name");
            String address = resultSet.getString("address");
            String postalcode = resultSet.getString("postal_code");
            Timestamp createdate = resultSet.getTimestamp("create_date");
            Timestamp lastupdate = resultSet.getTimestamp("last_update");
            String phone = resultSet.getString("phone");
            int division_ID = resultSet.getInt("division_ID");
            String createdby = resultSet.getString("customer_name");
            String updatedby = resultSet.getString("customer_name");

            // place in object
            customer = new customerinfo(customer_ID, name, address, postalcode, phone, createdate,
                    createdby, lastupdate, updatedby, division_ID);

            customerinfo.addcustomer(customer);
        }

        return customerinfo.getallcustomerrecords();
    }

    /** select all appointments
     *
     * @throws SQLException  /** select all appointments
     */
    public static void selectallappointments () throws SQLException {

        String allappointments = "select * from appointments";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(allappointments);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            int Appointment_ID = resultSet.getInt("appointment_id");
            String Title = resultSet.getString("title");
            String Description = resultSet.getString("description");
            String Location = resultSet.getString("location");
            int Contact_ID = resultSet.getInt("Contact_ID");
            String Type = resultSet.getString("type");
            LocalDateTime Start = (LocalDateTime) resultSet.getObject("start");
            LocalDateTime End = (LocalDateTime) resultSet.getObject("end");;
            int Customer_ID = resultSet.getInt("Customer_ID");
            int User_ID = resultSet.getInt("User_ID");

        // place into object
            addappointments(new appointmentinfo(Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID ,  User_ID));

        }
    }

    /* *************************************** add appointment page ********************************************************* */

    /**    choose a country from database
     *
     * @throws SQLException     /**    choose a country from database
     */
    public static void select_countries () throws SQLException {

        String countries = "select country, country_id FROM countries";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(countries);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("country_id");
            String name = resultSet.getString("country");
            addcountries(new countries(id, name));
        }
    }

    /**    choose a division from database
     *
     * @param id
     * @throws SQLException     /**    choose a division from database
     */
    public static void select_divisions (int id) throws SQLException {

        String divisions = "select * FROM first_level_divisions where country_id = ?";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(divisions);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id1 = resultSet.getInt("division_id");
            String name = resultSet.getString("division");
            adddivisions(new divisions(id,name));
        }

    }

    /**    select desired contacts
     *
     * @throws SQLException
     */
    public static void select_contacts () throws SQLException {

        String countries = "select * FROM contacts";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(countries);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("contact_id");
            String name = resultSet.getString("contact_name");
            String email = resultSet.getString("email");
            contacts.addcontacts(new contacts(id, name,email));
        }
    }

    /* *************************************** delete customers appointments ********************************************************* */

    /** delete customer
     *
     * @param customer_id   /** delete customer
     * @return   /** delete customer
     * @throws SQLException   /** delete customer
     */
    public static boolean delete_customers(int customer_id) throws SQLException {

        String delete = "DELETE FROM customers WHERE customer_ID = ?";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(delete);
        preparedStatement.setInt(1, customer_id);

        System.out.println(customer_id);

        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            return true;
        } else {
            return false;

        }
    }

    /** delete appointment
     *
     * @param appointment_id     /** delete appointment
     * @return    /** delete appointment
     * @throws SQLException     /** delete appointment
     */
    public static boolean delete_appointments(int appointment_id) throws SQLException {

        String delete = "DELETE FROM appointments WHERE appointment_id = ?";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(delete);
        preparedStatement.setInt(1, appointment_id);

        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            return true;
        } else {
            return false;

        }
    }
    /* **************************************** add customers appointments ******************************************************** */

    /** add customers to databaase
     *
     * @param name    /** add customers to databaase
     * @param address    /** add customers to databaase
     * @param postalcode   /** add customers to databaase
     * @param phone    /** add customers to databaase
     * @param time    /** add customers to databaase
     * @param createby    /** add customers to databaase
     * @param time2    /** add customers to databaase
     * @param updateby    /** add customers to databaase
     * @param division_ID    /** add customers to databaase
     * @return   /** add customers to databaase
     * @throws SQLException    /** add customers to databaase
     */
    public static boolean addcustomer(String name, String address, String postalcode,
                                      String phone,  LocalDateTime time, String createby, LocalDateTime time2, String updateby, int division_ID ) throws SQLException {

        String sql = "INSERT INTO customers (Customer_Name, address, postal_code, " +
                "phone, create_date, created_by, last_update, last_updated_by, division_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3,postalcode);
        preparedStatement.setString(4, phone);
        preparedStatement.setObject(5, time);
        preparedStatement.setString(6,createby);
        preparedStatement.setObject(7,time2);
        preparedStatement.setString(8,updateby);
        preparedStatement.setInt(9, division_ID);

        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            return true;
        }
        else {
            return false;

        }
    }

    /** add appointments
     *
     * @param Title     /** add appointments
     * @param Description     /** add appointments
     * @param Location     /** add appointments
     * @param Type     /** add appointments
     * @param Start      /** add appointments
     * @param End    /** add appointments
     * @param Customer_ID     /** add appointments
     * @param Contact_ID     /** add appointments
     * @param User_ID     /** add appointments
     * @return     /** add appointments
     * @throws SQLException     /** add appointments
     */
    public static boolean addappointment(String Title, String Description, String Location, String Type,
                                         LocalDateTime Start, LocalDateTime End,  int Customer_ID, int Contact_ID, int User_ID) throws SQLException {

        String sql = "INSERT INTO appointments (Title,  Description,  Location,  Type,\n" +
                "                                        start, end, Customer_ID,   Contact_ID, user_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(sql);
        preparedStatement.setString(1,Title);
        preparedStatement.setString(2, Description);
        preparedStatement.setString(3,Location);
        preparedStatement.setString(4,Type);
        preparedStatement.setObject(5, Start);
        preparedStatement.setObject(6,End);
        preparedStatement.setInt(7, Customer_ID);
        preparedStatement.setInt(8, Contact_ID);
        preparedStatement.setInt(9, User_ID);

        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            return true;
        }
        else {
            return false;

        }
    }                                                 ////////////////////////////////////////////
    /* **************************************** update customer appointments ******************************************************** */


    /** update customers
     *
     * @param id     /** update customers
     * @param name     /** update customers
     * @param address     /** update customers
     * @param postalcode     /** update customers
     * @param phone     /** update customers
     * @param updatedate     /** update customers
     * @param updateperson     /** update customers
     * @param division_ID     /** update customers
     * @return     /** update customers
     * @throws SQLException     /** update customers
     */
    public static boolean updatecustomer(int id, String name, String address, String postalcode,
                                         String phone,  LocalDateTime updatedate, String updateperson, int division_ID ) throws SQLException {

        String update = "UPDATE CUSTOMERS SET CUSTOMER_NAME = ?, ADDRESS = ?, POSTAL_CODE = ?,  " +
                "PHONE = ?,  last_update = ?, last_updated_by = ?, division_ID = ?  WHERE CUSTOMER_ID = ?";

        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(update);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3,postalcode);
        preparedStatement.setObject(5, updatedate);
        preparedStatement.setString(6,updateperson);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(7, division_ID);
        preparedStatement.setInt(8,id);

        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /** update appointments
     *
     * @param Title     /** update appointments
     * @param Description    /** update appointments
     * @param Location     /** update appointments
     * @param Type     /** update appointments
     * @param Start     /** update appointments
     * @param End     /** update appointments
     * @param updatedate     /** update appointments
     * @param updateby     /** update appointments
     * @param cuid     /** update appointments
     * @param Contact_ID     /** update appointments
     * @param id     /** update appointments
     * @return     /** update appointments
     * @throws SQLException     /** update appointments
     */
    public static boolean updateappointment(String Title, String Description, String Location,  String Type,
                                            LocalDateTime Start, LocalDateTime End , LocalDateTime updatedate, String updateby, int cuid, int Contact_ID, int id) throws SQLException {

        String update = "UPDATE appointments SET Title = ?, Description = ?, Location = ?,  Type = ?,  Start = ?,   " +
                "End = ?,  last_update = ?, last_updated_by = ?, customer_id = ?, Contact_ID = ? WHERE appointment_ID = ?";

        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(update);

        preparedStatement.setString(1, Title);
        preparedStatement.setString(2,Description);
        preparedStatement.setString(3,Location);
        preparedStatement.setString(4, Type);
        preparedStatement.setObject(5, Start);
        preparedStatement.setObject(6,End);
        preparedStatement.setObject(7,updatedate);
        preparedStatement.setString(8,updateby);
        preparedStatement.setInt(9,cuid);
        preparedStatement.setInt(10, Contact_ID);
        preparedStatement.setInt(11,id);

        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            return true;
        }
        else {
            return false;
        }
    }
                                                        /////////////////////////////////////////////////////
    /* *********************************** selecting information ************************************************************* */


    /** select appointments with id
     *
     * @param id    /** select appointments with id
     * @return    /** select appointments with id
     * @throws SQLException    /** select appointments with id
     */
    public static boolean select_customer_with_appointments (int id) throws SQLException {

        String customerappointments = "SELECT * " + "FROM customers " + "WHERE customer_id IN (SELECT customer_id FROM appointments where customer_id = ?)";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(customerappointments);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id1 = resultSet.getInt("customer_id");
            return true;
        }
        else  {
            return  false;
        }

    }

    /** select type and month report
     *
     * @param type  /** select type and month report
     * @param input1  /** select type and month report
     * @param input2  /** select type and month report
     * @return  /** select type and month report
     * @throws SQLException  /** select type and month report
     */
    public  static int select_type_month_count(String type, LocalDateTime input1, LocalDateTime input2) throws SQLException {

        int rows = 0;

        String return_sql = "SELECT TYPE, start from appointments where type = ? and start between ? and ?";

        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(return_sql);

        preparedStatement.setString(1,type);
        preparedStatement.setObject(2,input1);
        preparedStatement.setObject(3,input2);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String type2 = resultSet.getString("type");
            LocalDateTime date = (LocalDateTime) resultSet.getObject("start");
            LocalDateTime date2 = (LocalDateTime) resultSet.getObject("start");

            // appointmentinfo.addtypesmonths(new appointmentinfo(date, type));
            System.out.println(date + " " + type2 + " " + date2);
            rows++;

        }
        System.out.println(rows);
        return rows;

    }


    /** select appointments in 15 minutes
     *
     * @param input1     /** select appointments in 15 minutes
     * @param input2     /** select appointments in 15 minutes
     * @return     /** select appointments in 15 minutes
     * @throws SQLException     /** select appointments in 15 minutes
     */
    public static boolean select_15min_appointment(LocalDateTime input1, LocalDateTime input2) throws SQLException {

        String dates = "Select * From appointments Where start between ? and ? ";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(dates);
        preparedStatement.setObject(1, input1);
        preparedStatement.setObject(2, input2);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            LocalDateTime d1 = (LocalDateTime) resultSet.getObject("start");
            LocalDateTime d2 = (LocalDateTime) resultSet.getObject("end");
            int appointID = resultSet.getInt("appointment_id");

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

            Alert alert = new Alert(Alert.AlertType.ERROR, "You have appointments in 15 minutes at \n"
                    +  d1.format(dateTimeFormatter) + "\n and it ends at\n " +  d2.format(dateTimeFormatter) + " \nand the appointment id is " + appointID, new ButtonType[0]);
            alert.setTitle("LOGIN");
            DialogPane dialogPane3 = alert.getDialogPane();
            dialogPane3.setStyle("-fx-font-size: 20;");
            Optional<ButtonType> added = alert.showAndWait();

            return true;
        }

        else {

            // alert for no appointments in 15 minutes
            Alert alert = new Alert(Alert.AlertType.ERROR, "You have no appointments in 15 minutes", new ButtonType[0]);
            alert.setTitle("LOGIN");
            DialogPane dialogPane3 = alert.getDialogPane();
            dialogPane3.setStyle("-fx-font-size: 20;");
            Optional<ButtonType> added = alert.showAndWait();
            return false;
        }
    }


    /** select username and password to log in
     * @param inputusername     /** select username and password to log in
     * @param inputpassword     /** select username and password to log in
     * @return     /** select username and password to log in
     * @throws SQLException     /** select username and password to log in
     */
    public static boolean select_username (String inputusername, String inputpassword) throws SQLException {

        String username = "Select User_Name, user_id From Users Where User_Name = ? and Password = ?";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(username);
        preparedStatement.setString(1, inputusername);
        preparedStatement.setString(2, inputpassword);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String username1 = resultSet.getString("User_Name");
            int id = resultSet.getInt("user_id");

            // logged in object
            loggedin = new users(id, username1);

            return true;
        }
        else {
            return false;
        }

    }

    /** select the amount of customers added report
     *
     * @param input1     /** select the amount of customers added report
     * @param input2     /** select the amount of customers added report
     * @return     /** select the amount of customers added report
     * @throws SQLException     /** select the amount of customers added report
     */
    public static int select_customer_amount (LocalDateTime input1, LocalDateTime input2) throws SQLException {

        int rows = 0;

        String return_sql = "SELECT * from customers where create_date between ? and ?";

        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(return_sql);

        preparedStatement.setObject(1,input1);
        preparedStatement.setObject(2,input2);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int customer_ID = resultSet.getInt("Customer_ID");
            rows++;

        }
        return rows;

    }

    /** select contact schedule
     *
     * @param contact_id     /** select contact schedule
     * @param input1     /** select contact schedule
     * @param input2    /** select contact schedule
     * @throws SQLException     /** select contact schedule
     */
    public static void select_contact_appointment (int contact_id, LocalDateTime input1, LocalDateTime input2) throws SQLException {

        String return_sql = "SELECT * from appointments where contact_id = ? and start between ? and ?";

        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(return_sql);

        preparedStatement.setInt(1,contact_id);
        preparedStatement.setObject(2,input1);
        preparedStatement.setObject(3,input2);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int appointment_ID = resultSet.getInt("Appointment_ID");
            int contact_id1 = resultSet.getInt("contact_id");
            String title = resultSet.getString("Title");
            String type = resultSet.getString("Type");
            String description = resultSet.getString("Description");
            LocalDateTime start_date = (LocalDateTime) resultSet.getObject("Start");
            LocalDateTime end_date = (LocalDateTime) resultSet.getObject("End");
            int customer_ID = resultSet.getInt("Customer_ID");

            System.out.println( contact_id1+ " " + appointment_ID + " " +
                    type + " " + title + " " + description + " " + start_date + " " + end_date + " " + customer_ID);

            addallcontacts_report(new appointmentinfo(contact_id1, appointment_ID,title,type,description,start_date,end_date,customer_ID));
        }
    }

                            ///////////////////////////////////////////////////
    /* **************************************** weekly/monthly appts ******************************************************** */

    /** select weekly or monthly appointments
     *
     * @param datetime    /** select weekly or monthly appointments
     * @param datetime2    /** select weekly or monthly appointments
     * @throws SQLException    /** select weekly or monthly appointments
     */
     public static void select_weeklyappointments(LocalDateTime datetime, LocalDateTime datetime2) throws SQLException {

        String return_dates = "SELECT * FROM appointments where start between ? and ? order by start";

        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(return_dates);

        preparedStatement.setObject(1, datetime);
        preparedStatement.setObject(2,datetime2);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int appointment_ID = resultSet.getInt("Appointment_ID");
            int contact_id = resultSet.getInt("contact_id");
            String title = resultSet.getString("Title");
            String type = resultSet.getString("Type");
            String description = resultSet.getString("Description");
            LocalDateTime start_date = (LocalDateTime) resultSet.getObject("Start");
            LocalDateTime end_date = (LocalDateTime) resultSet.getObject("End");
            int customer_ID = resultSet.getInt("Customer_ID");

            System.out.println( appointment_ID + " " +
                    type + " " + title + " " + description + " " + start_date + " " + end_date + " " + customer_ID);
            appointmentinfo.addalldates_report2(new appointmentinfo(contact_id, appointment_ID,title,type,description,start_date,end_date,customer_ID));
        }
    }

    /** select weekly or monthly appointments
     *
     * @param datetime1    /** select weekly or monthly appointments
     * @param datetime2   /** select weekly or monthly appointments
     * @throws SQLException    /** select weekly or monthly appointments
     */
    public  static void select_monthlyappointments (LocalDateTime datetime1, LocalDateTime datetime2) throws SQLException {

        String return_dates = "SELECT * FROM appointments where start between ? and ? order by start";

        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(return_dates);

        preparedStatement.setObject(1, datetime1);
        preparedStatement.setObject(2, datetime2);
        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {
            int appointment_ID = resultSet.getInt("Appointment_ID");
            int contact_id = resultSet.getInt("contact_id");
            String title = resultSet.getString("Title");
            String type = resultSet.getString("Type");
            String description = resultSet.getString("Description");
            LocalDateTime start_date = (LocalDateTime) resultSet.getObject("Start");
            LocalDateTime end_date = (LocalDateTime) resultSet.getObject("End");
            int customer_ID = resultSet.getInt("Customer_ID");

            appointmentinfo.addalldates_report(new appointmentinfo(contact_id, appointment_ID, title, type, description, start_date, end_date, customer_ID));
        }
    }

                                    //////////////////////////////////////////////////
    /* ***************************************validate time slots ********************************************************* */

    /** validates start and end times on appointments
     *
     * @param input1   /** validates start and end times on appointments
     * @param input2   /** validates start and end times on appointments
     * @return   /** validates start and end times on appointments
     * @throws SQLException   /** validates start and end times on appointments
     */
    public static boolean check_start_end_databse (LocalDateTime input1, LocalDateTime input2) throws SQLException {

        String dates = "Select start, end From appointments Where start = ? and end = ?";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(dates);
        preparedStatement.setObject(1, input1);
        preparedStatement.setObject(2, input2);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            LocalDateTime d1 = (LocalDateTime) resultSet.getObject("start");
            LocalDateTime d2 = (LocalDateTime) resultSet.getObject("end");
  //      System.out.println(d1 + "    " + d2);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Time slot taken on that date. " +
                    "Either choose another slot or delete an appointment", new ButtonType[0]);
            Optional<ButtonType> added = alert.showAndWait();

            return true;
        }

        else {
            return false;
        }

    }

    /** validates start and end times on appointments
     *
     * @param input1   /** validates start and end times on appointments
     * @param input2   /** validates start and end times on appointments
     * @return   /** validates start and end times on appointments
     * @throws SQLException   /** validates start and end times on appointments
     */
    public static boolean check_start_databse (LocalDateTime input1, LocalDateTime input2) throws SQLException {

        LocalDateTime d1 = null;

        String dates = "Select start From appointments Where start = ? and start = ?";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(dates);
        preparedStatement.setObject(1, input1);
        preparedStatement.setObject(2, input2);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            d1 = (LocalDateTime) resultSet.getObject("start");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Time slot taken on that start date. " +
                    "Either choose another slot or delete appointment", new ButtonType[0]);
            Optional<ButtonType> added = alert.showAndWait();

            return true;
        }

        else {
            return false;
        }

    }

    /** validates start and end times on appointments
     *
     * @param input1   /** validates start and end times on appointments
     * @param input2   /** validates start and end times on appointments
     * @return   /** validates start and end times on appointments
     * @throws SQLException  /** validates start and end times on appointments
     */
    public static boolean check_end_databse (LocalDateTime input1, LocalDateTime input2) throws SQLException {

        LocalDateTime d1 = null;

        String dates = "Select end From appointments Where end = ? and end = ?";
        PreparedStatement preparedStatement = jdbc.connection.prepareStatement(dates);
        preparedStatement.setObject(1, input1);
        preparedStatement.setObject(2, input2);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            d1 = (LocalDateTime) resultSet.getObject("end");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Time slot taken on that end date. " +
                    "Either choose another slot or delete appointment", new ButtonType[0]);
            Optional<ButtonType> added = alert.showAndWait();

            return true;
        }

        else {
            return false;
        }

    }
}
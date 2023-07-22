package isaiah.jdbc;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.SimpleFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * updates appointments
 *
 * */
public class updateappointment implements Initializable {


    Stage stage;
    Parent scene;

    public static int transferid = 0;

    public static customerinfo customerid = null;
    public static users userid = null;

    customerinfo appointment = null;
    customerinfo customer = null;

    public Label time;

    public RadioButton all;
    @FXML public RadioButton month;
    @FXML public RadioButton week;

    public TextField date;
    @FXML public TextField apid;
    @FXML public TextField aptitle;
    @FXML public TextField apdescription;
    @FXML public TextField aplocation;
    @FXML public TextField apcustomer;
    @FXML public TextField apuser;

    public TableView aptable;

    public TableColumn apidtable;
    public TableColumn aptabletitle;
    public TableColumn aptabledescr;
    public TableColumn aptableloc;
    public TableColumn aptablecontact;
    public TableColumn aptabletype;
    public TableColumn aptablestart;
    public TableColumn aptableend;
    public TableColumn aptablecuid;
    public TableColumn aptableuserid;

    public DatePicker apdate;

    @FXML
    public ComboBox aptype;
    @FXML
    public ComboBox apCONTACT;
    public ComboBox apend;
    public ComboBox apstart;

    /** initiliaze
     *
     * @param url initiliaze
     * @param resourceBundle initiliaze
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* set date time */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String dates = LocalDateTime.now().format(dateTimeFormatter);
        date.setText(dates);

        /* fill boxes and table */
        try {
            // delete objects to refill them
            contacts.deletecontacts();
            appointmentinfo.deletealltypes();
            appointmentinfo.deleteappointments2();
            jdbccommands.select_contacts();
            jdbccommands.selectallappointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* set combo boxes */
        aptable.setItems(appointmentinfo.getAllappointments());
        apCONTACT.setItems(contacts.getallcontacts());
        aptype.setItems(appointmentinfo.getalltypes());

        /* set table */
        this.apidtable.setCellValueFactory(new PropertyValueFactory("appointmentid"));
        this.aptabletitle.setCellValueFactory(new PropertyValueFactory("title"));
        this.aptabledescr.setCellValueFactory(new PropertyValueFactory("description"));
        this.aptableloc.setCellValueFactory(new PropertyValueFactory("location"));
        this.aptablecontact.setCellValueFactory(new PropertyValueFactory("contactid"));
        this.aptabletype.setCellValueFactory(new PropertyValueFactory("type"));
        this.aptablestart.setCellValueFactory(new PropertyValueFactory("start"));
        this.aptableend.setCellValueFactory(new PropertyValueFactory("end"));
        this.aptablecuid.setCellValueFactory(new PropertyValueFactory("customerid"));
        this.aptableuserid.setCellValueFactory(new PropertyValueFactory("userid"));


    }


    /** save appointments to database
     * @param actionEvent onClick
     * @throws SQLException using sql database */
    public void onsave(ActionEvent actionEvent) throws SQLException {

        /* get selections */
        String Title = this.aptitle.getText();
        String Description = this.apdescription.getText();
        String Location = this.aplocation.getText();
        LocalDateTime updateDate = LocalDateTime.now();
        String updateBy = jdbccommands.getloggedin().getusersname();


        /* get selections */
        times start1 = (times) apstart.getValue();
        times end1 = (times) apend.getValue();

            try {
                int Customer_ID = Integer.parseInt(this.apcustomer.getText());
                contacts contacts = (contacts) apCONTACT.getSelectionModel().getSelectedItem();
                int Contact_ID = contacts.getcontacts_ID();

                //  int User_ID = jdbccommands.getloggedin().getusers_ID();
                String type = (String) aptype.getSelectionModel().getSelectedItem();

                /* convert times */
                LocalTime start2 = start1.getTimes_name();
                LocalTime end2 = end1.getTimes_name();
                LocalDate date = this.apdate.getValue();
                ZoneId id = ZoneId.of("UTC");

                // get times
                LocalDateTime start = LocalDateTime.from(LocalDateTime.of(date, start2).atZone(id));
                LocalDateTime end = LocalDateTime.from(LocalDateTime.of(date, end2).atZone(id));

                    /* check date time selections */
                    if (jdbccommands.check_start_end_databse(start, end)) {
                    } else {


                        /* add to database */
                        jdbccommands.updateappointment(Title, Description, Location, type, start, end, updateDate, updateBy, Customer_ID, Contact_ID, transferid);

                        Alert alert = new Alert(AlertType.CONFIRMATION, "appointment updated", new ButtonType[0]);
                        Optional<ButtonType> added = alert.showAndWait();

                        /* clear list */
                        appointmentinfo.deleteappointments2();
                        /* repopulate list */
                        jdbccommands.selectallappointments();

                    }

                } catch (NumberFormatException | NullPointerException exception) {
                Alert alert = new Alert(AlertType.ERROR, "Either no customer number. You must choose from appointments. \nOr choose a date and time.", new ButtonType[0]);
                Optional<ButtonType> added = alert.showAndWait();
            }
            }


    /** delete appointments
     *
     * @param actionEvent  delete appointments
     * @throws SQLException  delete appointments
     */
    public void ondelete(ActionEvent actionEvent) throws SQLException {

        appointmentinfo selection = (appointmentinfo) this.aptable.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(AlertType.ERROR, " you must select something", new ButtonType[0]);
            Optional<ButtonType> update = alert.showAndWait();
        }

        else {

            Alert alert = new Alert(AlertType.CONFIRMATION, " are you sure", new ButtonType[0]);
            Optional<ButtonType> update = alert.showAndWait();

            /* confirm delete  */
            if (update.isPresent() && update.get() == ButtonType.OK) {
                if (jdbccommands.delete_appointments(selection.getAppointmentid())) {
                    appointmentinfo.deleteappointments(selection);
                    Alert alert2 = new Alert(AlertType.CONFIRMATION, "Appointment ID" + selection.getAppointmentid() +
                            " and Appointment type - " + selection.getType() + "  deleted", new ButtonType[0]);
                    Optional<ButtonType> delete2 = alert2.showAndWait();
                }
            }
            else {
                new Alert(AlertType.ERROR, " not deleted", new ButtonType[0]);
                Optional<ButtonType> delete2 = alert.showAndWait();
            }
        }
    }

    /** makes sure start time is correct / check for existing appointments
     *
     * @param actionEvent makes sure start time is correct / check for existing appointments
     */
    public void onstart(ActionEvent actionEvent) {

        LocalDate date = this.apdate.getValue();
        try {

            times start1 = (times) apstart.getSelectionModel().getSelectedItem();
            times end1 = (times) apend.getValue();
            LocalTime start2 = start1.getTimes_name();

            ZoneId id = ZoneId.of("UTC");
            LocalDateTime dates = LocalDateTime.from(LocalDateTime.of(date, start2).atZone(id));

            /*  check for existing appointments */
            if (jdbccommands.check_start_databse(dates, dates)) {}

            /*  check end time */
            if (end1.getTimes_name().isBefore(start1.getTimes_name())) {
                Alert alert = new Alert(AlertType.ERROR, "End time cannot be before start time", new ButtonType[0]);
                Optional<ButtonType> added = alert.showAndWait();
            }

            /*  check end time */
            if (end1.getTimes_name().isAfter(start1.getTimes_name().plusMinutes(61))) {
                Alert alert = new Alert(AlertType.ERROR, "timeframes can be no longer than 1 hr", new ButtonType[0]);
                Optional<ButtonType> added = alert.showAndWait();

            }
        }
        catch (NullPointerException | SQLException e) {}
    }


    /** makes sure end time is correct / check for existing appointments
     *
     * @param actionEvent  makes sure end time is correct / check for existing appointments
     */
    public void onend(ActionEvent actionEvent) {

        LocalDate date = this.apdate.getValue();

        try {

            times start1 = (times) apstart.getValue();
            times end1 = (times) apend.getValue();
            LocalTime end2 = end1.getTimes_name();

            ZoneId id = ZoneId.of("UTC");
            LocalDateTime dates2 = LocalDateTime.from(LocalDateTime.of(date, end2).atZone(id));

            /* check for existing appointments */
            if (jdbccommands.check_end_databse(dates2,dates2)) {}

            /*  check end time */
            if (end1.getTimes_name().isBefore(start1.getTimes_name())) {
                Alert alert = new Alert(AlertType.ERROR, "End time cannot be before start time", new ButtonType[0]);
                Optional<ButtonType> added = alert.showAndWait();
            }

            /*  check end time */
            if (end1.getTimes_name().isAfter(start1.getTimes_name().plusMinutes(61))) {
                Alert alert = new Alert(AlertType.ERROR, "timeframes can be no longer than 1 hr", new ButtonType[0]);
                Optional<ButtonType> added = alert.showAndWait();

            }

        } catch (NullPointerException | SQLException e) {}
    }

    /** makes sure date is  selected
     *
     * @param mouseEvent makes sure date is  selected
     */
    public void endtimeclick(MouseEvent mouseEvent) {

        LocalDate date = this.apdate.getValue();

        if (date == null) {
            Alert alert = new Alert(AlertType.ERROR, "you must pick a date first");
            Optional<ButtonType> datepick = alert.showAndWait();
        }
    }

    /** makes sure date is  selected
     *
     * @param mouseEvent  makes sure date is  selected
     */
    public void onstarttimeclick(MouseEvent mouseEvent) {

        LocalDate date = this.apdate.getValue();

        if (date == null) {
            Alert alert = new Alert(AlertType.ERROR, "you must pick a date first");
            Optional<ButtonType> datepick = alert.showAndWait();
        }

    }

    /** makes sure date is not outdated *
     *
     * @param actionEvent * makes sure date is not outdated *
     */
    public void ondate(ActionEvent actionEvent) {

        LocalDate date = this.apdate.getValue();

        /* checks chosen date to today's date */
        if(date.isBefore(LocalDate.now()))  {
            Alert alert = new Alert(Alert.AlertType.ERROR,"cannot be in the past");
            Optional<ButtonType> date2 = alert.showAndWait();
        }

        /* gets time list after picking date */
        if (!(date == null)) {
            apend.setItems(times.getAlltimesend());
            apstart.setItems(times.getAlltimesstart());
        }
    }

    /** transfer new appointment
     *
     * @param appointmenttransfer transfer new appointment to be updated
     */
    @FXML public void transferappointmentover(appointmentinfo appointmenttransfer) {

        appointmentinfo recordtoupdate = appointmenttransfer;

        transferid = recordtoupdate.getAppointmentid();
        aptitle.setText(recordtoupdate.getTitle());
        apdescription.setText(recordtoupdate.getDescription());
        aplocation.setText(recordtoupdate.getLocation());
        apcustomer.setText(String.valueOf(recordtoupdate.getCustomerid()));
        apdate.getValue();
        aptype.getSelectionModel().selectFirst();
        apCONTACT.getSelectionModel().selectFirst();
        apend.getSelectionModel().selectFirst();
        apstart.getSelectionModel().selectFirst();

    }

    /** leave page
     *
     * @param actionEvent leave page
     * @throws IOException leave page
     */
    public void oncancel(ActionEvent actionEvent) throws IOException {

        this.stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("3-customerrecords.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }

    /** goes to reports page
     *
     * @param actionEvent   goes to reports page
     * @throws IOException goes to reports page
     */
    public void onreports(ActionEvent actionEvent) throws IOException {
        this.stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("3-appointmentpage.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }

    /** UPDATE INFORMATION
     *
     * @param actionEvent  UPDATE INFORMATION
     * @throws IOException  UPDATE INFORMATION
     */
    public void onupdate(ActionEvent actionEvent) throws IOException {

        // object selected
        appointmentinfo selection = (appointmentinfo)  aptable.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(AlertType.ERROR, "you must pick an appointment first");
            Optional<ButtonType> updatepick = alert.showAndWait();
        }

        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("4-updateappointment.fxml"));
            loader.load();

            updateappointment updateappointment = loader.getController();
            updateappointment.transferupdateappointment(selection);

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** transfer appointment
     *
     * @param transfer  transfer appointment
     */
    public void transferupdateappointment (appointmentinfo transfer) {

        // appointment object
        appointmentinfo update = transfer;

        // to be transferred from the add appointment page
        transferid = transfer.getAppointmentid();
        aptitle.setText(transfer.getTitle());
        apdescription.setText(transfer.getDescription());
        aplocation.setText(transfer.getLocation());
        apcustomer.setText(String.valueOf(transfer.getCustomerid()));
        apdate.getValue();
        aptype.getSelectionModel().selectFirst();
        apCONTACT.getSelectionModel().selectFirst();
        apend.getSelectionModel().selectFirst();
        apstart.getSelectionModel().selectFirst();

    }
}

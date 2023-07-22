package isaiah.jdbc;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;
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

/** the classes to add appointments to the system */
public class addappointment implements Initializable {

    /** set stage for going to next page */
    Stage stage;
    Parent scene;

    public TextField date;

    /** input information */
    @FXML public TextField aptitle;
    @FXML public TextField apdescription;
    @FXML public TextField aplocation;
    @FXML public TextField apcustomer;


    @FXML public TableView<appointmentinfo> aptable;

    /** table columns */
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

    @FXML public DatePicker apdate;

    /** combo boxes */
    @FXML public ComboBox<String> aptype;
    @FXML public ComboBox<contacts> apCONTACT;
    @FXML public ComboBox<times> apend;
    @FXML public ComboBox<times> apstart;


    /** initialize page */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /** set date time */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String dates = LocalDateTime.now().format(dateTimeFormatter);
        date.setText(dates);

        /** fill boxes and table */
        try {

            appointmentinfo.deleteappointments2();
            appointmentinfo.deletealltypes();
            contacts.deletecontacts();

            jdbccommands.select_contacts();
            jdbccommands.selectallappointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /** set combo boxes */
        aptable.setItems(appointmentinfo.getAllappointments());
        apCONTACT.setItems(contacts.getallcontacts());
        aptype.setItems(appointmentinfo.getalltypes());

        /** set table */
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

      /** get selections */
      String Title = this.aptitle.getText();
      String Description = this.apdescription.getText();
      String Location = this.aplocation.getText();

      int Customer_ID = Integer.parseInt(this.apcustomer.getText());
      int User_ID = jdbccommands.getloggedin().getusers_ID();


      /* check empty slots */
      if (Title.isEmpty() || Description.isEmpty() || Location.isEmpty() ) {
          Alert alert = new Alert(AlertType.ERROR, "Name or address cannot be empty", new ButtonType[0]);
          Optional<ButtonType> added = alert.showAndWait();
      }
          else {

          try {

      /** get selections */
        times start1 = apstart.getValue();
        times end1 = apend.getValue();
        contacts contacts = apCONTACT.getSelectionModel().getSelectedItem();
        int Contact_ID = contacts.getcontacts_ID();
        String type = aptype.getSelectionModel().getSelectedItem();

        /** convert times */
        LocalTime start2 = start1.getTimes_name();
        LocalTime end2 = end1.getTimes_name();
        LocalDate date = this.apdate.getValue();
        ZoneId id = ZoneId.of("UTC");
        LocalDateTime start = LocalDateTime.from(LocalDateTime.of(date, start2).atZone(id));
        LocalDateTime end = LocalDateTime.from(LocalDateTime.of(date, end2).atZone(id));


          /** check date time selections */
          if (jdbccommands.check_start_end_databse(start, end)) { }

          else {
                        /* add to database */
                        jdbccommands.addappointment(Title, Description, Location, type, start, end, Customer_ID, Contact_ID, User_ID);

                        Alert alert = new Alert(AlertType.CONFIRMATION, "Appointment Added", new ButtonType[0]);
                        Optional<ButtonType> added = alert.showAndWait();

                        /* clear object list */
                        appointmentinfo.deleteappointments2();
                        /* repopulate list */
                        jdbccommands.selectallappointments();
                    }
                }
          catch(NumberFormatException | NullPointerException exception) {
              Alert alert = new Alert(AlertType.ERROR, " time and date, Contact, Session and anything else cannot be blank", new ButtonType[0]);
              Optional<ButtonType> added = alert.showAndWait();
                }
            }
      }


    /** update appointment
     *
     * @param actionEvent    update appointment
     * @throws IOException    update appointment
     */
    public void onupdate(ActionEvent actionEvent) throws IOException {

        /** get object */
        appointmentinfo selection = aptable.getSelectionModel().getSelectedItem();

        /** if object null */
        if (selection == null) {
            Alert alert = new Alert(AlertType.ERROR, " you must select something");
            Optional<ButtonType> update = alert.showAndWait();
        }

        else {
            /* transfer to update appointment */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("4-updateappointment.fxml"));
            loader.load();

            /** transfer to update appointment */
            updateappointment updatetheappointment = loader.getController();
            updatetheappointment.transferappointmentover(selection);

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    /** delete appointments
     *
     * @param actionEvent delete appointments
     * @throws SQLException delete appointments
     */
    public void ondelete(ActionEvent actionEvent) throws SQLException {

        /** get object */
        appointmentinfo selection = this.aptable.getSelectionModel().getSelectedItem();

        /** if object  null */
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
                    Alert alert2 = new Alert(AlertType.CONFIRMATION, "Appointment ID " + selection.getAppointmentid() +
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

    /** transfer over customer number
     *
     * @param selection transfer over customer number
     */
    public void transferovercustomer(customerinfo selection) {

        /** get customer object */
        customerinfo customerid = selection;
        this.apcustomer.setText(String.valueOf(selection.getId()));
    }

    /** makes sure start time is correct / check for existing appointments
     *
     * @param actionEvent makes sure start time is correct / check for existing appointments
     */
    public void onstart(ActionEvent actionEvent) {

        /**  get date value */
        LocalDate date = this.apdate.getValue();
        try {

            /** get time start and end values */
            times start1 = apstart.getSelectionModel().getSelectedItem();
            times end1 = apend.getValue();
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
     * @param actionEvent makes sure end time is correct / check for existing appointments
     */
    public void onend(ActionEvent actionEvent) {

        /** get date value */
        LocalDate date = this.apdate.getValue();

        try {

            /** get start and end time values */
            times start1 = apstart.getValue();
            times end1 = apend.getValue();
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

        /** get date */
        LocalDate date = this.apdate.getValue();

        if (date == null) {
            Alert alert = new Alert(AlertType.ERROR, "you must pick a date first");
            Optional<ButtonType> datepick = alert.showAndWait();
        }
    }

    /**
     *
     * @param mouseEvent makes sure date is  selected
     */
    public void onstarttimeclick(MouseEvent mouseEvent) {

        /** get date */
        LocalDate date = this.apdate.getValue();

        if (date == null) {
            Alert alert = new Alert(AlertType.ERROR, "you must pick a date first");
            Optional<ButtonType> datepick = alert.showAndWait();
        }

    }

    /** makes sure date is not outdated
     *
     * @param actionEvent makes sure date is not outdated
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

    /** goes to appointment page
     *
     * @param actionEvent  goes to appointment page
     * @throws IOException  goes to appointment page
     */
    public void onreports(ActionEvent actionEvent) throws IOException {
        this.stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("3-appointmentpage.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }



}

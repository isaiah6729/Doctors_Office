package isaiah.jdbc;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *   this class implements showing reports
 */
public class appointmentpage implements Initializable {

    Stage stage;
    Parent scene;

    public Label time;

    public Button customerreport;

    public TableView appointmenttable;
    public TableView appointmenttable2;
    public TableView appointmenttable21;

    public DatePicker date2;
    public DatePicker date1;

    public TableColumn datetime;
    public TableColumn type;

    public TableColumn contact;

    public TableColumn id;
    public TableColumn title;
    public TableColumn type2;
    public TableColumn desc;
    public TableColumn start;
    public TableColumn end;
    public TableColumn cuid;

    public TableColumn id1;
    public TableColumn contact1;
    public TableColumn type21;
    public TableColumn title1;
    public TableColumn desc1;
    public TableColumn start1;
    public TableColumn end1;
    public TableColumn cuid1;

    public TableColumn id11;
    public TableColumn contact11;
    public TableColumn type211;
    public TableColumn title11;
    public TableColumn desc11;
    public TableColumn start11;
    public TableColumn end11;
    public TableColumn cuid11;

    public TextField date;
    public TextField customerresults;
    public TextField typemonthresults1;

    public ComboBox monthbox;
    public ComboBox reportbox;
    public ComboBox reportmonths;
    public ComboBox typereport1;
    public ComboBox customermonthbox;

    /** initalizes page
     *
     * @param url  initalizes page
     * @param resourceBundle initalizes page
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String localDateTime = LocalDateTime.now().format(dateTimeFormatter);
        date.setText(localDateTime);

        try {

            // delete objects to refill them
            reports.deleteallreports();
            months.deleteallmonths();
            contacts.deletecontacts();
            appointmentinfo.deletealltypes();
            jdbccommands.select_contacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        reports.addreports();
        months.addmonths();
        reportmonths.setItems(months.getallmonths());
        reportbox.setItems(reports.getAllreports());
        customermonthbox.setItems(months.getallmonths());
        monthbox.setItems(months.getallmonths());

        contact.setCellValueFactory(new PropertyValueFactory<>("contactid"));
        id.setCellValueFactory(new PropertyValueFactory<>("appointmentid"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type2.setCellValueFactory(new PropertyValueFactory<>("type"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        cuid.setCellValueFactory(new PropertyValueFactory<>("customerid"));

        contact1.setCellValueFactory(new PropertyValueFactory<>("contactid"));
        id1.setCellValueFactory(new PropertyValueFactory<>("appointmentid"));
        title1.setCellValueFactory(new PropertyValueFactory<>("title"));
        type21.setCellValueFactory(new PropertyValueFactory<>("type"));
        desc1.setCellValueFactory(new PropertyValueFactory<>("description"));
        start1.setCellValueFactory(new PropertyValueFactory<>("start"));
        end1.setCellValueFactory(new PropertyValueFactory<>("end"));
        cuid1.setCellValueFactory(new PropertyValueFactory<>("customerid"));

        contact11.setCellValueFactory(new PropertyValueFactory<>("contactid"));
        id11.setCellValueFactory(new PropertyValueFactory<>("appointmentid"));
        title11.setCellValueFactory(new PropertyValueFactory<>("title"));
        type211.setCellValueFactory(new PropertyValueFactory<>("type"));
        desc11.setCellValueFactory(new PropertyValueFactory<>("description"));
        start11.setCellValueFactory(new PropertyValueFactory<>("start"));
        end11.setCellValueFactory(new PropertyValueFactory<>("end"));
        cuid11.setCellValueFactory(new PropertyValueFactory<>("customerid"));
    }

    /** check monthly dates
     *
     * @param actionEvent  check monthly dates
     * @throws SQLException  check monthly dates
     */
    public void onmonthbox(ActionEvent actionEvent) throws SQLException {

        try {
            months month = (months) monthbox.getValue();
            int year = LocalDate.now().getYear();
            int lastday = LocalDate.of(year, month.getMonths_ID(), 1).lengthOfMonth();

            appointmentinfo.deletealldatetimes();

            LocalDateTime dateTime1 = LocalDateTime.of(year, month.getMonths_ID(), 1, 0, 0);
            LocalDateTime dateTime2 = LocalDateTime.of(year, month.getMonths_ID(), lastday, 10, 0);

            /* check monthly database  */
            jdbccommands.select_monthlyappointments(dateTime1, dateTime2);
            appointmenttable.setItems(appointmentinfo.getAlldatetimes());
        }
        catch (NullPointerException exception) {}

    }

    /** check weekly dates
     *
     * @param actionEvent check weekly dates
     * @throws SQLException check weekly dates
     */
    public void getweeklydates(ActionEvent actionEvent) throws SQLException {

        LocalDate dates1 = date1.getValue();
        LocalDate dates2 = date2.getValue();
        LocalTime notime = LocalTime.of(0, 0);

        appointmentinfo.deletealldatetimes2();
try {
    /* check weekly database  */
    if (dates1.isBefore(LocalDate.now()) || dates2.isBefore(LocalDate.now())) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "cannot be in the past");
        Optional<ButtonType> date2 = alert.showAndWait();
    } else {
        if (dates2.isBefore(dates1)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "End date cannot be before start date", new ButtonType[0]);
            Optional<ButtonType> added = alert.showAndWait();
        } else {

            /* check weekly database  */
            jdbccommands.select_weeklyappointments(LocalDateTime.of(dates1, notime), LocalDateTime.of(dates2, notime));
            appointmenttable2.setItems(appointmentinfo.getAlldatetimes2());
        }
    }
} catch (NullPointerException exception) {
    Alert alert = new Alert(Alert.AlertType.ERROR, "choose a report");
    Optional<ButtonType> alerting = alert.showAndWait();
}
    }

    /** check weekly dates error
     *
     * @param actionEvent check weekly dates error
     * @throws SQLException check weekly dates error
     */
    public void ondate2(ActionEvent actionEvent) throws SQLException {

        LocalDate dates1 = date1.getValue();
        LocalDate dates2 = date2.getValue();

        if (dates1.isBefore(LocalDate.now()) || dates2.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "cannot be in the past");
            Optional<ButtonType> date2 = alert.showAndWait();
        } else {
            if (dates2.isBefore(dates1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "End date cannot be before start date", new ButtonType[0]);
                Optional<ButtonType> added = alert.showAndWait();
            }
        }
    }

    /** check weekly dates error
     *
      * @param actionEvent check weekly dates error
     */
    public void ondate1(ActionEvent actionEvent) {

        LocalDate dates1 = date1.getValue();
        LocalDate dates2 = date2.getValue();

        if (dates1.isBefore(LocalDate.now()) || dates2.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "cannot be in the past");
            Optional<ButtonType> date2 = alert.showAndWait();
        } else {
            if (dates2.isBefore(dates1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "End date cannot be before start date", new ButtonType[0]);
                Optional<ButtonType> added = alert.showAndWait();
            }
        }
    }

    /** check weekly dates error
     *
     * @param actionEvent check weekly dates error
     * @throws SQLException check weekly dates error
     */
    public void onreportbutton(ActionEvent actionEvent) throws SQLException {

        reports report = (reports) reportbox.getValue();
        months month = (months) reportmonths.getValue();

        /** lambda  1 */
        /** this lambda chooses the type of reports from the object, either contact schedule or type/month.
         * this id # is then used to bring up the correct object (user interface selection). using this lamba
         * as a boolean makes it easier and quicker to get the right object to be used.
         */
        reports.makingreport anotherreport = x -> x == 1;
try {
    /* choose first report */
    if (anotherreport.report(report.getReports_ID())) {

        String type = (String) typereport1.getValue();

        int month1 = month.getMonths_ID();
        int year = LocalDate.now().getYear();
        int lastday = LocalDate.of(year, month1, 1).lengthOfMonth();

        LocalDateTime dateTime1 = LocalDateTime.of(year, month1, 1, 0, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(year, month1, lastday, 10, 0);

        /* generate type and month count reports */
        int results = jdbccommands.select_type_month_count(type, dateTime1, dateTime2);

        typemonthresults1.setText(type + " & " + month + " = " + results + " for the month");
    }

    /** lambda  2 */
    /** this lambda chooses the type of reports from the object, either contact schedule or type/month.
     * this id # is then used to bring up the correct object (user interface selection). using this lamba
     * as a boolean makes it easier and quicker to get the right object to be used.
     */
    reports.makingreport anotherreport2 = x -> x == 2;

    /* choose 2nd report */
    if (anotherreport2.report(report.getReports_ID())) {

        contacts contact = (contacts) typereport1.getValue();

        int month1 = month.getMonths_ID();
        int year = LocalDate.now().getYear();
        int lastday = LocalDate.of(year, month1, 1).lengthOfMonth();
        LocalDateTime dateTime1 = LocalDateTime.of(year, month1, 1, 0, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(year, month1, lastday, 10, 0);

        appointmentinfo.deletecontactReport();
        /* get contact schedule report */
        jdbccommands.select_contact_appointment(contact.getcontacts_ID(), dateTime1, dateTime2);
        appointmenttable21.setItems(appointmentinfo.getallallcontacts_report());
    }
        } catch (NullPointerException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "choose a report");
                    Optional<ButtonType> alerting = alert.showAndWait();
                }
    }

    /** generate the amount of customers added to database report
     *
     * @param actionEvent generate the amount of customers added to database report
     * @throws SQLException generate the amount of customers added to database report
     */
    public void customerreport(ActionEvent actionEvent) throws SQLException {

        months month2 = (months) customermonthbox.getSelectionModel().getSelectedItem();

        try {
            /* get variables */
            int month1 = month2.getMonths_ID();
            int year = LocalDate.now().getYear();
            int lastday = LocalDate.of(year, month1, 1).lengthOfMonth();

            /* set variables to make Localdatetime */
            LocalDateTime dateTime1 = LocalDateTime.of(year, month1, 1, 0, 0);
            LocalDateTime dateTime2 = LocalDateTime.of(year, month1, lastday, 10, 0);

            /* check database of customer count */
            int results = jdbccommands.select_customer_amount(dateTime1, dateTime2);

            customerresults.setText("There were " + results + " added in " + month2.getMonth_name());
        } catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "choose a report");
            Optional<ButtonType> alerting = alert.showAndWait();
        }
    }

    /** generate the amount of customers added to database report
     *
     * @param actionEvent generate the amount of customers added to database report
     */
    public void onreportbox(ActionEvent actionEvent) {

        /* lambda  1 */
      /**  this lambda chooses the type of reports from the object, either contact schedule or type/month.
                * this id # is then used to bring up the correct object (user interface selection). using this lamba
                * as a boolean makes it easier and quicker to get the right object to be used. */
        reports.makingreport anotherreport = x -> x == 1;

        reports report = (reports) reportbox.getValue();

        /* set first box */
        if (anotherreport.report(report.getReports_ID())) {

            typereport1.setPromptText("Choose a type");
            typereport1.setItems(appointmentinfo.getalltypes());
        }

        /* lambda  2 */
        /**  this lambda chooses the type of reports from the object, either contact schedule or type/month.
         * this id # is then used to bring up the correct object (user interface selection). using this lamba
         * as a boolean makes it easier and quicker to get the right object to be used. */
        reports.makingreport anotherreport2 = x -> x == 2;

        /* set 2nd box */
        if (anotherreport2.report(report.getReports_ID())) {
            appointmentinfo.deletealltypes();
            typereport1.setPromptText("Choose a contact");
            typereport1.setItems(contacts.getallcontacts());
        }
    }

    /** generate a report alert
     *
     * @param mouseEvent generate a report alert
     */
    public void typereportclick1(MouseEvent mouseEvent) {

        reports report = (reports) reportbox.getValue();

        if (report == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must choose a report first!", new ButtonType[0]);
            Optional<ButtonType> reports = alert.showAndWait();
        }
    }

    /** go back to customer records
     *
     * @param actionEvent go back to customer records
     * @throws IOException go back to customer records
     */
    public void gobackcustomers(ActionEvent actionEvent) throws IOException {
        this.stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("3-customerrecords.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }

    /** go back to update appointment
     *
     * @param actionEvent go back to update appointment
     * @throws IOException go back to update appointment
     */
    public void gobackAppointment(ActionEvent actionEvent) throws IOException {
        this.stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("4-updateappointment.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }
}





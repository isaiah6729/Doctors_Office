package isaiah.jdbc;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** this page is where you add delete and update customers
 *
 */
public class customerrecordspage implements Initializable {

    Stage stage;
    Parent scene;

    @FXML TableView<customerinfo> crtable;

    @FXML public TextField date;
    public TextField customerresults;

    @FXML TableColumn crtableid;
    @FXML TableColumn crtablename;
    @FXML TableColumn crtablephonenumber;
    @FXML TableColumn crtablepostalcode;
    @FXML TableColumn crtableaddress;
    @FXML TableColumn crtablestate;
    @FXML TableColumn crtablecreatedate;
    @FXML TableColumn crtablecreateby;
    @FXML TableColumn crtableupdated;
    @FXML TableColumn crtableupdateby;


    /** initialize
     *
     * @param url initialize
     * @param resourceBundle initialize
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* set date time */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String dates = LocalDateTime.now().format(dateTimeFormatter);
        date.setText(dates);

        try {

            // delete object to reset the object information
            customerinfo.deletecustomers();
            /*set table */
            this.crtable.setItems(jdbccommands.selectallcustomers());

            /* get variables */
            int month1 = LocalDate.now().getMonthValue();
            int year = LocalDate.now().getYear();
            Month monthname = LocalDate.of(year, month1,1).getMonth();
            int lastday = LocalDate.of(year, month1, 1).lengthOfMonth();

            /* set variables */
            LocalDateTime dateTime1 = LocalDateTime.of(2021, month1, 1, 0, 0);
            LocalDateTime dateTime2 = LocalDateTime.of(2023, month1, lastday, 10, 0);

            /* get customer tally monthly */
            int results = jdbccommands.select_customer_amount(dateTime1,dateTime2);

            /* display results according to last day or not */
            if (!(LocalDate.now().getDayOfMonth() == lastday)) {
                customerresults.setText("There have been " + results + " customers added in " + monthname + " so far.");
            }
            else {
                customerresults.setText("There was " + results + " customers added in " + monthname);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        this.crtableid.setCellValueFactory(new PropertyValueFactory("id"));
        this.crtablename.setCellValueFactory(new PropertyValueFactory("name"));
        this.crtableaddress.setCellValueFactory(new PropertyValueFactory("address"));
        this.crtablepostalcode.setCellValueFactory(new PropertyValueFactory("postalcode"));
        this.crtablephonenumber.setCellValueFactory(new PropertyValueFactory("phonenumber"));
        this.crtablecreatedate.setCellValueFactory(new PropertyValueFactory("createdate"));
        this.crtablecreateby.setCellValueFactory(new PropertyValueFactory("createdby"));
        this.crtableupdated.setCellValueFactory(new PropertyValueFactory("lastupdated"));
        this.crtableupdateby.setCellValueFactory(new PropertyValueFactory("lastupdatedby"));
        this.crtablestate.setCellValueFactory(new PropertyValueFactory("divisionsid"));

    }

    /** add appointment
     *
     * @param actionEvent add appointment
     * @throws IOException add appointment
     */
    public void onadd(ActionEvent actionEvent) throws IOException {
        this.stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        this.scene = (Parent)FXMLLoader.load(this.getClass().getResource("2-addcustomer.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }

    /** update appointment
     *
     * @param actionEvent update appointment
     * @throws IOException update appointment
     */
    public void onupdate(ActionEvent actionEvent) throws IOException {

        customerinfo selection = crtable.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(AlertType.ERROR, " you must select something");
            Optional<ButtonType> update = alert.showAndWait();
        }

        /* transfer customer */
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("2-updatecustomer.fxml"));
            loader.load();

            updatecustomer update = loader.getController();
            update.transfercustomerover(selection);

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** deletes appointment
     *
     * @param actionEvent deletes appointment
     * @throws SQLException deletes appointment
     */
    public void ondelete(ActionEvent actionEvent) throws SQLException {

        customerinfo selection = this.crtable.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(AlertType.ERROR, " you must select something", new ButtonType[0]);
            Optional<ButtonType> update = alert.showAndWait();
        }
        else {

            /* checks to see if customer has appointments */
            if (jdbccommands.select_customer_with_appointments(selection.getId())) {
                Alert alert = new Alert(AlertType.ERROR, " delete appointments", new ButtonType[0]);
                Optional<ButtonType> update = alert.showAndWait();
            }

            else {
                Alert alert = new Alert(AlertType.CONFIRMATION, " are you sure", new ButtonType[0]);
                Optional<ButtonType> update = alert.showAndWait();

                /* confirms */
                if (update.isPresent() && update.get() == ButtonType.OK) {
                    if (jdbccommands.delete_customers(selection.getId()) == true) {
                        customerinfo.deletecustomer(selection);
                        Alert alert2 = new Alert(AlertType.CONFIRMATION, " customer deleted", new ButtonType[0]);
                        Optional<ButtonType> delete2 = alert2.showAndWait();
                    }
                } else {
                    new Alert(AlertType.ERROR, " not deleted", new ButtonType[0]);
                    Optional<ButtonType> delete2 = alert.showAndWait();
                }
            }
        }
    }

    /** sign out
     *
     * @param actionEvent sign out
     * @throws IOException sign out
     */
    public void onsignout(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, " are you sure", new ButtonType[0]);
        Optional<ButtonType> update = alert.showAndWait();

        if (update.isPresent() && update.get() == ButtonType.OK) {

            this.stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            this.scene = (Parent)FXMLLoader.load(this.getClass().getResource("1-login.fxml"));
            this.stage.setScene(new Scene(this.scene));
            this.stage.show();
        }

    }

    /** go to appointments
     *
     * @param actionEvent  go to appointments
     * @throws IOException  go to appointments
     */
    public void appointmentbutton(ActionEvent actionEvent) throws IOException {

        customerinfo selection = (customerinfo)this.crtable.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(AlertType.ERROR, " you must select something", new ButtonType[0]);
            Optional var4 = alert.showAndWait();
        }
        else {

            /* transfer customer to appointment page */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("4-addappointment.fxml"));
            loader.load();

            addappointment add = (addappointment)loader.getController();
            add.transferovercustomer(selection);

            this.stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = (Parent)loader.getRoot();
            this.stage.setScene(new Scene(scene));
            this.stage.show();
        }
    }

    /** goes to reports page
     *
     * @param actionEvent goes to reports page
     * @throws IOException goes to reports page
     */
    public void onreports (ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("3-appointmentpage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

}

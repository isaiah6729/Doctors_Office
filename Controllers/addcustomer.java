package isaiah.jdbc;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *   this class implements adding customers
 */
public class addcustomer implements Initializable {

    Stage stage;
    Parent scene;

    @FXML public TextField date;

    @FXML public TextField crname;
    @FXML public TextField craddress;
    @FXML public TextField crpostalcode;
    @FXML public TextField crphonenumber;

    @FXML public ComboBox country;
    @FXML public ComboBox stateprovince;


    /** initalizes page
     *
     * @param url initialize page
     * @param resourceBundle initialize page
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* set date time */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String dates = LocalDateTime.now().format(dateTimeFormatter);
        date.setText(dates);

        try {
            /* set country box */
            countries.deletecountries();
            jdbccommands.select_countries();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        /* set country box */
        this.country.setItems(countries.getallcountries());

    }

    /** save customer to database
     *
     * @param actionEvent save customer to database
     * @throws IOException save customer to database
     */
    public void onsave(ActionEvent actionEvent) throws IOException {


        // get the commands object to set the user ID
        jdbccommands jdbccommands = null;

        divisions division = (divisions) this.stateprovince.getSelectionModel().getSelectedItem();

        String recordname = this.crname.getText();
        String recordaddress = this.craddress.getText();
        String postalcode = this.crpostalcode.getText();
        String recordphonenumber = this.crphonenumber.getText();
        LocalDateTime recordCreate_Date = LocalDateTime.now();
        String recordCreated_By = jdbccommands.getloggedin().getusersname();
        LocalDateTime recordLast_Update = LocalDateTime.now();
        String recordLast_Updated_By = recordCreated_By;


        /* if empty */
        if (recordname.isEmpty() || recordaddress.isEmpty() || postalcode.isEmpty() || recordphonenumber.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Name or address cannot be empty, Postal code or Phone number cannot be empty");
            Optional<ButtonType> added = alert.showAndWait();
        } else {

            try {

                int recorddivisions_ID = division.getdivisions_ID();

                /* add to database */
                if (jdbccommands.addcustomer(recordname, recordaddress, postalcode, recordphonenumber, recordCreate_Date,
                        recordCreated_By, recordLast_Update, recordLast_Updated_By, recorddivisions_ID)) {

                    Alert alert = new Alert(AlertType.CONFIRMATION, "Customer Added");
                    Optional<ButtonType> added = alert.showAndWait();

                    // go to next page
                    this.stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    this.scene = FXMLLoader.load(this.getClass().getResource("3-customerrecords.fxml"));
                    this.stage.setScene(new Scene(this.scene));
                    this.stage.show();
                } else {
                    Alert alert = new Alert(AlertType.CONFIRMATION, "Customer not Added");
                    Optional<ButtonType> added = alert.showAndWait();
                }

            } catch (NumberFormatException | NullPointerException exception) {
                Alert alert = new Alert(AlertType.ERROR, "Nothing can be blank");
                Optional<ButtonType> added = alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /** exit page
     *
     * @param actionEvent exit page
     * @throws IOException exit page
     */
    public void oncancel(ActionEvent actionEvent) throws IOException {
        this.stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        this.scene = (Parent)FXMLLoader.load(getClass().getResource("3-customerrecords.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }

    /** choose a country / division
     *
     * @param actionEvent choose a country / division
     * @throws SQLException choose a country / division
     */
    public void oncountry(ActionEvent actionEvent) throws SQLException {

        try {
            // get country selection
            countries country1 = (countries) country.getSelectionModel().getSelectedItem();

            /* clear division */
            divisions.deletedivisions();

            /* repopulate division */
            jdbccommands.select_divisions(country1.getCountry_ID());

            stateprovince.setItems(divisions.getalldivisions());
        }
        catch (NullPointerException exception) {}
    }

    /** pick country alert
     *
     * @param mouseEvent pick country alert
     */
    public void onstateclick(MouseEvent mouseEvent) {

        countries country1 = (countries) country.getSelectionModel().getSelectedItem();

        if (country1 == null) {
            Alert alert = new Alert(AlertType.ERROR, "must pick a country first");
            Optional<ButtonType> added = alert.showAndWait();
        }

    }
}


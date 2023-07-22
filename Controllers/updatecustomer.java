package isaiah.jdbc;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 *
 * this page updates the customer information
 *
 * */
public class updatecustomer implements Initializable {

    Stage stage;
    Parent scene;

    public TextField date;

    public TextField updateid;
    public TextField updatename;
    public TextField updateaddress;
    public TextField updatepostalcode;
    public TextField updatephonenumber;

    public ComboBox updatecountry;
    public ComboBox updatestate;

    /** initialize page
     *
     * @param url initialize page
     * @param resourceBundle initialize page
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* set date time */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String dates = ZonedDateTime.now().format(dateTimeFormatter);
        date.setText(dates);

        /* set country box */
        try {
            countries.deletecountries();
            jdbccommands.select_countries();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* set country box */
        this.updatecountry.setItems(countries.getallcountries());
    }

    /** save customer
     *
     * @param actionEvent save customer
     * @throws IOException goes to next page
     */
    public void onsave(ActionEvent actionEvent) throws IOException {

        // setting commands object to be used for userID
        jdbccommands jdbccommands = null;

        divisions updatedivision = (divisions)this.updatestate.getSelectionModel().getSelectedItem();

        // getting input
        Integer id = Integer.parseInt(this.updateid.getText());
        String name = this.updatename.getText();
        String address = this.updateaddress.getText();
        String postalcode = updatepostalcode.getText();
        String phonenumber = updatephonenumber.getText();
        LocalDateTime updatedate = LocalDateTime.now();

        // used to get the logged in user id
        String updateLast_Updated_By = jdbccommands.getloggedin().getusersname();



        try {

            Integer updatedivisions_ID = updatedivision.getdivisions_ID();

            /*  check if slots empty */
            if (name.isEmpty() || address.isEmpty() || postalcode.isEmpty() || phonenumber.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR, "Name or address cannot be empty");
                Optional<ButtonType> update = alert.showAndWait();
            }

            else {

                /* update customer */
                if (jdbccommands.updatecustomer(id, name, address, postalcode, phonenumber, updatedate, updateLast_Updated_By,
                        updatedivisions_ID)) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer record updated successfully");
                    Optional<ButtonType> update = alert.showAndWait();

                    /* clear countries */
                    countries.deletecountries();

                    /* go to next page */
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("3-customerrecords.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "not correct");
                    Optional<ButtonType> update = alert.showAndWait();
                }
            }
        }
        catch (NumberFormatException | NullPointerException | SQLException exception) {
            Alert alert = new Alert(AlertType.ERROR, "Nothing can be blank");
            Optional<ButtonType> added = alert.showAndWait();
        }
    }

    /** leave page
     *
     * @param actionEvent leave page
     * @throws IOException leave page
     */
    public void oncancel(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(this.getClass().getResource("3-customerrecords.fxml"));
        stage.setScene(new Scene(this.scene));
        stage.show();
    }

    /** transfer customer
     *
     * @param customertransfer transfer customer
     */
    @FXML public void transfercustomerover(customerinfo customertransfer) {

        // customer object being sent over
        customerinfo recordtoupdate = customertransfer;

        //gathering customer information
        updateid.setText(String.valueOf(recordtoupdate.getId()));
        this.updatename.setText(recordtoupdate.getName());
        this.updateaddress.setText(recordtoupdate.getAddress());
        this.updatepostalcode.setText(recordtoupdate.getPostalcode());
        this.updatephonenumber.setText(recordtoupdate.getPhonenumber());
        this.updatestate.getSelectionModel().selectFirst();
        this.updatecountry.getSelectionModel().selectFirst();
    }

    /** selection country divison
     *
     * @param actionEvent selection country divison
     * @throws SQLException selection country divison
     */
    public void oncountry(ActionEvent actionEvent) throws SQLException {

        countries country1 = (countries) updatecountry.getSelectionModel().getSelectedItem();

        divisions.deletedivisions();

        jdbccommands.select_divisions(country1.getCountry_ID());

        this.updatestate.setItems(divisions.getalldivisions());
    }
}

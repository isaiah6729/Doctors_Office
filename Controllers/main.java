package isaiah.jdbc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** start with login
 *
 */
public class main extends Application {

    /** start with login
     *
     * @param stage start with login
     * @throws IOException start with login
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("1-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1320, 740);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /** main
     *
     * @param args main
     * @throws SQLException main
     * @throws ClassNotFoundException main
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // open connection
        jdbc.openconnection();

       launch();

                // close connection after launching
                jdbc.closeconnection();
    }
}
package gui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class AdminGUI {
    public AdminGUI() throws Exception {
        start();
    }

    public void start() throws Exception {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//            stage.getIcons().add(new Image("bin/icon.png"));
            alert.setTitle("Blad");
            alert.setContentText("Problem z polaczeniem sieciowym!");
            alert.showAndWait();


    }
}

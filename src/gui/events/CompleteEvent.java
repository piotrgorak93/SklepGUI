package gui.events;

import engine.Item;
import javafx.scene.control.Alert;

import java.util.ArrayList;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class CompleteEvent {
    public CompleteEvent(ArrayList<Item> userBucket) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Kupi�es przedmioty " + userBucket);
        alert.showAndWait();
    }
}

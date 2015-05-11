package gui.events;

import engine.Item;
import javafx.scene.control.Alert;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class RemoveEvent {
    public RemoveEvent(Item item) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Usunieto z bazy " + item);

        alert.showAndWait();
    }
}

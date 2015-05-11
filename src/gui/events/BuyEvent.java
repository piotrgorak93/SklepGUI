package gui.events;

import engine.Item;
import javafx.scene.control.Alert;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class BuyEvent {
    public BuyEvent(Item item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Dodano do koszyka przedmiot: " + item);

        alert.showAndWait();
    }
}

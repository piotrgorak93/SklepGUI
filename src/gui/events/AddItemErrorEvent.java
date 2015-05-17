package gui.events;

import javafx.scene.control.Alert;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class AddItemErrorEvent {
    public AddItemErrorEvent() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Wypelnij wszystkie pola");
        alert.showAndWait();
    }
}

package gui.events;

import gui.LocalItem;
import javafx.scene.control.Alert;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class AddItemEvent {
    public AddItemEvent(LocalItem item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Dodano przedmiot " + item +", kliknij Zapisz, by zaktualizowac baze danych");
        alert.showAndWait();
    }
}

package gui.events;

import javafx.scene.control.Alert;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-17.
 */
public class DatabaseSavedEvent {
    public DatabaseSavedEvent() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Baza danych zostala zaktualizowana");
        alert.showAndWait();
    }
}

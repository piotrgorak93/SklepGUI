package gui.events;

import javafx.scene.control.Alert;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class NothingFound {
    public NothingFound(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Nic nie znaleziono");

        alert.showAndWait();
    }
}

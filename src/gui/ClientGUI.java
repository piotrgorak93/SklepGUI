package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class ClientGUI {
    @FXML
    ListView<String> itemList;


    @FXML
    private void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "Single", "Double", "Suite", "Family App");
        itemList.setItems(items);

    }

}

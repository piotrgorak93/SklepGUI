package gui;

import engine.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import rmi.rmiTestClient.MeetingClient;
import rmi.rmiTestMeeting.IMeeting;

import java.rmi.RemoteException;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class AdminGUI {
    public TableView productTable;
    IMeeting meeting;
    private ObservableList<Item> itemsOnList;
    @FXML
    private ListView<Item> itemListAdmin;
    private Item selectedItem;

    @FXML
    private void initialize() {
//        ObservableList<Item> itemsInTable = FXCollections.observableArrayList(new Item("ASD","ASD","ASD",20.0,5));
//        productName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
//        productTable.setItems(itemsInTable);
        meeting = new MeetingClient().connectToServer();
        createList();
        //Image im = new Image("file:1.png");
        //System.out.println(im);

        // image = new ImageView(im);
        itemListAdmin.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItem = itemListAdmin.getSelectionModel().getSelectedItem();
                    printResult();
                });
    }

    private void printResult() {
    }

    public void createList() {

        System.out.println(meeting);
        try {
            itemsOnList = FXCollections.observableArrayList(
                    meeting.getAllItems());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        itemListAdmin.setItems(itemsOnList);
    }

    public void searchHandler(Event event) {
    }

    public void searchByCategoryHandler(Event event) {

    }

    public void searchByDescriptionHandler(Event event) {

    }
}

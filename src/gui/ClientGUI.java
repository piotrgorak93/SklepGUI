package gui;

import engine.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import rmi.rmiTestClient.MeetingClient;
import rmi.rmiTestMeeting.IMeeting;

import java.rmi.RemoteException;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class ClientGUI {
    @FXML
    public ListView<Item> itemList;
    public javafx.scene.control.TableView<Item> productTable;
    public TableColumn<Item, Number> productID;
    public TableColumn<Item, String> productName;
    public TableColumn<Item, String> productCategory;
    public TableColumn<Item, String> productDescription;
    public TableColumn<Item, Number> productPrice;
    public TableColumn<Item, Number> productQuantity;


    IMeeting meeting;
    Item selectedItem;


    @FXML
    private void initialize() {
//        ObservableList<Item> ob = FXCollections.observableArrayList(new Item("ASD","ASD","ASD",20.0,5));
//        productName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
//        productTable.setItems(ob);
        meeting = new MeetingClient().connectToServer();
        createList();
        //Image im = new Image("file:1.png");
        //System.out.println(im);

        // image = new ImageView(im);
        itemList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItem = itemList.getSelectionModel().getSelectedItem();
                    printResult();
                });
    }

    public void createList() {
        ObservableList<Item> items = null;
        try {
            items = FXCollections.observableArrayList(meeting.getAllItems());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        itemList.setItems(items);
    }

    public void printResult() {
        ObservableList<Item> ob = FXCollections.observableArrayList(new Item(selectedItem.getName(), selectedItem.getCategory(),
                selectedItem.getDescription(), selectedItem.getPrice(), selectedItem.getQuantity(), selectedItem.getId()));

        productID.setCellValueFactory(cellData -> cellData.getValue().idProperty);
        productName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
        productCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty);
        productDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty);
        productPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty);
        productQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty);
        productTable.setItems(ob);
        productTable.getItems().forEach(System.out::println);
    }


}

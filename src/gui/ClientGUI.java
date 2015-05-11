package gui;

import engine.Item;
import gui.events.BuyEvent;
import gui.events.NothingFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import rmi.rmiTestClient.MeetingClient;
import rmi.rmiTestMeeting.IMeeting;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class ClientGUI {
    @FXML
    public ListView<Item> itemList;
    public javafx.scene.control.TableView<LocalItem> productTable;
    public TableColumn<LocalItem, Number> productID;
    public TableColumn<LocalItem, String> productName;
    public TableColumn<LocalItem, String> productCategory;
    public TableColumn<LocalItem, String> productDescription;
    public TableColumn<LocalItem, Number> productPrice;
    public TableColumn<LocalItem, Number> productQuantity;

    public TableView<LocalItem> foundProductTable;
    public TableColumn<LocalItem, Number> foundProductID;
    public TableColumn<LocalItem, String> foundProductName;
    public TableColumn<LocalItem, String> foundProductCategory;
    public TableColumn<LocalItem, String> foundProductDescription;
    public TableColumn<LocalItem, Number> foundProductPrice;
    public TableColumn<LocalItem, Number> foundProductQuantity;
    public TextField searchDescripionBox;
    public TextField searchCategoryBox;
    public TextField searchBox;
    public Button addToBucketButton;


    ArrayList<Item> myBucket = new ArrayList<>();

    IMeeting meeting;
    Item selectedItem;
    ObservableList<Item> itemsOnList = null;
    ObservableList<LocalItem> itemsInTable = null;
    ObservableList<LocalItem> itemsInResultTable = null;

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
        itemList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItem = itemList.getSelectionModel().getSelectedItem();
                    printResult();
                });
    }

    public void createList() {
        ObservableList<Item> asd = null;
        System.out.println(meeting);
        try {
            asd = FXCollections.observableArrayList(
                    meeting.getAllItems());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        itemList.setItems(asd);
    }

    public void printResult() {
        if (selectedItem != null) {
            itemsInTable = FXCollections.observableArrayList(new LocalItem(selectedItem.getName(), selectedItem.getCategory(),
                    selectedItem.getDescription(), selectedItem.getPrice(), selectedItem.getQuantity(),
                    selectedItem.getId()));

            productID.setCellValueFactory(cellData -> cellData.getValue().idProperty);
            productName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
            productCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty);
            productDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty);
            productPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty);
            productQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty);
            productTable.setItems(itemsInTable);
            productTable.getItems().forEach(System.out::println);
        } else cleanTable();
    }

    private void cleanTable() {
//        ObservableList<Item> itemsInTable = FXCollections.observableArrayList(new Item("", "",
//                "", "", selectedItem.getQuantity(), selectedItem.getId()));
        productTable.setItems(null);

    }

    public void addItemToBucket() {
        Item localItem = selectedItem;
        if (localItem != null) {
            myBucket.add(localItem);
            //            meeting.buyItem(selectedItem);
            System.out.println(itemsOnList);
            removeFromStock(localItem);
        }
    }

    public void removeFromStock(Item item) {
        itemsInTable.remove(item);
        int quantity = item.getQuantity();
        if (quantity == 1) {
            itemsOnList.remove(item);

        } else
            item.setQuantity(--quantity);


        new BuyEvent(item);
        updateList(item);
    }

    private void updateList(Item item) {

        cleanTable();
        printResult();
//        itemsInTable.add(item);
//        itemList.setItems(itemsOnList);
//        productTable.setItems(itemsInTable);
    }

    @FXML
    public void searchHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchName(searchBox.getText());
        }
    }

    @FXML
    public void searchByCategoryHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchCategory(searchCategoryBox.getText());
        }
    }


    @FXML
    public void searchByDescriptionHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchDescription(searchDescripionBox.getText());
        }
    }

    private void searchCategory(String text) {
        ArrayList<Item> foundList = null;
        try {
            foundList = meeting.findItemsByCategory(text);
            System.out.println(foundList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (foundList != null)
            printSearchResults(foundList);
        else new NothingFound();
    }

    private void searchDescription(String text) {
        ArrayList<Item> foundList = null;
        try {
            foundList = meeting.findItemsByDescription(text);
            System.out.println(foundList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (foundList != null)
            printSearchResults(foundList);
        else new NothingFound();
    }

    private void searchName(String text) {
        ArrayList<Item> foundList = null;
        try {
            foundList = meeting.findItemsByName(text);
            System.out.println(foundList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (foundList != null)
            printSearchResults(foundList);
        else new NothingFound();
    }

    private void printSearchResults(ArrayList<Item> foundList) {
        itemsInResultTable = FXCollections.observableArrayList();
        for (Item item : foundList) {
            itemsInResultTable.add(new LocalItem(item.getName(), item.getCategory(), item.getDescription(), item.getPrice(),
                    item.getQuantity(), item.getId()));
        }

        foundProductID.setCellValueFactory(cellData -> cellData.getValue().idProperty);
        foundProductName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
        foundProductCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty);
        foundProductDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty);
        foundProductPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty);
        foundProductQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty);
        foundProductTable.setItems(itemsInResultTable);
    }

}

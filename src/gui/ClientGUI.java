package gui;

import engine.Item;
import gui.events.BuyEvent;
import gui.events.CompleteEvent;
import gui.events.NothingFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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
    public TextField searchDescriptionBox;
    public TextField searchCategoryBox;
    public TextField searchBox;
    public Button addToBucketButton;
    public Button searchAddToBucketButton;
    public Tab bucketPane;

    public TableView<LocalItem> bucketProductTable;
    public TableColumn<LocalItem, Number> bucketProductID;
    public TableColumn<LocalItem, String> bucketProductName;
    public TableColumn<LocalItem, String> bucketProductCategory;
    public TableColumn<LocalItem, String> bucketProductDescription;
    public TableColumn<LocalItem, Number> bucketProductPrice;
    public TableColumn<LocalItem, Number> bucketProductQuantity;
    public Button refreshList;
    public Button buyItems;
    public Button removeItem;

    User user;
    IMeeting meeting;
    Item selectedItem;
    LocalItem selectedItemFound;
    ObservableList<Item> itemsOnList = null;
    ObservableList<LocalItem> itemsInTable = null;
    ObservableList<LocalItem> itemsInResultTable = null;
    ObservableList<LocalItem> itemsInBucketTable = null;

    @FXML
    private void initialize() {
        meeting = new MeetingClient().connectToServer();

        createList();
        itemList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItem = itemList.getSelectionModel().getSelectedItem();
                    printResult();
                });
        foundProductTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItemFound = foundProductTable.getSelectionModel().getSelectedItem();
                });
        setUser();

    }

    public void setUser() {
        User local = null;
        try {
            local = meeting.getLastLogged();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.user = local;
        System.out.println("USER " + this.user);
    }

    public void createList() {
        try {
            itemsOnList = FXCollections.observableArrayList(
                    meeting.getAllItems());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        itemList.setItems(itemsOnList);
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
        productTable.setItems(null);
    }

    public void addItemToBucket() {
        if (selectedItem != null) {
            try {
                meeting.addToBucket(user, selectedItem);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            bucketPane.setDisable(false);
            printBucket();
            removeFromLocalStock(selectedItem);
        }
    }

    public void searchAddItemToBucket() {

        try {
            meeting.addToBucket(user, selectedItemFound);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(itemsOnList);
        bucketPane.setDisable(false);
        removeFromLocalStock(selectedItemFound);
        itemsInResultTable.remove(selectedItemFound);
        printBucket();
    }

    public void removeFromLocalStock(Item item) {
        int quantity = item.getQuantity();
        System.out.println(quantity);
        if (quantity == 1) {
            itemList.getItems().remove(item);
            itemsOnList.remove(item);
            cleanTable();
        } else
            item.setQuantity(--quantity);
        new BuyEvent(item);
        updateList();
    }

    private void updateList() {

        cleanTable();
        printResult();
    }

    @FXML
    public void searchHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchDescriptionBox.setText("");
            searchCategoryBox.setText("");
            searchName(searchBox.getText());
        }
    }

    @FXML
    public void searchByCategoryHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchDescriptionBox.setText("");
            searchBox.setText("");
            searchCategory(searchCategoryBox.getText());
        }
    }


    @FXML
    public void searchByDescriptionHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchBox.setText("");
            searchCategoryBox.setText("");
            searchDescription(searchDescriptionBox.getText());
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
        if (foundList.size() != 0)
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
        if (foundList.size() != 0)
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
        if (foundList.size() != 0)
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

    private void printBucket() {
        itemsInBucketTable = FXCollections.observableArrayList();
        try {
            for (Item item : meeting.getUserBucket(user)) {
                itemsInBucketTable.add(new LocalItem(item.getName(), item.getCategory(), item.getDescription(), item.getPrice(),
                        1, item.getId()));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        bucketProductID.setCellValueFactory(cellData -> cellData.getValue().idProperty);
        bucketProductName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
        bucketProductCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty);
        bucketProductDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty);
        bucketProductPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty);
        bucketProductQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty);
        bucketProductTable.setItems(itemsInBucketTable);
    }

    public void removeFromBucket() {
        try {
            meeting.removeItem(selectedItem);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void buyItemsFromBucket() {
        ArrayList<Item> userBucket = null;
        try {
            userBucket = meeting.getUserBucket(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (Item item : userBucket) {
            try {
                meeting.removeItem(item);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        new CompleteEvent(userBucket);

    }
}

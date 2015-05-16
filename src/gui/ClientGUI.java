package gui;

import engine.Item;
import gui.events.AddToBucketEvent;
import gui.events.CompleteEvent;
import gui.events.NothingFound;
import javafx.beans.property.SimpleIntegerProperty;
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
    public TabPane tab;

    ArrayList<Item> checked = new ArrayList<>();
    User user;
    IMeeting meeting;
    Item selectedItem;
    LocalItem selectedItemFound;
    LocalItem selectedItemInBucket;
    ObservableList<Item> itemsOnList = null;
    ObservableList<LocalItem> itemsInTable = null;
    ObservableList<LocalItem> itemsInResultTable = null;
    ObservableList<LocalItem> itemsInBucketTable = null;
    int id;

    @FXML
    private void initialize() {
        id = Login.staticId;
        meeting = new MeetingClient().connectToServer();
        setUser();
        try {
            if (meeting.getUserBucket(user) != null) {
                bucketPane.setDisable(false);
            } else
                bucketPane.setDisable(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        createList();
        printBucket();
        itemList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItem = itemList.getSelectionModel().getSelectedItem();
                    printResult();
                });
        foundProductTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItemFound = foundProductTable.getSelectionModel().getSelectedItem();
                });
        bucketProductTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItemInBucket = bucketProductTable.getSelectionModel().getSelectedItem();
                });

    }

    public void setUser() {
        User local = null;
        try {
            local = meeting.getLogged(id);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.user = local;
        try {
            if (local != null) {
                meeting.clearBucket(local);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void createList() {

        try {
            itemsOnList = FXCollections.observableArrayList(
                    meeting.getAllItems());
            ArrayList<Item> arr = new ArrayList<>(itemsOnList);
            arr = meeting.sortItems(arr);
            itemsOnList = FXCollections.observableList(arr);
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
            removeFromLocalStock(selectedItem);
            printBucket();
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
        new AddToBucketEvent(item);
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
        ArrayList<Item> local = new ArrayList<>();
        try {
            local = meeting.getUserBucket(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        for (Item item : local) {
            itemsInBucketTable.add(new LocalItem(item.getName(), item.getCategory(), item.getDescription(), item.getPrice(),
                    1, item.getId()));
        }
        System.out.println("Wypisuje koszyk " + itemsInBucketTable);
        for (int i = 0; i < itemsInBucketTable.size() - 1; i++) {
            for (int j = i + 1; j < itemsInBucketTable.size(); j++) {
                if (!isChecked(itemsInBucketTable.get(i))) {
                    checked.add(itemsInBucketTable.get(i));
                    if (itemsInBucketTable.get(i).getId() == itemsInBucketTable.get(j).getId())
                        increaseQuantity(itemsInBucketTable.get(i));
                }
            }
        }
        for (int i = 0; i < itemsInBucketTable.size() - 1; i++) {
            for (int j = i + 1; j < itemsInBucketTable.size(); j++) {
                if (isTheSameItem(itemsInBucketTable.get(i), itemsInBucketTable.get(j))) {
                    deleteItem(itemsInBucketTable.get(j));
                }
            }
        }
        System.out.println("Wypisuje koszyk " + itemsInBucketTable);

        bucketProductID.setCellValueFactory(cellData -> cellData.getValue().idProperty);
        bucketProductName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
        bucketProductCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty);
        bucketProductDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty);
        bucketProductPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty);
        bucketProductQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty);
        bucketProductTable.setItems(itemsInBucketTable);
    }

    public void removeFromBucket() {
        if (selectedItemInBucket != null) {
            System.out.println("Koszyk lokalny " + user.getMyBucket());
            System.out.println("Przedmiot w koszyku " + selectedItemInBucket);
            Item toDelete = new Item(selectedItemInBucket.getName(), selectedItemInBucket.getCategory(),
                    selectedItemInBucket.getDescription(), selectedItemInBucket.getPrice(), selectedItemInBucket.getQuantity(), selectedItemInBucket.getId());
            try {
                meeting.removeFromBucket(user, toDelete);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            printBucket();
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
        cleanTable();

    }

    public boolean isChecked(Item item) {
        for (Item item1 : checked) {
            if (item1.getId() == item.getId())
                return true;
        }
        return false;
    }

    public void increaseQuantity(LocalItem item) {
        System.out.println("Ilosc przedmiotow przed " + item + " : " + item.getQuantity());
        int local = item.getQuantity();
        item.setQuantity(++local);
        System.out.println("Ilosc przedmiotow po " + item + " : " + item.getQuantity());
        item.quantityProperty = new SimpleIntegerProperty(local);
    }

    public boolean isTheSameItem(Item item, Item item2) {
        return item.getId() == item2.getId();
    }

    public void deleteItem(Item item) {
        itemsInBucketTable.remove(item);
    }
}

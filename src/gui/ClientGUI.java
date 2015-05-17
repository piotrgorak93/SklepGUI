package gui;

import engine.Item;
import gui.events.AddToBucketEvent;
import gui.events.CompleteEvent;
import gui.events.NoFunctionEvent;
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
import java.util.HashMap;

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

    HashMap<Item, Integer> checked = new HashMap<>();
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
                    printListOfItems();
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
            cleanBucketTable();
            ArrayList<Item> arr = new ArrayList<>(itemsOnList);
            arr = meeting.sortItems(arr);
            itemsOnList = FXCollections.observableList(arr);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        itemList.setItems(itemsOnList);
    }

    public void printListOfItems() {
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

    public boolean isInBucket(Item item) {
        ArrayList<Item> bucket = new ArrayList<>();

        try {
            bucket = meeting.getUserBucket(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (Item item1 : bucket) {
            if (item1.getId() == item.getId()) {
                return true;
            }
        }

        return false;
    }

    public void cleanBucketTable() {
        try {
            meeting.clearBucket(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        bucketProductTable.setItems(null);
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
//        if (selectedItemFound != null) {
//            System.out.println(selectedItemFound);
//            Item local = new Item(selectedItemFound.getName(), selectedItemFound.getCategory(),
//                    selectedItemFound.getDescription(), selectedItemFound.getPrice(), selectedItemFound.getQuantity(), selectedItemFound.getQuantity());
//            try {
//                meeting.addToBucket(user, local);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Found " + local);
//            System.out.println("Wyniki: " + itemsInResultTable);
//            removeFromLocalStock(local);
//            cleanResultTable();
//            //ArrayList<Item> temp = deleteFromResultTable(local);
//            //printSearchResults(temp);
//            printBucket();
//
//        }
        new NoFunctionEvent();
    }

    private ArrayList<Item> deleteFromResultTable(Item local) {
        ArrayList<Item> toReturn = new ArrayList<>();
        for (LocalItem localItem : itemsInResultTable) {
            toReturn.add(new Item(localItem.getName(), localItem.getCategory(),
                    localItem.getDescription(), localItem.getPrice(), localItem.getQuantity(), localItem.getId()));

        }
        for (Item item : toReturn) {
            if (item.getId() == local.getId()) {
                if (item.getQuantity() == 1)
                    toReturn.remove(item);
                else {
                    int temp = item.getQuantity();
                    item.setQuantity(--temp);
                }
            }
        }
        return toReturn;
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

    public void removeFromSearch() {
        if (selectedItemFound != null) {
            Item toDelete = new Item(selectedItemFound.getName(), selectedItemFound.getCategory(),
                    selectedItemFound.getDescription(), selectedItemFound.getPrice(),
                    selectedItemFound.getQuantity(), selectedItemFound.getId());
            try {
                meeting.removeFromBucket(user, toDelete);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            printBucket();
            resetItemToStock(toDelete);
            printListOfItems();
        }
    }

    public void cleanResultTable() {
        foundProductTable.setItems(null);
    }

    public void resetItemToStock(Item item) {
        for (Item item1 : itemsOnList) {
            if (item.getId() == item1.getId()) {
                System.out.println(item.getQuantity());
                int globQ = item1.getQuantity();
                item1.setQuantity(++globQ);
            }
        }
    }

    private void updateList() {
        cleanTable();
        printListOfItems();
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
            System.out.println("Znaleziono : " + foundList);
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
            System.out.println("Znaleziono : " + foundList);
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
            System.out.println("Znaleziono : " + foundList);
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

    private boolean checkIfMapContainsKey(Item key) {
        for (Item item : checked.keySet()) {
            if (item.getId() == key.getId())
                return true;
        }
        return false;
    }

    private int returnValueFromMap(Item item) {
        for (Item item1 : checked.keySet()) {
            if (item1.getId() == item.getId())
                return checked.get(item1);
        }
        return 0;
    }

    private void addOneToQuantity(Item item, int quantity) {
        for (Item item1 : checked.keySet()) {
            if (item1.getId() == item.getId()) {
                checked.put(item1, quantity);
            }
        }
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
        for (Item item : local) {
            if (!checkIfMapContainsKey(item)) {
                checked.put(item, 1);
            } else {
                System.out.println(item);
                int temp = returnValueFromMap(item);
                addOneToQuantity(item, ++temp);
            }
        }

        ObservableList<LocalItem> list = createTable();
        bucketProductID.setCellValueFactory(cellData -> cellData.getValue().idProperty);
        bucketProductName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
        bucketProductCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty);
        bucketProductDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty);
        bucketProductPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty);
        bucketProductQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty);
        bucketProductTable.setItems(list);
        checked.clear();
        local.clear();
        for (LocalItem localItem : list) {
            local.add(new Item(localItem.getName(), localItem.getCategory(), localItem.getDescription(),
                    localItem.getPrice(), localItem.getQuantity(), localItem.getId()));
        }
    }


    public ObservableList<LocalItem> createTable() {
        ObservableList<LocalItem> localItems = FXCollections.observableArrayList();
        for (Item item : checked.keySet()) {
            localItems.add(new LocalItem(item.getName(), item.getCategory(), item.getDescription(),
                    item.getPrice(), returnValueFromMap(item), item.getId()));
        }
        return localItems;

    }

    public void removeFromBucket() {
        if (selectedItemInBucket != null) {
            Item toDelete = new Item(selectedItemInBucket.getName(), selectedItemInBucket.getCategory(),
                    selectedItemInBucket.getDescription(), selectedItemInBucket.getPrice(),
                    selectedItemInBucket.getQuantity(), selectedItemInBucket.getId());
            try {
                meeting.removeFromBucket(user, toDelete);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            printBucket();
            resetItemToStock(toDelete);
            printListOfItems();
        }
    }

    public void buyItemsFromBucket() {
        ArrayList<Item> userBucket = null;
        ArrayList<Item> newDBContent = new ArrayList<>();
        try {
            userBucket = meeting.getUserBucket(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (Item item : itemsOnList) {
            newDBContent.add(new Item(item.getName(), item.getCategory(), item.getDescription(),
                    item.getPrice(), item.getQuantity(), item.getId()));
        }
        try {
            meeting.updateDatabase(newDBContent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        new CompleteEvent(userBucket);
        cleanTable();
        cleanBucketTable();
    }
}

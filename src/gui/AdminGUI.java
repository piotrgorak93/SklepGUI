package gui;

import engine.Item;
import gui.events.NothingFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.NumberStringConverter;
import rmi.rmiTestClient.MeetingClient;
import rmi.rmiTestMeeting.IMeeting;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class AdminGUI {
    public ListView<Item> itemListAdmin;
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
    public TableView<LocalItem> dbProductTable;
    public TableColumn<LocalItem, Number> dbProductID;
    public TableColumn<LocalItem, String> dbProductName;
    public TableColumn<LocalItem, String> dbProductCategory;
    public TableColumn<LocalItem, String> dbProductDescription;
    public TableColumn<LocalItem, Number> dbProductPrice;
    public TableColumn<LocalItem, Number> dbProductQuantity;
    public Button addToDB;
    public Button removeFromDB;
    public Button saveToDB;
    IMeeting meeting;
    private ObservableList<Item> itemsOnList;
    @FXML
    private Item selectedItem;
    private ObservableList<LocalItem> itemsInTable;
    private ObservableList<LocalItem> itemsInResultTable;
    ArrayList<Item> downloadedFromServer;
    private ArrayList<Item> localDatabase;
    private Item selectedItemDB;

    @FXML
    private void initialize() {

        meeting = new MeetingClient().connectToServer();
        listenerAdd();
        listenerRemove();
        listenerSave();
        createList();
        printDatabaseEdit();
        itemListAdmin.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItem = itemListAdmin.getSelectionModel().getSelectedItem();
                    printResult();

                });
        dbProductTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItemDB = dbProductTable.getSelectionModel().getSelectedItem();
                });
    }

    private void printResult() {
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

    private void printDatabaseEdit() {


        ObservableList<LocalItem> itemsToPrint = FXCollections.observableArrayList();
        System.out.println("FUNKCJA: " + itemsToPrint);

        try {
            downloadedFromServer = meeting.getAllItems();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (downloadedFromServer != null) {
            for (Item item : downloadedFromServer) {
                itemsToPrint.add(new LocalItem(item.getName(), item.getCategory(), item.getDescription(), item.getPrice(), item.getQuantity(), item.getId()));
            }

            System.out.println("DRUKUJE LISTE: " + itemsToPrint);
            dbProductName.setCellFactory(TextFieldTableCell.forTableColumn());
            dbProductName.setOnEditCommit(
                    event -> event.getTableView().getItems().get(
                            event.getTablePosition().getRow()).setNewName(event.getNewValue())
            );
            dbProductID.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            dbProductID.setOnEditCommit(
                    event -> event.getTableView().getItems().get(
                            event.getTablePosition().getRow()).setNewID(event.getNewValue())
            );
            dbProductCategory.setCellFactory(TextFieldTableCell.forTableColumn());
            dbProductCategory.setOnEditCommit(
                    event -> event.getTableView().getItems().get(
                            event.getTablePosition().getRow()).setNewCategory(event.getNewValue())
            );
            dbProductDescription.setCellFactory(TextFieldTableCell.forTableColumn());
            dbProductDescription.setOnEditCommit(
                    event -> event.getTableView().getItems().get(
                            event.getTablePosition().getRow()).setNewDescription(event.getNewValue())
            );
            dbProductQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            dbProductQuantity.setOnEditCommit(
                    event -> event.getTableView().getItems().get(
                            event.getTablePosition().getRow()).setNewQuantity(event.getNewValue())
            );
            dbProductPrice.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            dbProductPrice.setOnEditCommit(
                    event -> event.getTableView().getItems().get(
                            event.getTablePosition().getRow()).setNewPrice(event.getNewValue())
            );

            dbProductID.setCellValueFactory(cellData -> cellData.getValue().idProperty);
            dbProductName.setCellValueFactory(cellData -> cellData.getValue().nameProperty);
            dbProductCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty);
            dbProductDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty);
            dbProductPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty);
            dbProductQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty);
            dbProductTable.setItems(itemsToPrint);
        }

    }


    public void createList() {
        try {
            itemsOnList = FXCollections.observableArrayList(
                    meeting.getAllItems());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        itemListAdmin.setItems(itemsOnList);
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

    private void cleanTable() {
        productTable.setItems(null);
    }

    public void addToDatabase(Item item) {
        try {
            meeting.addItem(item);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void removeFromDatabase(Item item) {
        try {
            meeting.removeItem(item);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void saveDatabase() {
        try {
            meeting.updateDatabase(downloadedFromServer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void listenerAdd() {
        addToDB.setOnAction(event -> addToDatabase(selectedItemDB));
    }

    public void listenerRemove() {
        removeFromDB.setOnAction(event -> removeFromDatabase(selectedItemDB));
    }

    public void listenerSave() {
        saveToDB.setOnAction(event -> saveDatabase());
    }

}

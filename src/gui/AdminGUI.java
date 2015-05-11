package gui;

import engine.Item;
import gui.events.NothingFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
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
    IMeeting meeting;
    private ObservableList<Item> itemsOnList;
    @FXML
    private Item selectedItem;
    private ObservableList<LocalItem> itemsInTable;
    private ObservableList<LocalItem> itemsInResultTable;

    @FXML
    private void initialize() {

        meeting = new MeetingClient().connectToServer();
        createList();
        itemListAdmin.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedItem = itemListAdmin.getSelectionModel().getSelectedItem();
                    printResult();
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

}

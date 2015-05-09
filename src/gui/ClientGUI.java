package gui;

import engine.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rmi.rmiTestClient.MeetingClient;
import rmi.rmiTestMeeting.IMeeting;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class ClientGUI {
    @FXML
    ListView<Item> itemList;
    public ImageView image;


    @FXML
    private void initialize() {
        IMeeting meeting = new MeetingClient().connectToServer();
        try {
            ObservableList<Item> items = FXCollections.observableArrayList(meeting.getAllItems());
            itemList.setItems(items);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Image im = new Image("file:1.png");
        System.out.println(im);
        System.out.println(image.getImage());

        // image = new ImageView(im);


    }

}

package rmi.rmiTestMeeting;

import engine.Item;
import gui.User;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IMeeting extends Remote {
    void addItem(Item item) throws RemoteException;

    void buyItem(Item item) throws RemoteException;

    void removeItem(Item item) throws RemoteException;

    void decreaseQuantity(Item item) throws RemoteException;

    void addItems(ArrayList<Item> items, User user) throws RemoteException;

    ArrayList<Item> getAllItems() throws RemoteException;

    ArrayList<Item> findItemsByName(String name) throws RemoteException;

    ArrayList<Item> findItemsByCategory(String category) throws RemoteException;

    ArrayList<Item> findItemsByDescription(String description) throws RemoteException;

    ArrayList<Item> sortItems(ArrayList<Item> itemArrayList) throws RemoteException;

    ArrayList<Item> sortReverseItems(ArrayList<Item> itemArrayList) throws RemoteException;

    void removeFromBucket(User user, Item item) throws RemoteException;


    User getUserByName(String name) throws RemoteException;

    void addToLogged(User user, int id) throws RemoteException;

    User getLogged(int id) throws RemoteException;

    ArrayList<Item> getUserBucket(User user) throws RemoteException;


    void addToBucket(User user, Item item) throws RemoteException;


    void clearBucket(User user) throws RemoteException;


    boolean validateUser(User user) throws RemoteException;


    void updateDatabase(ArrayList<Item> localDatabase) throws RemoteException;


}
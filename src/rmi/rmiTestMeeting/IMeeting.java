package rmi.rmiTestMeeting;

import engine.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IMeeting extends Remote {
    void addItem(Item item) throws RemoteException;

    void buyItem(Item item) throws RemoteException;

    void removeItem(Item item) throws RemoteException;

    ArrayList<Item> getAllItems() throws RemoteException;

    ArrayList<Item> findItemsByName(String name) throws RemoteException;

    ArrayList<Item> findItemsByCategory(String category) throws RemoteException;

    ArrayList<Item> findItemsByDescription(String description) throws RemoteException;

    ArrayList<Item> sortItems(ArrayList<Item> itemArrayList) throws RemoteException;

    ArrayList<Item> sortReverseItems(ArrayList<Item> itemArrayList) throws RemoteException;


}
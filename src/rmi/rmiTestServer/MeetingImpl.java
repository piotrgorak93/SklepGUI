package rmi.rmiTestServer;

import engine.Database;
import engine.Item;
import gui.events.AddEvent;
import gui.events.BuyEvent;
import gui.events.RemoveEvent;
import rmi.rmiTestMeeting.IMeeting;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

public class MeetingImpl extends UnicastRemoteObject implements IMeeting {
    private static final long serialVersionUID = 1L;
    private Database database;

    public MeetingImpl() throws RemoteException {
        database = new Database();
    }


    @Override
    public void addItem(Item item) throws RemoteException {
        database.addItemToDatabase(item);
        new AddEvent();
    }

    @Override
    public void buyItem(Item item) throws RemoteException {
        database.removeItemFromDatabase(item);
        new BuyEvent();
    }

    @Override
    public void removeItem(Item item) throws RemoteException {
        database.removeItemFromDatabase(item);
        new RemoveEvent();
    }

    @Override
    public ArrayList<Item> getAllItems() throws RemoteException {
        return database.getItemArrayList();
    }

    @Override
    public ArrayList<Item> findItemsByName(String name) throws RemoteException {
        return database.getItemByName(name);
    }

    @Override
    public ArrayList<Item> findItemsByCategory(String category) throws RemoteException {
        return database.getItemByCategory(category);
    }

    @Override
    public ArrayList<Item> findItemsByDescription(String description) throws RemoteException {
        return database.getItemByDescription(description);
    }

    @Override
    public ArrayList<Item> sortItems(ArrayList<Item> itemArrayList) throws RemoteException {
        Collections.sort(itemArrayList, Item.itemNameComparator);
        return itemArrayList;
    }

    @Override
    public ArrayList<Item> sortReverseItems(ArrayList<Item> itemArrayList) throws RemoteException {
        Collections.sort(itemArrayList, Item.itemNameComparatorReverse);
        return itemArrayList;
    }
}

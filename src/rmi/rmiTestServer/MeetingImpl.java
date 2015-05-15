package rmi.rmiTestServer;

import engine.Database;
import engine.Item;
import gui.User;
import gui.events.AddEvent;
import gui.events.BuyEvent;
import rmi.rmiTestMeeting.IMeeting;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

public class MeetingImpl extends UnicastRemoteObject implements IMeeting {
    private static final long serialVersionUID = 1L;
    private Database database;
    ArrayList<User> userList = new ArrayList<>();
    User lastLogged;

    public MeetingImpl() throws RemoteException {
        database = new Database();
        userList.add(new User("admin", "admin"));
        userList.add(new User("user", "user"));
    }

    @Override
    public User getUserByName(String name) {
        for (User user1 : userList) {
            if (user1.nazwa.equals(name)) return user1;
        }
        return null;
    }

    @Override
    public void setLastLogged(User user) {
        this.lastLogged = user;
    }

    @Override
    public User getLastLogged() {
        return lastLogged;
    }

    @Override
    public ArrayList<Item> getUserBucket(User user) {
        return getUserByName(user.nazwa).getMyBucket();
    }

    @Override
    public void addToBucket(User user, Item item) {

        findUserOnList(user).addToMyBucket(item);
    }

    @Override
    public void removeFromBucket(User user, Item item) {
        int index = this.userList.indexOf(user);
        this.userList.get(index).removeFromBucket(item);
    }

    @Override
    public void clearBucket(User user) {
        int index = this.userList.indexOf(user);
        this.userList.get(index).clearBucket();
    }

    @Override
    public boolean validateUser(User user) {
        for (User user1 : userList) {
            if (user1.nazwa.equals(user.nazwa) && user1.haslo.equals(user.haslo)) return true;
        }
        return false;
    }

    @Override
    public void addItem(Item item) throws RemoteException {
        database.addItemToDatabase(item);
        new AddEvent();
    }

    @Override
    public void buyItem(Item item) throws RemoteException {
        database.removeItemFromDatabase(item);
        new BuyEvent(item);
    }

    @Override
    public void removeItem(Item item) throws RemoteException {

//        new RemoveEvent(item);
        database.removeItemFromDatabase(item);

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

    @Override
    public void updateDatabase(ArrayList<Item> localDatabase) {
        this.database.updateDatabase(localDatabase);
    }

    public User findUserOnList(User user) {

        for (User user1 : userList) {
            if (user1.nazwa.equals(user.nazwa))
                return user1;
        }

        return null;
    }
}

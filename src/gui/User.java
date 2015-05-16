package gui;

import engine.Item;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-04.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public String nazwa;
    public String haslo;
    ArrayList<Item> myBucket = new ArrayList<>();

    public User(String nazwa, String haslo) {
        this.nazwa = nazwa;
        this.haslo = haslo;
    }

    public ArrayList<Item> getMyBucket() {
        return myBucket;
    }

    public void addToMyBucket(Item item) {
        this.myBucket.add(item);
    }

    public void removeFromBucket(Item item) {
        for (int i = 0; i < myBucket.size(); i++) {
            if (myBucket.get(i).getId() == item.getId()) {
                myBucket.remove(i);
                break;
            }
        }
    }

    public void clearBucket() {
        this.myBucket.clear();
    }
}

package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Database {
    private ArrayList<Item> itemArrayList;

    public Database() {
        itemArrayList = new ArrayList<Item>(
                Arrays.<Item>asList(
                        new Item("Komputer biurowy", "Komputer", "8GB RAM, Intel i3", 2000, 5),
                        new Item("Komputer do gier", "Komputer", "16GB RAM, Intel i7", 4000, 4),
                        new Item("Komputer multimedialny", "Komputer", "4GB RAM, AMD Athlon", 2100, 6),
                        new Item("Apple iPhone 6", "Telefon", "Najnowszy iPhone, robi super zdjêcia", 3500, 10),
                        new Item("Samsung Galaxy S5", "Telefon", "4GB RAM, 12Mpix, kup teraz!", 2700, 8),
                        new Item("Zegarek poz³acany", "Zegarek", "Szykowny zegarek firmy ROLEX", 1500, 5)


                ));
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void addItemToDatabase(Item item) {
        getItemArrayList().add(item);
    }

    public void removeItemFromDatabase(Item item) {
        itemArrayList.remove(getItemIndexByItem(item));

    }

    public int getItemIndexByItem(Item item) {
        return itemArrayList.indexOf(item);
    }

    public ArrayList<Item> getItemByName(String name) {
        ArrayList<Item> itemsToReturn = null;
        for (Item item : itemArrayList) {
            if (item.getName().equalsIgnoreCase(name))
                itemsToReturn.add(item);
        }

        return itemsToReturn;
    }

    public ArrayList<Item> getItemByDescription(String description) {
        ArrayList<Item> itemsToReturn = null;
        for (Item item : itemArrayList) {
            if (item.getDescription().contains(description))
                itemsToReturn.add(item);
        }

        return itemsToReturn;
    }

    public ArrayList<Item> getItemByCategory(String category) {
        ArrayList<Item> itemsToReturn = null;
        for (Item item : itemArrayList) {
            if (item.getCategory().equalsIgnoreCase(category))
                itemsToReturn.add(item);
        }

        return itemsToReturn;
    }
}

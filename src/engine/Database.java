package engine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Database {
    private ArrayList<Item> itemArrayList;
    ArrayList<Item> itemsToReturn = new ArrayList<>();

    public Database() {
        itemArrayList = new ArrayList<Item>(
                Arrays.<Item>asList(
                        new Item("Komputer biurowy", "Komputer", "8GB RAM, Intel i3", 2000, 5, 1),
                        new Item("Komputer do gier", "Komputer", "16GB RAM, Intel i7", 4000, 4, 2),
                        new Item("Komputer multimedialny", "Komputer", "4GB RAM, AMD Athlon", 2100, 6, 3),
                        new Item("Apple iPhone 6", "Telefon", "Najnowszy iPhone, robi super zdjecia", 3500, 10, 4),
                        new Item("Samsung Galaxy S5", "Telefon", "4GB RAM, 12Mpix, kup teraz!", 1700, 8, 5),
                        new Item("Zegarek pozlacany", "Zegarek", "Szykowny zegarek firmy ROLEX", 1500, 5, 6),
                        new Item("Komputer biurowy DELL Inspiron 14 3451", "Komputer", "Intel Celeron N2840, 2GB RAM, 500GB HDD, W8.1", 1099, 2, 7),
                        new Item("Komputer do gier ASUS N551JM", "Komputer", "i7, 12 GB RAM, 1TB HDD, GTX 860 W8.1", 4200, 1, 8),
                        new Item("Komputer do gier Alienware M18x", "Komputer", "i7, 32 GB RAM, 2TB HDD, GTX 880 W8.1", 16999, 1, 9),
                        new Item("Komputer multimedialny HP 15-R233NW", "Komputer", "i3, 8 GB RAM, 1TB HDD, GF 820 M, W8.1", 2399, 3, 10),
                        new Item("Wiedzmin 3 Wild Hunt", "Gra komputerowa", "PC GAME PL, PEGI 18", 130, 10, 11),
                        new Item("Sony Xperia T3", "Telefon", "Smartphone, Android 4.4 8Mpix", 800, 3, 12),
                        new Item("Microsoft Lumia 535", "Telefon", "Smartphone, Windows Phone 8.1, 5Mpix", 450, 2, 13),
                        new Item("Samsung Galaxy S6", "Telefon", "Smartphone, Android 5, 16 Mpix", 2699, 1, 14),
                        new Item("Nak³adanie folii na ekran", "Uslugi", "Tanio, szybko, profesjonalnie", 10, 100, 15)


                ));
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void addItemToDatabase(Item item) {
        getItemArrayList().add(item);
    }

    public void removeItemFromDatabase(Item item) {
        int quantity = item.getQuantity();
        if (quantity == 1) {
            itemArrayList.remove(item);
            System.out.println("USUWAM Z BAZY");
            System.out.println("LISTA PRZEDMIOTOW " + itemArrayList);
        } else
            item.setQuantity(--quantity);

    }

    public ArrayList<Item> getItemByName(String name) {
        itemsToReturn.clear();
        for (Item item : itemArrayList) {
            if (item.getName().toLowerCase().contains(name))
                itemsToReturn.add(item);
        }
        System.out.println("Zwracam: " + itemsToReturn);
        return itemsToReturn;
    }


    public ArrayList<Item> getItemByCategory(String category) {

        itemsToReturn.clear();
        System.out.println("Lista: " + itemsToReturn);
        if (!category.equalsIgnoreCase(""))
            for (Item item : itemArrayList) {
                if (item.getCategory().equalsIgnoreCase(category)) {

                    itemsToReturn.add(itemsToReturn.size(), item);

                }
            }
        System.out.println("Zwracam: " + itemsToReturn);
        return itemsToReturn;
    }

    public ArrayList<Item> getItemByDescription(String description) {
        itemsToReturn.clear();
        System.out.println("Lista: " + itemsToReturn);
        if (!description.equalsIgnoreCase(""))
            for (Item item : itemArrayList) {
                if (item.getDescription().toLowerCase().contains(description)) {
                    itemsToReturn.add(item);
                }

            }
        System.out.println("Zwracam: " + itemsToReturn);
        return itemsToReturn;
    }
}

package engine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Database {
    private final ArrayList<Item> itemArrayList = new ArrayList<Item>(
            Arrays.<Item>asList(
                    new Item("Komputer biurowy", "Komputer", "8GB RAM, Intel i3", 2000, 5),
                    new Item("Komputer do gier", "Komputer", "16GB RAM, Intel i7", 4000, 4),
                    new Item("Komputer multimedialny", "Komputer", "4GB RAM, AMD Athlon", 2100, 6),
                    new Item("Apple iPhone 6", "Telefon", "Najnowszy iPhone, robi super zdjêcia", 3500, 10),
                    new Item("Samsung Galaxy S5", "Telefon", "4GB RAM, 12Mpix, kup teraz!", 2700, 8),
                    new Item("Zegarek poz³acany", "Zegarek", "Szykowny zegarek firmy ROLEX", 1500, 5)


            ));


    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }
}

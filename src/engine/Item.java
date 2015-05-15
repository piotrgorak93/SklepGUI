package engine;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Item implements Serializable {
    private static final long serialVersionUID = -1360412267536229201L;

    private final String category;
    private final String name;


    private final String description;
    private final double price;
    private int quantity;
    private final int id;

    public Item(String name, String category, String description, double price, int quantity, int id) {


        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public static Comparator<Item> itemNameComparator = (item1, item2) -> {
        String item1Name = item1.getName().toUpperCase();
        String item2Name = item2.getName().toUpperCase();
        return item1Name.compareTo(item2Name);
    };
    public static Comparator<Item> itemNameComparatorReverse = (item1, item2) -> {
        String item1Name = item1.getName().toUpperCase();
        String item2Name = item2.getName().toUpperCase();
        return item2Name.compareTo(item1Name);
    };

    @Override
    public String toString() {
        return getName();
    }
}

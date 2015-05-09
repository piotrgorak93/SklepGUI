package engine;

import java.util.Comparator;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Item {
    private final String category;
    private final String name;
    private final String description;
    private final double price;
    private final int quantity;
    private final int id;
    private static int staticId = 0;

    public Item(String name, String category, String description, double price, int quantity) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.id = ++staticId;
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
}

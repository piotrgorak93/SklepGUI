package engine;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Item {
    private final String category;
    private final String name;
    private final String description;
    private final double price;
    private final int quantity;

    public Item(String name, String category, String description, double price, int quantity) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
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
}

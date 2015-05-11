package gui;

import javafx.beans.property.*;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-11.
 */
public class LocalItem {
    public final StringProperty nameProperty;
    public final StringProperty categoryProperty;
    public final StringProperty descriptionProperty;
    public final DoubleProperty priceProperty;
    public final IntegerProperty quantityProperty;
    public final IntegerProperty idProperty;
    public final String description;
    public final double price;
    public int quantity;
    public final int id;
    public final String category;
    public final String name;

    public LocalItem(String name, String category, String description, double price, int quantity, int id) {
        this.nameProperty = new SimpleStringProperty(name);
        this.categoryProperty = new SimpleStringProperty(category);
        this.descriptionProperty = new SimpleStringProperty(description);
        this.priceProperty = new SimpleDoubleProperty(price);
        this.quantityProperty = new SimpleIntegerProperty(quantity);
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
        this.idProperty = new SimpleIntegerProperty(this.id);
    }
}

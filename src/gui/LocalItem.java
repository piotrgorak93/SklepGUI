package gui;

import javafx.beans.property.*;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-11.
 */
public class LocalItem {
    public StringProperty nameProperty;
    public StringProperty categoryProperty;
    public StringProperty descriptionProperty;
    public DoubleProperty priceProperty;
    public IntegerProperty quantityProperty;
    public IntegerProperty idProperty;
    public String description;
    public double price;
    public int quantity;
    public int id;
    public String category;
    public String name;

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

    public void setNewName(String newValue) {
        this.name = newValue;
        this.nameProperty = new SimpleStringProperty(newValue);
    }

    public void setNewID(Number newValue) {
        int intval = newValue.intValue();
        this.id = intval;
        this.idProperty = new SimpleIntegerProperty(intval);
    }

    public void setNewCategory(String newValue) {
        this.category = newValue;
        this.categoryProperty = new SimpleStringProperty(newValue);
    }

    public void setNewDescription(String newValue) {
        this.description = newValue;
        this.descriptionProperty = new SimpleStringProperty(newValue);
    }

    public void setNewPrice(Number newValue) {
        double doubleval = newValue.doubleValue();
        this.price = (double) newValue;
        this.priceProperty = new SimpleDoubleProperty(doubleval);
    }

    public void setNewQuantity(Number newValue) {
        int intval = newValue.intValue();
        this.quantity = intval;
        this.quantityProperty = new SimpleIntegerProperty(intval);
    }
}

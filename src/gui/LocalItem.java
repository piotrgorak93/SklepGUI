package gui;

import engine.Item;
import javafx.beans.property.*;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-11.
 */
public class LocalItem extends Item {
    public StringProperty nameProperty;
    public StringProperty categoryProperty;
    public StringProperty descriptionProperty;
    public DoubleProperty priceProperty;
    public IntegerProperty quantityProperty;
    public IntegerProperty idProperty;


    public LocalItem(String name, String category, String description, double price, int quantity, int id) {
        super(name, category, description, price, quantity, id);
        this.nameProperty = new SimpleStringProperty(name);
        this.categoryProperty = new SimpleStringProperty(category);
        this.descriptionProperty = new SimpleStringProperty(description);
        this.priceProperty = new SimpleDoubleProperty(price);
        this.quantityProperty = new SimpleIntegerProperty(quantity);
        this.idProperty = new SimpleIntegerProperty(id);
    }

    public void setNewName(String newValue) {
        this.nameProperty = new SimpleStringProperty(newValue);
    }

    public void setNewID(Number newValue) {
        this.idProperty = new SimpleIntegerProperty(newValue.intValue());
    }

    public void setNewCategory(String newValue) {
        this.categoryProperty = new SimpleStringProperty(newValue);
    }

    public void setNewDescription(String newValue) {
        this.descriptionProperty = new SimpleStringProperty(newValue);
    }

    public void setNewPrice(Number newValue) {
        System.out.println("USTAWIAM NOWA CENE z " + this.priceProperty + " na " + newValue);
        this.priceProperty = new SimpleDoubleProperty(newValue.doubleValue());
    }

    public void setNewQuantity(Number newValue) {
        this.quantityProperty = new SimpleIntegerProperty(newValue.intValue());
    }
}

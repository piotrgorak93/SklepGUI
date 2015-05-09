package engine;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Item implements java.io.Serializable {
    private static final long serialVersionUID = -1360412267536229201L;

    private final String category;
    private final String name;
    private final String description;
    private final double price;
    private final int quantity;
    private final int id;
    private static int staticId = 0;
    private Image img;

    public Item(String name, String category, String description, double price, int quantity) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.id = ++staticId;
        String plik = "../img/" + this.id + 1 + ".png";

//        this.img = new Image(Item.class.getResourceAsStream("1.png"));
        System.out.println(this.img);


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

    public Image getImg() {
        System.out.println(img);
        return img;
    }
}

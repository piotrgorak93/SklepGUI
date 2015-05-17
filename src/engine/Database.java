package engine;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Database {
    private ArrayList<Item> itemArrayList;
    ArrayList<Item> itemsToReturn = new ArrayList<>();
    ArrayList<Item> listFromXML = new ArrayList<>();

    public Database() {
        try {

            File fXmlFile = new File("database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    listFromXML.add(new Item(eElement.getElementsByTagName("name").item(0).getTextContent(),
                            eElement.getElementsByTagName("category").item(0).getTextContent(),
                            eElement.getElementsByTagName("description").item(0).getTextContent(),
                            Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("quantity").item(0).getTextContent()),
                            Integer.parseInt(eElement.getAttribute("id"))));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemArrayList = listFromXML;
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
        } else
            item.setQuantity(--quantity);

    }

    public ArrayList<Item> getItemByName(String name) {
        itemsToReturn.clear();
        for (Item item : itemArrayList) {
            if (item.getName().toLowerCase().contains(name))
                itemsToReturn.add(item);
        }
        return itemsToReturn;
    }


    public ArrayList<Item> getItemByCategory(String category) {

        itemsToReturn.clear();
        if (!category.equalsIgnoreCase(""))
            for (Item item : itemArrayList) {
                if (item.getCategory().toLowerCase().contains(category)) {

                    itemsToReturn.add(itemsToReturn.size(), item);

                }
            }
        return itemsToReturn;
    }

    public ArrayList<Item> getItemByDescription(String description) {
        itemsToReturn.clear();
        if (!description.toLowerCase().contains(description))
            for (Item item : itemArrayList) {
                if (item.getDescription().toLowerCase().contains(description)) {
                    itemsToReturn.add(item);
                }

            }
        return itemsToReturn;
    }

    public void updateDatabase(ArrayList<Item> database) {
        System.out.println("Baza danych zostala zaktualizowana");
        this.itemArrayList = database;
    }

    public void decreaseQuantity(Item item) {
        int temp = 0;
        for (Item item1 : itemArrayList) {
            if (item1.getId() == item.getId()) {
                temp = item1.getQuantity();
                if (temp > 1)
                    item1.setQuantity(--temp);
                else
                    itemArrayList.remove(item1);
            }
        }
        System.out.println("Zmniejszy³em liczbe, by³o " + temp + " jest" + itemArrayList);
    }
}

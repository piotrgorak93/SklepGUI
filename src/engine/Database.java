package engine;


import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        this.itemArrayList = database;
        saveToXML();
        System.out.println("Baza danych zostala zaktualizowana");
    }

    public void saveToXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("items");
            doc.appendChild(rootElement);
            for (Item item : this.itemArrayList) {
                Element staff = doc.createElement("item");
                rootElement.appendChild(staff);
                Attr attr = doc.createAttribute("id");
                attr.setValue(Integer.toString(item.getId()));
                staff.setAttributeNode(attr);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(item.getName()));
                staff.appendChild(name);
                Element category = doc.createElement("category");
                category.appendChild(doc.createTextNode(item.getCategory()));
                staff.appendChild(category);
                Element description = doc.createElement("description");
                description.appendChild(doc.createTextNode(item.getDescription()));
                staff.appendChild(description);
                Element price = doc.createElement("price");
                price.appendChild(doc.createTextNode(Double.toString(item.getPrice())));
                staff.appendChild(price);
                Element quantity = doc.createElement("quantity");
                quantity.appendChild(doc.createTextNode(Integer.toString(item.getQuantity())));
                staff.appendChild(quantity);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("database.xml"));

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Item class model an item, it hold the information of the item
 * This item implement serializable for data storage
 * @author samuel zhu
 * @version 1.0
 */
public class Item implements Serializable {

    private String name; //hold the name for the item
    private String url; //hold the url for the website
    private double price; //hold the price for the item
    private int quantity; //hold the quantity for the item

    /**
     * Initialize an Item object that hold the item information
     * @param name the name for the item
     * @param url the url for the website
     * @param price the price for the item
     * @param quantity the quantity for the item
     */
    public Item(String name, String url, double price, int quantity) {
        if (name==null || url==null || price<0 || quantity<0)
            throw new IllegalArgumentException("Invalid attribute for item");
        this.name = name;
        this.url = url;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Convert the items object in the array list to 2d array
     * @param items array list holding the items object
     * @return the String array display in the table
     */
    public static ArrayList<Object[]> convertItem(ArrayList<Item> items) {
        ArrayList<Object[]> objects = new ArrayList<>();
        for (int i=0; i<items.size(); i++) { //loop through the items array list
            objects.add(items.get(i).toArray(i+1)); //convert the item object to array to display in the table
        }
        return objects;
    }

    /**
     * convert this object to array
     * @return the object array contain information of number, name, url, price, and quantity
     */
    public Object[] toArray(int number) {
        return new Object[] {number, getName(), getUrl(), getPrice(),getQuantity()};
    }

    /**
     * Getter for the name
     * @return String of the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the url for the website
     * @return String of the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter for the price
     * @return double for the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter for the quantity
     * @return int for the quantity
     */
    public int getQuantity() {
        return quantity;
    }
}

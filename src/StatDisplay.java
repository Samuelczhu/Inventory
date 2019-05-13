import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class is a panel for statistic display, it display the number of types, total quantity, and total price of the items in the table
 *
 * @author samuel zhu
 * @version 1.0
 */
public class StatDisplay extends JPanel {

    private int types; //the number of types

    private int totalQuantity; //hold the total quantity: the total number of items

    private double totalPrice; //hold the total price for the items

    private JLabel typesLabel; //the display for types
    private JLabel totalQuantityLabel; //the display for totalQuantity
    private JLabel totalPriceLabel; //the display for total price

    /**
     * Constructor to create and setup the panel
     */
    public StatDisplay() {
        super();
        this.setLayout(new GridLayout(1,3)); //1 row 3 column grid
        this.setBorder(new EmptyBorder(5,5,10,5));

        //default everything to 0
        types=0;
        totalQuantity=0;
        totalPrice = 0;

        //create the labels
        typesLabel = new JLabel(String.valueOf(types));
        typesLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 17));
        typesLabel.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5),new TitledBorder(Strings.types())));

        totalQuantityLabel = new JLabel(String.valueOf(totalQuantity));
        totalQuantityLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 17));
        totalQuantityLabel.setBorder(new TitledBorder(Strings.totalQuantity()));

        totalPriceLabel = new JLabel(String.valueOf(totalPrice));
        totalPriceLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 17));
        totalPriceLabel.setBorder(new TitledBorder(Strings.totalPrice()));

        //add the labels to this panel
        this.add(typesLabel);
        this.add(totalQuantityLabel);
        this.add(totalPriceLabel);
    }

    /**
     * Calculate and display the statistic
     * @param items the items to perform statistic
     */
    public void displayStat(ArrayList<Item> items) {
        //reset all the stats to 0
        types=0;
        totalQuantity=0;
        totalPrice=0;

        for (;types<items.size();types++) { //use type as the index
            Item item = items.get(types);
            totalQuantity += item.getQuantity();
            totalPrice += item.getPrice() * item.getQuantity();
        }

        //update the UI for the stat display
        typesLabel.setText(String.valueOf(types));
        totalQuantityLabel.setText(String.valueOf(totalQuantity));
        totalPriceLabel.setText(new DecimalFormat("#0.00").format(totalPrice)); //format of price **.**
    }

    /**
     * Update the language of the titled border
     */
    public void updateLanguage() {
        typesLabel.setBorder(new TitledBorder(Strings.types()));
        totalQuantityLabel.setBorder(new TitledBorder(Strings.totalQuantity()));
        totalPriceLabel.setBorder(new TitledBorder(Strings.totalPrice()));
    }
}

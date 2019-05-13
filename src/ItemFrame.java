import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * This class is a frame that allows users to edit or add a new Item
 *
 * @author samuelzhu
 * @version 1.0
 */
public class ItemFrame extends JFrame implements ActionListener {

    private Inventory parent; //the parent frame

    private boolean isEdit; //true if this frame is for editing, false if this frame is for adding

    //hold the array list to modify
    private ArrayList<Item> items;

    //hold the index to edit if for edit
    private int index;

    //the main container
    private Container mainContainer;

    // the title label
    private JLabel titleLabel;

    //the label for the text inputs
    private JLabel nameLabel;
    private JLabel urlLabel;
    private JLabel priceLabel;
    private JLabel quantityLabel;

    //the textFields for the text inputs
    private JTextField nameInput;
    private JTextField urlInput;
    private JTextField priceInput;
    private JTextField quantityInput;

    //the buttons for cancel and OK
    private JButton cancelButton;
    private JButton OKButton;


    /**
     * Constructor for adding a new Item
     * @param parent The parent object
     * @param items the array list of items where the new item will be added
     */
    public ItemFrame(Inventory parent, ArrayList<Item> items) {
        super(Strings.addItem()); //the title
        //setup the icon for adding new item
        ImageIcon addIcon = new ImageIcon("icons\\add_icon.png");
        this.setIconImage(addIcon.getImage());
        this.parent = parent;
        this.items = items;
        this.isEdit=false; //this frame is for adding if this constructor is called

        //setup the frame
        setupFrame();
    }

    /**
     * Constructor for editing an existing item
     * @param parent the parent object
     * @param items the array list of items contained the item need to be modified
     * @param index the index for the item need to be modified in the items array list
     */
    public ItemFrame(Inventory parent, ArrayList<Item> items, int index) {
        super(Strings.editItem()); //the title
        //setup the icon for editing
        ImageIcon editIcon = new ImageIcon("icons\\edit_icon.png");
        this.setIconImage(editIcon.getImage());
        this.parent = parent;
        this.items = items;
        this.isEdit=true; //this frame is for editing if this constructor is called
        this.index = index; //the index of the item to edit

        //setup the frame
        setupFrame();

        //fill the frame with the item information
        fillFrame();
    }

    /**
     * setup the UI in the frame
     */
    private void setupFrame() {
        this.setSize(new Dimension(500,400));
        mainContainer = this.getContentPane();

        //setup the title
        setupTitlePanel();

        //setup the center of the panel
        setupCenterPanel();
        //make it visible
        this.setVisible(true);
        this.setResizable(false); //make this frame not resizable
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exiting();
            }
        });
    }

    /**
     * Exit this frame
     */
    private void exiting() {
        parent.freeItemFrame(); //free the item frame in parent
        this.dispose(); //close this frame
    }

    /**
     * Setup the title panel
     */
    private void setupTitlePanel() {
        JPanel titleP = new JPanel();
        titleP.setLayout(new GridLayout(1,1));
        titleP.setBorder(new EmptyBorder(20,5,5,5));
        titleLabel = new JLabel((isEdit) ? (Strings.editItem()) : (Strings.addItem()), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 25));
        titleP.add(titleLabel);
        mainContainer.add(titleP,BorderLayout.NORTH);
    }

    /**
     * Setup the center of the panel
     */
    private void setupCenterPanel() {
        //the center panel
        JPanel centerP = new JPanel();
        centerP.setLayout(new BoxLayout(centerP, BoxLayout.Y_AXIS));

        //the center grid
        JPanel centerG = new JPanel();
        centerG.setLayout(new GridLayout(4,2, 10,2));
        centerG.setBorder(new EmptyBorder(30,10,30,70));

        //create all the labels
        nameLabel = new JLabel(Strings.name(), SwingConstants.RIGHT);
        urlLabel = new JLabel(Strings.url(), SwingConstants.RIGHT);
        priceLabel = new JLabel(Strings.price(), SwingConstants.RIGHT);
        quantityLabel = new JLabel(Strings.quantity(), SwingConstants.RIGHT);

        //create all the textField
        nameInput = new JTextField();
        urlInput = new JTextField();
        priceInput = new JTextField();
        quantityInput = new JTextField();

        //add the labels and textFields to the grid
        centerG.add(nameLabel);
        centerG.add(nameInput);
        centerG.add(urlLabel);
        centerG.add(urlInput);
        centerG.add(priceLabel);
        centerG.add(priceInput);
        centerG.add(quantityLabel);
        centerG.add(quantityInput);

        //setup the button panel
        JPanel buttonP = new JPanel();
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
        buttonP.setBorder(new EmptyBorder(20,10,70,10));
        //setup the buttons
        cancelButton = new JButton(Strings.cancel());
        cancelButton.addActionListener(this);
        OKButton = new JButton(Strings.OK());
        OKButton.addActionListener(this);
        //add the buttons to the panel
        buttonP.add(Box.createHorizontalGlue());
        buttonP.add(cancelButton);
        buttonP.add(Box.createHorizontalGlue());
        buttonP.add(OKButton);
        buttonP.add(Box.createHorizontalGlue());

        //setup the center panel
        centerP.add(centerG);
        centerP.add(buttonP);
        //add the center panel to the main container
        mainContainer.add(centerP, BorderLayout.CENTER);
    }

    /**
     * Fill the information in the frame with the item to be edited, this is work for editing
     */
    private void fillFrame() {
        Item item = items.get(index); //get the item need to be edited
        nameInput.setText(item.getName()); //fill the item name in the name input
        urlInput.setText(item.getUrl()); //fill the item url in the url input
        priceInput.setText(String.valueOf(item.getPrice())); //fill the item price in the price input
        quantityInput.setText(String.valueOf(item.getQuantity())); //fill the item quantity in the quantity input
    }

    /**
     * Update the language: Chinese or English
     */
    public void updateLanguage() {
        if (isEdit) { //editing item
            this.setTitle(Strings.editItem());
            titleLabel.setText(Strings.editItem());
        } else { //adding item
            this.setTitle(Strings.addItem());
            titleLabel.setText(Strings.addItem());
        }

        //update languages for the labels for inputs
        nameLabel.setText(Strings.name());
        urlLabel.setText(Strings.url());
        priceLabel.setText(Strings.price());
        quantityLabel.setText(Strings.quantity());

        //update languages for the buttons
        cancelButton.setText(Strings.cancel());
        OKButton.setText(Strings.OK());
    }

    /**
     * implement the action event listener
     * @param e the action event performed
     */
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o instanceof JButton){
            JButton button = (JButton) o; //cast to button
            if (button == cancelButton) { //cancel this frame
                exiting();
            }
            if (button == OKButton) { //finish the edit or add action
                try {
                    //get all the inputs
                    String name = nameInput.getText(); //get the name
                    String url = urlInput.getText(); //get the url
                    String priceS = priceInput.getText(); //get the text of the price
                    //check for valid price
                    if (priceS.contains(".") && priceS.substring(priceS.indexOf(".")).length()>3) { //at most 2 decimal places
                        throw new IllegalArgumentException("Invalid price, at most 2 decimal digit for price");
                    }
                    double price = Double.valueOf(priceS);
                    int quantity = Integer.valueOf(quantityInput.getText());

                    //make the item with the corresponding attributes
                    Item item = new Item(name,url,price,quantity);
                    if (isEdit) { //editing the item
                        items.remove(index); //remove the old item
                        items.add(index,item); //add the new item back to that index
                        parent.statusBar.setText(Strings.itemEdited()); //update status for edit
                    } else { //adding the item
                        items.add(item); //add the new item to the back of the items array list
                        parent.statusBar.setText(Strings.itemAdded()); //update status for add
                    }

                    //update the UI in the parent frame
                    //update the table along with the statistic
                    parent.updateTable();
                    parent.selectTableRow(index); //select the row that was edited
                    parent.freeItemFrame(); //free the item frame in parent
                    this.dispose(); //close this frame, job done
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, Strings.invalidInput());
                    parent.statusBar.setText(Strings.invalidInput());
                }
            }
        }
    }
}

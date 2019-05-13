import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This class model a inventory that hold the item information
 * It is designed for the convenient to buy Elec items in Taobao
 *
 * @author samuel zhu
 * @version 1.0
 */
public class Inventory extends JFrame implements ActionListener {

    //the data file objects for the setting and table data
    private DataFile dataFile;

    //The name of the column of the table
    private String[] columnName = Strings.columnNames();

    //the items in the table
    private ArrayList<Item> items;
    //stack to hold the deleted items
    private Stack<Item> stackItems;

    //boolean hold whether the item is modified or not
    private boolean isModified;

    private Container mainContainer; //the main container

    //fields for the menu item
    //the file menu
    private JMenu fileMenu; //file menu
    private JMenuItem quitItem; //menu item for quit
    private JMenuItem newItem; //menu item to create a new data file
    private JMenuItem openItem; //menu item for open a file
    private JMenuItem renameItem; //menu item for rename the file
    private JMenuItem saveItem; //menu item to save a file
    private JMenuItem saveAsItem; //menu item for save as file
    private JMenuItem exportItem; //menu item for export file
    private JMenuItem showInExplorerItem; //menu item to show in explorer
    private JMenuItem deleteFileItem; //menu item to delete the current inventory file
    //the edit menu
    private JMenu editMenu; //edit menu
    private JMenuItem addItem; //menu item to add new item
    private JMenuItem editItem; //menu item to edit specific item
    private JMenuItem copyUrlItem; //menu item to copy url to the clipboard
    private JMenuItem deleteItem; //menu item to delete item
    private JMenuItem undoDeleteItem; // menu item to undo the delete action
    private JMenuItem upItem; //menu item to move the selected item upward by one step
    private JMenuItem downItem; //menu item to move he selected item downward by one step
    //the setting menu
    private JMenu settingMenu; //setting menu
    private JMenu languageMenu; //subMenu for language in fileMenu
    //radio button menu items for language: Chinese and English
    public JRadioButtonMenuItem chineseItem;
    public JRadioButtonMenuItem englishItem;

    //status bar
    public JLabel statusBar;

    //main title
    private JLabel titleLabel;
    //the name label for the file
    private JLabel fileNameLabel;

    //center panel
    private JPanel centerPanel;

    //fields for the buttons
    private ModifyButton addButton; //button to add new item
    private ModifyButton removeButton; //button to remove selected item(s)
    private ModifyButton editButton; //button to edit the single selected item
    private ModifyButton upButton; //button to move the selected item up
    private ModifyButton downButton; //button to move the selected item down

    //The table
    private JTable table;
    private DefaultTableModel tableModel; //the table model

    //popup menu for the table
    //popup menu for single selected item
    private JPopupMenu popupMenu; //this popup menu contain copyUrlItem, editItem, and deleteItem
    private JMenuItem copyUrlItem1; //menu item for copying the url of the selected list
    private JMenuItem deleteItem1; //menu item to delete item
    private JMenuItem editItem1; //menu item to edit specific item
    //popup menu for multiple selected items
    private JPopupMenu popupMenuMultiple; //this popup menu contain deleteItem
    private JMenuItem deleteItem2; //menu item for delete item

    //the statistic display panel
    private StatDisplay statDisplay;

    //the item frame for edit or add new item to the items array list
    private ItemFrame itemFrame;

    /**
     * The Inventory constructor, call this constructor to run the program
     */
    public Inventory() {
        //setup the icon
        ImageIcon mainIcon = new ImageIcon("icons\\icon.png");
        this.setIconImage(mainIcon.getImage());
        this.setTitle(Strings.appTitle());
        this.setSize(new Dimension(800,600));
        mainContainer = this.getContentPane();

        //setup the stackItems for undo delete
        stackItems = new Stack<>();

        //set up the menu
        setupMenu();

        //setup the status bar
        statusBar = new JLabel(Strings.welcomeStatus());
        statusBar.setBorder(new LineBorder(Color.BLACK)); //set a line border
        mainContainer.add(statusBar,BorderLayout.SOUTH);

        //set up the main title
        setupTitle();

        //setup the center panel
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        mainContainer.add(centerPanel);

        //set up the buttons
        setupButtons();
        //setup the table
        setupTable();

        //setup the statistic display panel
        statDisplay = new StatDisplay();
        statDisplay.displayStat(items);
        centerPanel.add(statDisplay);

        updateLanguage(); //update the language

        isModified = false; //not modified

        //handling the exit
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exiting();
            }
        });

        this.setVisible(true);
    }

    /**
     * Set the menu of the for this program
     */
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar(); //create the menu bar

        //setup the menus
        fileMenu = new JMenu(Strings.file());
        languageMenu = new JMenu(Strings.language()); //the language menu is a submenu for file menu
        editMenu = new JMenu(Strings.edit());
        settingMenu = new JMenu(Strings.setting());

        //setup the menu items
        //setup quit item, which inside file menu
        quitItem = new JMenuItem(Strings.quit());
        quitItem.addActionListener(this); //add listener for quit item
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        //setup languages item: Chinese and English, which inside language submenu, which inside setting menu
        chineseItem = new JRadioButtonMenuItem(Strings.chinese());
        englishItem = new JRadioButtonMenuItem(Strings.english());
        ButtonGroup languageGroup = new ButtonGroup(); //group for the menu item of languages
        languageGroup.add(chineseItem);
        languageGroup.add(englishItem);
        languageMenu.add(chineseItem);
        languageMenu.add(englishItem);
        chineseItem.setSelected(true);
        chineseItem.addActionListener(this);
        englishItem.addActionListener(this);
        //setup the add item, which inside edit menu
        addItem = new JMenuItem(Strings.addItem());
        addItem.addActionListener(this);
        //setup the delete item, which inside the edit menu
        deleteItem = new JMenuItem(Strings.deleteItem());
        deleteItem.addActionListener(this);
        //setup the edit item, which inside the edit menu
        editItem = new JMenuItem(Strings.editItem());
        editItem.addActionListener(this);
        //setup the undo delete item, which inside the edit menu
        undoDeleteItem = new JMenuItem(Strings.undoDelete());
        undoDeleteItem.addActionListener(this);
        //setup the copy url item, which inside the edit menu
        copyUrlItem = new JMenuItem(Strings.copyUrl());
        copyUrlItem.addActionListener(this);
        //setup the up item, which inside the edit menu
        upItem = new JMenuItem(Strings.up());
        upItem.addActionListener(this);
        //setup the down item, which inside the edit menu
        downItem = new JMenuItem(Strings.down());
        downItem.addActionListener(this);
        //setup the open item, which inside the file menu
        openItem = new JMenuItem(Strings.open());
        openItem.addActionListener(this);
        //setup the save item, which inside the file menu
        saveItem = new JMenuItem(Strings.save());
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveItem.addActionListener(this);
        //setup the save as item, which inside the file menu
        saveAsItem = new JMenuItem(Strings.saveAs());
        saveAsItem.addActionListener(this);
        //setup the export item, which inside the file menu
        exportItem = new JMenuItem(Strings.export());
        exportItem.addActionListener(this);
        //setup the delete file item, which inside the file menu
        deleteFileItem = new JMenuItem(Strings.deleteFile());
        deleteFileItem.addActionListener(this);
        //setup the rename file item, which inside the file menu
        renameItem = new JMenuItem(Strings.rename());
        renameItem.addActionListener(this);
        //setup the show in explorer menu item, which inside the file menu
        showInExplorerItem = new JMenuItem(Strings.showInExplorer());
        showInExplorerItem.addActionListener(this);
        //setup the new menu item, which inside the file menu
        newItem = new JMenuItem(Strings.newFile());
        newItem.addActionListener(this);


        //add the items and menus and set up the menu
        //setup the file menu
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(renameItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(showInExplorerItem);
        fileMenu.addSeparator();
        fileMenu.add(deleteFileItem);
        fileMenu.addSeparator();
        fileMenu.add(quitItem);
        //setup the edit menu
        editMenu.add(addItem);
        editMenu.add(editItem);
        editMenu.addSeparator();
        editMenu.add(deleteItem);
        editMenu.add(undoDeleteItem);
        editMenu.addSeparator();
        editMenu.add(copyUrlItem);
        editMenu.addSeparator();
        editMenu.add(upItem);
        editMenu.add(downItem);
        //setup the setting menu
        settingMenu.add(languageMenu);
        //setup the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(settingMenu);
        this.setJMenuBar(menuBar); //setup the menu bar
    }

    /**
     * set up the main title for this program
     */
    private void setupTitle() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
        titleLabel = new JLabel(Strings.mainTitle(),JLabel.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 30));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        fileNameLabel = new JLabel("",JLabel.CENTER);
        fileNameLabel.setFont(new Font("Helvetica Neue", Font.ITALIC, 20));
        fileNameLabel.setForeground(Color.blue);
        fileNameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);
        titlePanel.add(fileNameLabel);
        titlePanel.setBorder(new EmptyBorder(20,10,20,10));
        mainContainer.add(titlePanel,BorderLayout.NORTH);
    }

    /**
     * setup the buttons on the top of the table inside the center panel
     */
    private void setupButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        //TODO: replace the text with image
        //setup buttons for editing
        //add button
        addButton = new ModifyButton("+",Color.GREEN);
        addButton.addActionListener(this);
        //remove button
        removeButton = new ModifyButton("-", Color.RED);
        removeButton.addActionListener(this);
        //edit button
        editButton = new ModifyButton(Strings.edit(),Color.BLUE);
        editButton.addActionListener(this);

        //setup buttons to move up and down
        upButton = new ModifyButton(Strings.up(),Color.ORANGE);
        upButton.addActionListener(this);
        downButton = new ModifyButton(Strings.down(),Color.MAGENTA);
        downButton.addActionListener(this);

        //add the button to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        centerPanel.add(buttonPanel);
    }

    /**
     * setup the table located in the center panel
     */
    private void setupTable() {
        //create the data file
        dataFile = new DataFile(this);
        //load the data
        try {
            items = dataFile.readDate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Strings.failLoadData());
            items = new ArrayList<>(); //create an empty array list to hold items
        }
        tableModel = new DefaultTableModel() {
            //make the table not editable
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String s:columnName)
            tableModel.addColumn(s);
        table = new JTable(tableModel); //create the table with the data

        //setup the popup menu
        //setup the popup menu for single selected item
        popupMenu = new JPopupMenu();
        //create the menu item inside the popup menu
        copyUrlItem1 = new JMenuItem(Strings.copyUrl());
        copyUrlItem1.addActionListener(this);
        editItem1 = new JMenuItem(Strings.editItem());
        editItem1.addActionListener(this);
        deleteItem1 = new JMenuItem(Strings.deleteItem());
        deleteItem1.addActionListener(this);
        //add the items to the popup menu
        popupMenu.add(copyUrlItem1);
        popupMenu.add(editItem1);
        popupMenu.addSeparator();
        popupMenu.add(deleteItem1);
        //setup the popup menu for multiple selected items
        popupMenuMultiple = new JPopupMenu();
        //create the delete menu item inside the popup menu for multiple selection
        deleteItem2 = new JMenuItem(Strings.deleteItem());
        deleteItem2.addActionListener(this);
        //add the delete item to the popup menu for multiple selection
        popupMenuMultiple.add(deleteItem2);

        //add the mouse listener for the table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.isPopupTrigger()) {
                    JTable source = (JTable) mouseEvent.getSource();
                    int row = source.rowAtPoint(mouseEvent.getPoint());
                    int column = source.columnAtPoint(mouseEvent.getPoint());
                    if (!source.isRowSelected(row))
                        source.changeSelection(row,column,false,false);
                    if (table.getSelectedRowCount()==1) { //popup menu for single selected
                        popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
                    } else { //popup menu for multiple selection
                        popupMenuMultiple.show(mouseEvent.getComponent(), mouseEvent.getX(),mouseEvent.getY());
                    }
                }
            }
        });

        //fill in the row with the loaded data by update the table
        updateTable();

        //add the table to the scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(0,10,10,10),new LineBorder(Color.BLUE))); //set the border
        centerPanel.add(scrollPane);
    }

    /**
     * Update the language in the UI
     */
    private void updateLanguage() {
        columnName = Strings.columnNames(); //column names in the table
        this.setTitle(Strings.appTitle()); //title of this app
        titleLabel.setText(Strings.mainTitle());
        statusBar.setText(Strings.welcomeStatus());
        //update language in the file menu
        fileMenu.setText(Strings.file());
        newItem.setText(Strings.newFile());
        openItem.setText(Strings.open());
        saveItem.setText(Strings.save());
        saveAsItem.setText(Strings.saveAs());
        exportItem.setText(Strings.export());
        deleteFileItem.setText(Strings.deleteFile());
        renameItem.setText(Strings.rename());
        showInExplorerItem.setText(Strings.showInExplorer());
        //update language in the language menu
        languageMenu.setText(Strings.language());
        quitItem.setText(Strings.quit());
        chineseItem.setText(Strings.chinese());
        englishItem.setText(Strings.english());
        //set the language of the table
        columnName = Strings.columnNames();
        //update the language of on the table header
        for (int i=0;i<columnName.length;i++) {
            TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
            column.setHeaderValue(columnName[i]);
        }
        //update the language of the buttons
        editButton.setText(Strings.edit());
        upButton.setText(Strings.up());
        downButton.setText(Strings.down());
        //update the language for stat
        statDisplay.updateLanguage();
        //update the language inside edit menu
        editMenu.setText(Strings.edit());
        addItem.setText(Strings.addItem());
        deleteItem.setText(Strings.deleteItem());
        editItem.setText(Strings.editItem());
        copyUrlItem.setText(Strings.copyUrl());
        upItem.setText(Strings.up());
        downItem.setText(Strings.down());
        //update the language inside popup menu
        copyUrlItem1.setText(Strings.copyUrl());
        editItem1.setText(Strings.editItem());
        deleteItem1.setText(Strings.deleteItem());
        deleteItem2.setText(Strings.deleteItem());
        undoDeleteItem.setText(Strings.undoDelete());
        if (itemFrame!=null)
            itemFrame.updateLanguage(); //update the language if itemFrame is not null
        //update language in the setting menu
        settingMenu.setText(Strings.setting());
        updateFileName();
    }

    /**
     * update the name of the main title along with the file name
     */
    public void updateFileName() {
        File file = dataFile.getFile();
        if (file!=null && file.exists())
            fileNameLabel.setText(file.getName());
        else
            fileNameLabel.setText(Strings.noFile());
    }

    /**
     * Update the table with the items, which also update the statistic
     */
    public void updateTable() {
        //clear the table
        tableModel.setRowCount(0);
        //convert the item to array
        ArrayList<Object[]> objects = Item.convertItem(items);
        //display them to the table
        for (Object[] o:objects)
            tableModel.addRow(o);
        //update the stat if not null
        if (statDisplay!=null)
            statDisplay.displayStat(items);
    }

    /**
     * This method free the item frame, and it is designed to be called by the item frame after the item frame finished its job
     */
    public void freeItemFrame() {
        itemFrame = null; //free the item frame
    }

    /**
     * This method select a row in the table; it is designed to be called by the item frame for editing
     * @param row the index for the row to be selected
     */
    public void selectTableRow(int row) {
        table.setRowSelectionInterval(row,row); //select the row
    }

    /**
     * Pop up a JFrame to add a new Item in the table, only one itemFrame is allowed to popup at a time
     */
    public void addItem() {
        if (itemFrame==null) {//create a new item frame to add new item if no item frame is popup yet
            itemFrame = new ItemFrame(this, items);
            isModified = true;
        }
        else {//inform user that only one item frame allowed to popup at a time
            JOptionPane.showMessageDialog(this, Strings.handleOne());
            statusBar.setText(Strings.handleOne());
        }
    }

    /**
     * Pop up a JFrame to delete a selected item(s) from the table
     */
    public void deleteItem() {
        if (itemFrame!=null) { //user should handle one item at a time
            JOptionPane.showMessageDialog(this, Strings.handleOne());
            statusBar.setText(Strings.handleOne());
            return;
        }
        //get the index for rows to delete
        int[] rowToDelete = table.getSelectedRows();
        //check for delete nothing
        if (rowToDelete.length==0) {
            JOptionPane.showMessageDialog(this,Strings.deleteEmpty());
            statusBar.setText(Strings.deleteEmpty());
            return;
        }
        //delete item(s) with the corresponding index and add them to the stack
        for (int i=0;i<rowToDelete.length;i++) {
            //delete the selected items and push them into the stack
            stackItems.push(items.remove(rowToDelete[i]-i)); //notice that every times a row deleted, the next index to delete is decreased
        }
        //update the table
        updateTable();
        isModified = true;
        statusBar.setText(Strings.itemDeleted()); //update status
    }

    /**
     * Pop up a JFrame to edit a selected item from the table
     */
    public void editItem() {
        if (table.getSelectedRowCount()==1) {
            if (itemFrame==null) {//create the item frame to edit item if no item frame existed yet
                itemFrame = new ItemFrame(this, items, table.getSelectedRow());
                isModified = true;
            }
            else {//inform user to handle one item at a time
                JOptionPane.showMessageDialog(this, Strings.handleOne());
                statusBar.setText(Strings.handleOne());
            }
        } else { //show message that edit only work when single item is selected
            JOptionPane.showMessageDialog(this,Strings.editItemError());
            statusBar.setText(Strings.editItemError());
        }
    }

    /**
     * Move the selected item upward by one step in the table
     */
    private void moveUp() {
        if (itemFrame!=null) { //user should handle one item at a time
            JOptionPane.showMessageDialog(this, Strings.handleOne());
            statusBar.setText(Strings.handleOne());
            return;
        }
        if (table.getSelectedRowCount()==1) {
            //get the index of the selected row
            int index = table.getSelectedRow();
            if (index==0) { //show message that the selected item is on the top already
                JOptionPane.showMessageDialog(this, Strings.onTop());
                statusBar.setText(Strings.onTop());
                return;
            }
            Item item = items.remove(index); //pop the item out
            //move the selected item upward by one step
            items.add(--index,item); //decrement the index by 1, and add the item back

            isModified = true;
            //update the table
            updateTable();
            //keep the item selected
            table.setRowSelectionInterval(index,index);
            //show status
            statusBar.setText(Strings.itemMovedUp());
        } else { //tell the user that only one item is allow to move at a time
            JOptionPane.showMessageDialog(this,Strings.onlyOneAllowMove());
            statusBar.setText(Strings.onlyOneAllowMove());
        }
    }

    /**
     * Move the selected item downward by one step in the table
     */
    private void moveDown() {
        if (itemFrame!=null) { //user should handle one item at a time
            JOptionPane.showMessageDialog(this, Strings.handleOne());
            statusBar.setText(Strings.handleOne());
            return;
        }
        if (table.getSelectedRowCount()==1) {
            //get the index for the selected item
            int index = table.getSelectedRow();
            if (index>=items.size()-1) { //check for index out of bound, inform user that the selected item is already at the bottom
                JOptionPane.showMessageDialog(this,Strings.onBottom());
                statusBar.setText(Strings.onBottom());
                return;
            }
            //pop the selected item out from the items array list
            Item item = items.remove(index);
            //add the item back one step to the bottom by increment the index
            items.add(++index,item);

            isModified = true;
            //update the table
            updateTable();
            //keep the item selected
            table.setRowSelectionInterval(index,index);
            statusBar.setText(Strings.itemMovedDown()); //update status
        } else { //inform user that only one item is allowed to move at a time
            JOptionPane.showMessageDialog(this,Strings.onlyOneAllowMove());
            statusBar.setText(Strings.onlyOneAllowMove());
        }
    }

    /**
     * open a file chooser to let user choose to save the file
     * it is used in save when no file is open, save as, and new for creating file
     * @param file the file directory to open
     * @return true if the file saved successfully, false otherwise
     */
    private boolean chooseToSave(File file) {
        JFileChooser fileChooser = new JFileChooser(file);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory())
                    return true;
                String name = file.getName();
                return name.endsWith(".iv");
            }

            @Override
            public String getDescription() {
                return "*.iv";
            }
        });
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            //get and modify the file name if necessary
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            dataFile.setFile(new File((fileName.endsWith(".iv") ? (fileName) : (fileName+".iv"))));
            try {
                dataFile.storeDate(items);
                updateFileName(); //update the file name
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,Strings.failStoreData()); //inform user failed to store data
                return false;
            }
        }
        return false;
    }

    /**
     * The action listener for this program
     * @param e the event to be deal with
     */
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource(); //get the object to deal with

        //check the object and deal with action
        //deal with menu items
        if (o instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) o; //cast the object to menu item
            if (menuItem == quitItem) { //deal with quit item
                exiting();
            }
            if (menuItem == addItem) {
                addItem();
            }
            if (menuItem == deleteItem || menuItem==deleteItem1 || menuItem==deleteItem2) {
                deleteItem();
            }
            if (menuItem == editItem || menuItem == editItem1) {
                editItem();
            }
            if (menuItem == copyUrlItem || menuItem == copyUrlItem1) {
                if (table.getSelectedRowCount()==1) {
                    int rowIndex = table.getSelectedRow(); //get the selected row index
                    Item selectedItem = items.get(rowIndex); //get the selected item
                    //copy the url to the clipboard
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(new StringSelection(selectedItem.getUrl()), null);
                    statusBar.setText(Strings.urlCopied());
                } else { //display message that can copy url when only one item is selected
                    JOptionPane.showMessageDialog(this,Strings.copyUrlError());
                    statusBar.setText(Strings.copyUrlError());
                }
            }
            if (menuItem == undoDeleteItem) {
                if (!stackItems.empty()) {
                    //pop the item recently deleted and add it to the items array list
                    items.add(stackItems.pop());

                    //update the table
                    updateTable();
                    //update the stat
                    statDisplay.displayStat(items);
                    statusBar.setText(Strings.undoDeleted());//show status
                } else { //show message that nothing is deleted
                    JOptionPane.showMessageDialog(this, Strings.nothingDeleted());
                    statusBar.setText(Strings.nothingDeleted());
                }
            }
            if (menuItem == upItem) { //move the selected item up in the table
                moveUp();
            }
            if (menuItem == downItem){ //move the selected item down in the table
                moveDown();

            }
            if (menuItem == openItem) { //open a file
                //ask to save current file
                if (isModified) { //check whether the data is modified or not
                    boolean wantSave = JOptionPane.showConfirmDialog(this,Strings.saveBeforeExit(),Strings.pleaseConfirm(),JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                    if (wantSave) {
                        saveItem.doClick(); //click the save button for the user
                    }
                }
                //create a file chooser to choose
                JFileChooser fileChooser = new JFileChooser((dataFile.getFile()==null) ? (new File(DataFile.DEFAULT_PATH)) : (dataFile.getFile()));
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        if (file.isDirectory())
                            return true;
                        final String name = file.getName();
                        return name.endsWith(".iv");
                    }

                    @Override
                    public String getDescription() {
                        return "*.iv";
                    }
                });
                int returnValue = fileChooser.showOpenDialog(this);
                if (returnValue==JFileChooser.APPROVE_OPTION) {
                    dataFile.setFile(fileChooser.getSelectedFile());
                    isModified = false; //haven't modified the new data
                    statusBar.setText(Strings.openedNewFile()); //update status
                }
                //load the data
                try {
                    items = dataFile.readDate();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, Strings.failLoadData());
                }
                //update the table
                updateTable();
                updateFileName(); //update the file name
            }
            if (menuItem == saveItem) { //save the items to the current file
                //dealing with not open a file yet
                if (!dataFile.containFile()) {
                    if (chooseToSave(new File(DataFile.DEFAULT_PATH))) { //let the user choose to save starting from the default path
                        statusBar.setText(Strings.saved());
                        isModified = false; //saved
                    } else {
                        statusBar.setText(Strings.notSaved());
                    }
                    return; //finished dealing with new file
                }
                //dealing with already open a file
                try {
                    dataFile.storeDate(items);
                    statusBar.setText(Strings.saved());
                    updateFileName();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, Strings.failStoreData());
                    statusBar.setText(Strings.failLoadData());
                }
            }
            if (menuItem == saveAsItem) { //save the items to a new file
                if (chooseToSave((dataFile.getFile()==null) ? (new File(DataFile.DEFAULT_PATH)) : (dataFile.getFile()))) { //saved successfully
                    statusBar.setText(Strings.saved());
                    isModified = false; //saved
                } else {
                    statusBar.setText(Strings.notSaved());
                }
            }
            if (menuItem == exportItem) { //export the items into text document
                //open the file chooser to let user choose where to export, start from the default directory
                JFileChooser fileChooser = new JFileChooser(new File(DataFile.DEFAULT_PATH));
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if (f.isDirectory())
                            return true;
                        return f.getName().endsWith(".txt"); //export txt file as *.txt
                    }

                    @Override
                    public String getDescription() {
                        return "*.txt";
                    }
                });
                int returnValue = fileChooser.showSaveDialog(this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    //try to export as text
                    try {
                        //get and modify the file name if necessary
                        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                        dataFile.exportText(new File((fileName.endsWith(".txt") ? (fileName) : (fileName+".txt"))),items);
                        statusBar.setText(Strings.exportSuccess()); //inform user exported successfully
                    } catch (Exception ex) { //export failed because of the error
                        //inform user fail to export
                        statusBar.setText(Strings.exportFail());
                        JOptionPane.showMessageDialog(this,Strings.exportFail());
                    }
                }
            }
            if (menuItem == deleteFileItem) { //delete the file that hold the current data
                if (dataFile.deleteFile()) {
                    statusBar.setText(Strings.deleteSuccess());
                    updateFileName();
                }
                else {
                    JOptionPane.showMessageDialog(this, Strings.deleteFail());
                    statusBar.setText(Strings.deleteFail());
                }
            }
            if (menuItem==renameItem) { //rename the current file
                String newName = JOptionPane.showInputDialog(Strings.rename());
                if (newName==null || newName.equals("")) {
                    statusBar.setText(Strings.renameCancel());
                    return;
                }
                if (dataFile.renameFile(newName)) {
                    statusBar.setText(Strings.renameSuccess());
                } else {
                    JOptionPane.showMessageDialog(this,Strings.renameFail());
                    statusBar.setText(Strings.renameFail());
                }
                updateFileName();
            }
            if (menuItem==showInExplorerItem) { //show the current file in explorer
                Desktop desktop = Desktop.getDesktop(); //get the desk top
                try { //open the parent folder contain that file
                    desktop.open(new File(dataFile.getFile().getParent()));
                    statusBar.setText(Strings.openedInExplorer());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,Strings.unableToOpenInExplorer());
                    statusBar.setText(Strings.unableToOpenInExplorer());
                }
            }
            if (menuItem == newItem) { //create a new data file
                if (chooseToSave(new File(DataFile.DEFAULT_PATH))) { //let the user choose to save starting from the default path
                    statusBar.setText(Strings.fileCreated());
                } else {
                    statusBar.setText(Strings.fileNotCreated());
                }
            }
        }

        //dealing with the language radio button menu item
        if (o instanceof JRadioButtonMenuItem) {
            JRadioButtonMenuItem menuItem = (JRadioButtonMenuItem) o;
            if (menuItem == chineseItem) { //set language to chinese
                Strings.setLanguage(Strings.CHINESE);
                updateLanguage(); //update the language in the UI
            } else if (menuItem == englishItem) { //set language to english
                Strings.setLanguage(Strings.ENGLISH);
                updateLanguage(); //update the language in the UI
            }
        }

        //dealing with buttons
        if (o instanceof ModifyButton) {
            ModifyButton button = (ModifyButton) o; //caste the object to JButton
            if (button == addButton)  //deal with adding a new item to the table
                addItem();
            if (button ==removeButton) //deal with deleting selected item(s) from the table
                deleteItem();
            if (button == editButton)  //deal with editing a selected item from the table
                editItem();
            if (button == upButton) //deal with moving a selected item up from the table
                moveUp();
            if (button == downButton) //deal with moving a selected item down from the table
                moveDown();
        }
    }

    /**
     * This method is called before exit to confirm save or not
     */
    private void exiting() {
        if (isModified) { //check whether the data is modified or not
            boolean wantSave = JOptionPane.showConfirmDialog(this,Strings.saveBeforeExit(),Strings.pleaseConfirm(),JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            if (wantSave)
                saveItem.doClick(); //click the save button
        }
        try {
            dataFile.saveSetting(); //save the setting any way
            System.exit(0); //exit the program
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,Strings.failSaveSetting());
        }
    }

    /**
     * The main function for this program
     * @param args the arguments for the main function
     */
    public static void main(String[] args) {
        new Inventory();
    }
}

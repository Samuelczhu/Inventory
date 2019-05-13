import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is use to store and read data stored in the file
 * @author samuel zhu
 * @version 1.0
 */
public class DataFile {

    //the path for the file that stored the settings
    //the first line is the language, the second line is the last open directory
    public static final String DEFAULT_PATH = System.getProperty("user.dir")+File.separator+"files";
    public static final String SETTING_NAME = "setting.txt";

    private String lastOpenDirectory; //the directory for the last open file, which is stored in the LastOpen file in this directory

    private File file; //the file is dealing with
    private File settingFile; //the file that save the setting

    /**
     * Constructor for the data file, which read the last open file directory, default is current directory
     */
    public DataFile(Inventory parent) {
        File defaultDir = new File(DEFAULT_PATH); //the default directory
        defaultDir.mkdirs(); //create the directory
        settingFile = new File(DEFAULT_PATH+File.separator+SETTING_NAME);
        try {
            Scanner settingIn = new Scanner(settingFile);
            String languageS = settingIn.nextLine(); //read the language: 0 for Chinese and 1 for english,
            if (languageS.equals("Chinese")) {
                parent.chineseItem.setSelected(true);
                Strings.setLanguage(Strings.CHINESE);
            }
            if (languageS.equals("English")) {
                parent.englishItem.setSelected(true);
                Strings.setLanguage(Strings.ENGLISH);
            }
            lastOpenDirectory = settingIn.nextLine(); //read the last open directory
            settingIn.close(); //finished reading the setting data
        } catch (FileNotFoundException fNotFoundE) { //the file isn't there, so create one
            try {
                settingFile.createNewFile(); //create a new setting file
                //write the language and lastOpenDirectory, which is the default directory
                lastOpenDirectory = "";
                PrintWriter settingOutput = new PrintWriter(settingFile);
                settingOutput.println(Strings.getLanguage()); //save the language
                settingOutput.println(lastOpenDirectory); //save the last open directory
                settingOutput.close();
            } catch (IOException ioE) {
                //TODO: Delete this println after debugged for fail to create new setting file
                System.out.println(ioE);
            }
        }

        //setup the file for the data
        if (lastOpenDirectory!=null && !lastOpenDirectory.equals("")) {
            file = new File(lastOpenDirectory);
            try {
                file.createNewFile();
            } catch (IOException e) {
                //TODO: inform user failed to create file
                System.out.println(e);
            }
        }
    }

    /**
     * Read the data stored in the file and convert raw data to item object and stored in array list
     * @return The array list of item object from the data
     */
    public ArrayList<Item> readDate() throws IOException, ClassNotFoundException{
        ArrayList<Item> items = new ArrayList<>(); //array list that hold the data
        //loop to read all the data
        try {
            //create inputs
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(fileInputStream);
            while (true) {
                Item item = (Item) input.readObject(); //read the object and cast to item
                items.add(item); //add the item to the array list
            }
        } catch (EOFException endFileException) { /*end of file, finished reading*/ }

        return items; //return the items
    }


    /**
     * Serialize the item and store them into a file
     * @param items array list contain the serializable item object to store
     * @throws FileNotFoundException file not found exception, should be handle when use this method
     * @throws IOException input output exception, should be handle when use this method
     */
    public void storeDate(ArrayList<Item> items) throws FileNotFoundException, IOException{
        FileOutputStream fileOutputStream = new FileOutputStream(file,false); //do not append, clear and rewrite
        ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);
        //loop to write data
        for (Item item : items) {
            output.writeObject(item);
        }
        //close the outputs
        output.close();
        fileOutputStream.close();
    }

    /**
     * Set the current file to file choose by user
     * @param file the file user choose
     */
    public void setFile(File file) {
        this.file = file;
        lastOpenDirectory = file.getAbsolutePath();
    }

    /**
     * Save the setting information into the setting file
     */
    public void saveSetting() throws IOException {
        PrintWriter settingOutput = new PrintWriter(settingFile);
        settingOutput.println(Strings.getLanguage()); //save the language
        if (file!=null && file.exists())
            settingOutput.println(file.getAbsolutePath()); //save to the last open directory
        else
            settingOutput.println(""); //print nothing
        settingOutput.close();
    }

    /**
     * get the current file that can be use for open
     * @return the current file
     */
    public File getFile() {
        return file;
    }

    /**
     * provide information whether contained a file or not
     * @return true if the file is not null, false if the file is null
     */
    public boolean containFile(){
        return file!=null && file.exists();
    }

    /**
     * Rename the file
     * @param newName new name for this file
     * @return true if rename successfully, false otherwise
     */
    public boolean renameFile(String newName) {
        File newFile = new File(file.getParent()+File.separator+newName+".iv");
        boolean success = file.renameTo(newFile);
        if (success) {
            file = newFile;
            return true;
        }
        return false;
    }

    /**
     * Delete the current file
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteFile() {
        if (file.delete()) {
            lastOpenDirectory=null; //deleted
            file = null;
            return true;
        }
        return false;
    }

    /**
     * Export the file as text: *.txt
     * @param file The directory the text will be printed
     * @param items The items array list that contain information needed to be output
     */
    public void exportText(File file, ArrayList<Item> items) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file); //setup to output from the file
        //print the format to the user
        printWriter.println(Strings.exportFormat());
        //loop to print information in the format: #. name website price quantity
        for (int i=0;i<items.size();i++) {
            Item item = items.get(i); //get the individual item
            //print the item information
            printWriter.println((i+1)+". "+item.getName()+"    "+item.getUrl()+"    "+item.getPrice()+"    "+item.getQuantity());
        }
        printWriter.close(); //close the print writer
    }
}

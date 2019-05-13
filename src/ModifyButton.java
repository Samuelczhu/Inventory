import javax.swing.*;
import java.awt.*;

/**
 * This class is use for the buttons for modified list
 * It is designed to be used by the add, delete, edit, up, and down buttons
 * @author samuel zhu
 * @version 1.0
 */
public class ModifyButton extends JButton {

    /**
     * Constructor to setup the button
     * @param name the name for the button
     * @param color the background color for the button, the foreground color is white
     */
    public ModifyButton(String name, Color color) {
        super(name); //setup the name for the button
        this.setFont(new Font("Helvetica Neue", Font.BOLD, 13)); //set the font
        this.setSize(new Dimension(10,10)); //set the size
        this.setBackground(color); //set the background color
        this.setForeground(Color.white); //set the font color
    }
}

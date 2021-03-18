package src;

import javax.swing.*;
import java.awt.*;

public class Solution extends JFrame{
    private JPanel PANEL;

    private JLabel LABEL2;

    private ImageIcon ICON;
    
    

    public Solution(){
    
    super("Choose background color");
    

    
    ICON = new ImageIcon(getClass().getResource("MSFT.png"));
    LABEL2= new JLabel(ICON);
    
    PANEL.setLayout(new BorderLayout());

    LABEL2.setBounds(50, 50, 50, 50);

    PANEL.add(LABEL2);


    add(PANEL);
    
    setVisible(true);



    setDefaultCloseOperation(EXIT_ON_CLOSE);



    setSize(400,400);
    }
    
    public static void main(String[] args) {
    	new Solution();
    }
}
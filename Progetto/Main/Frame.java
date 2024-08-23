package Progetto.Main;

import java.awt.Insets;

import javax.swing.JFrame;

public class Frame extends JFrame{
    
    public Frame() {

        setTitle("Il miglior software della storia");
        //setResizable(false);    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new Panel());

        pack();

        Insets insets = getInsets();
        setSize(Global.FRAME_WIDTH + insets.left + insets.right, Global.FRAME_HEIGHT + insets.top + insets.bottom);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

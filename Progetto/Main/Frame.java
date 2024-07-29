package Progetto.Main;

import javax.swing.JFrame;

public class Frame extends JFrame{
    
    public Frame() {

        setTitle("Il miglior software della storia");
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new Panel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

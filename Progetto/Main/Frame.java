package Progetto.Main;

import javax.swing.JFrame;

public class Frame extends JFrame{
    
    public Frame() {

        setTitle("Il miglior software della storia");
        //setResizable(false);
        setSize(Global.FRAME_WIDTH, Global.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new Panel());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

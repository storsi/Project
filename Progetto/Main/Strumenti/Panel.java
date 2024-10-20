package Progetto.Main.Strumenti;

import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JPanel;

public class Panel extends JPanel{
    
    public Panel(LayoutManager layout, Dimension dim, Color background) {
        
        setLayout(layout);
        setPreferredSize(dim);
        setBackground(background);
    }

    public Panel(LayoutManager layout, Dimension dim, Color background, boolean visible) {
        
        setLayout(layout);
        setPreferredSize(dim);
        setBackground(background);
        setVisible(visible);
    }

    public Panel clone() {
        return new Panel(getLayout(), getPreferredSize(), getBackground());
    }
}

package Progetto.Barre;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

public abstract class Barra extends JPanel{

    public Barra(Dimension dim, Color background, LayoutManager layout) {
        
        super(layout);
        super.setPreferredSize(dim);
        super.setBackground(background);

        setUp();
    }
    
    protected abstract void setUp();
    public abstract void btnIconClicked(BtnIcon btn);
}
package Progetto.Main.Strumenti;

import javax.swing.JPanel;

import Progetto.Main.Global;

import java.awt.Color;
import java.awt.Dimension;

public class BarraDiSeparazione extends JPanel{

    private int width;
    
    public BarraDiSeparazione(int width, Color colore) {

        setWidth(width);
        setBackground(colore);

        setVisible(false);
    }

    public BarraDiSeparazione(int width, Color colore, boolean visible) {

        setWidth(width);
        setBackground(colore);
        setVisible(visible);
    }

    public BarraDiSeparazione(int width) {

        setWidth(width);
        setBackground(Color.BLACK);

        setVisible(false);
    }

    public void setWidth(int width) {
        this.width = width;
        setPreferredSize(new Dimension(width, Global.BARRA_DI_SEPARAZIONE_HEIGHT));

        revalidate();
        repaint();
    }

    @Override
    public int getWidth() {
        return width;
    }
}

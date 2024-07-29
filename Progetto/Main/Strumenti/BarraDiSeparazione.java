package Progetto.Main.Strumenti;

import javax.swing.JPanel;

import Progetto.Main.Global;

import java.awt.Color;
import java.awt.Dimension;

public class BarraDiSeparazione extends JPanel{
    
    public BarraDiSeparazione(int width, Color colore) {

        setWidth(width);
        setBackground(colore);

        setVisible(false);
    }

    public void setWidth(int width) {
        setPreferredSize(new Dimension(width, Global.BARRA_DI_SEPARAZIONE_HEIGHT));
    }
}

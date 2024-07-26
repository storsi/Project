package Progetto.Main.Strumenti;

import javax.swing.JPanel;

import Progetto.Main.Global;

import java.awt.Color;
import java.awt.Dimension;

public class BarraDiSeparazione extends JPanel{
    
    public BarraDiSeparazione(int width, Color colore) {

        setPreferredSize(new Dimension(width, Global.BARRA_DI_SEPARAZIONE_HEIGHT));
        setBackground(colore);

        setVisible(false);
    }
}

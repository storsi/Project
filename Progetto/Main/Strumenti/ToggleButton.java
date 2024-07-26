package Progetto.Main.Strumenti;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import Progetto.Barre.TerzaBarra;

public class ToggleButton extends PanelPerBtn{

    private Palla palla;
    private Barra barra;
    private boolean active;
    private TerzaBarra tb;
    
    public ToggleButton(TerzaBarra tb) {
        
        this.tb = tb;

        setUp();
    }

    private void setUp() {
        setLayout(null);
        setPreferredSize(new Dimension(70, 30));
        setBackground(getBackground());

        palla = new Palla((int)getPreferredSize().getHeight());
        barra = new Barra((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());
        barra.setBackground(palla.getBackground());

        active = false;

        add(palla);
        add(barra);
    }

    @Override
    public void onClick() {
        if(active) palla.setLocation(0, 0);
        else palla.setLocation((int)(getPreferredSize().getWidth() - getPreferredSize().getHeight()), 0);

        active = !active;

        tb.modificaDisposizioneTabelle();
    }

    @Override
    public void inHover() {
        
    }

    @Override
    public void outHover() {
        
    }
}

class Palla extends JPanel {

    private int size;

    public Palla(int size) {
        this.size = size;

        setOpaque(false);
        setBounds(0, 0, size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, size, size, size, size);
    }
}

class Barra extends JPanel {

    private int width, height, x, y;

    public Barra(int width, int height) {
        this.width = (int)(width / 1.5);
        this.height = (int)(height / 1.5);
        x = width / 10;
        y = height / 6;

        setOpaque(false);
        setBounds(x, y, width, this.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(245, 245, 220));
        g2d.fillRoundRect(x, y, width, height / 2, 10, 10);
    }
}

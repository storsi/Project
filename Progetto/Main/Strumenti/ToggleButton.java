package Progetto.Main.Strumenti;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import Progetto.Barre.TerzaBarra;
import Progetto.Main.Interface.Animated;
import Progetto.Main.Interface.Clickable;
import Progetto.Main.Interface.Hover;

public class ToggleButton extends JPanel implements Animated, Hover, Clickable{

    private Palla palla;
    private Barra barra;
    private boolean active, animazioneAttiva, hoverAttivo;
    private TerzaBarra tb;
    private double xPos;
    
    public ToggleButton(TerzaBarra tb) {
        
        this.tb = tb;
        this.xPos = 0;
        this.hoverAttivo = active = animazioneAttiva =  false;

        setUp();
    }

    private void setUp() {
        setLayout(null);
        setPreferredSize(new Dimension(70, 30));
        setBackground(tb.getBackground());
        setAnimationThread();
        setHoverThread();
        addMouseListener(this);

        palla = new Palla((int)getPreferredSize().getHeight());
        barra = new Barra((int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());
        barra.setBackground(palla.getBackground());

        

        add(palla);
        add(barra);
    }

    @Override
    public void afterClick() {

        animazioneAttiva = true;
        attivaThread();
    }

    @Override
    public void inHover() {
        hoverAttivo = true;
        attivaHover();
    }

    @Override
    public void outHover() {
        hoverAttivo = false;
    }

    @Override
    public boolean animationActive() {
        return animazioneAttiva;
    }

    @Override
    public void anima() {
        
        if(active) xPos -= 0.0001;
        else xPos += 0.0001;

        if(xPos >= (int)(getPreferredSize().getWidth() - getPreferredSize().getHeight()) || xPos <= 0) {
            animazioneAttiva = false;
            tb.modificaDisposizioneTabelle();
            active = !active;
        }
        
        palla.setLocation((int)xPos, 0);

        palla.revalidate();
        palla.repaint();
    }

    @Override
    public int getMilliseconds() {
        return 800;
    }

    @Override
    public void hover() {
        //TODO Aggiungere la classe informazione
    }

    @Override
    public boolean hoverActive() {
        return hoverAttivo;
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

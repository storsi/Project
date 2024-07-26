package Progetto.Main.Strumenti;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import Progetto.Main.Global;

public abstract class Overlay extends JPanel{

    private int x, y, width, height;
    
    public Overlay(int width, int height) {

        this.width = width;
        this.height = height;
        x = Global.FRAME_WIDTH - (width + ((int)(Global.FRAME_WIDTH * 0.8) - width) / 2);
        y = (Global.FRAME_HEIGHT - height) / 2;
        
        setBounds(x, y, width, height);
        setBackground(Global.COLORE_AVVISO);
        setOpaque(false);
        setLayout(Global.FL_C_10_10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int raggioCurvatura = 40;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, width, height, raggioCurvatura, raggioCurvatura);
    }
}

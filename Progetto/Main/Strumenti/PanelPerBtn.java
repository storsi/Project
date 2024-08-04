package Progetto.Main.Strumenti;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class PanelPerBtn extends JPanel implements MouseListener{

    private int width, height;
    private Dimension dim;

    public PanelPerBtn() {
        dim = new Dimension();
        setPreferredSize(dim);
        setOpaque(false);

        addMouseListener(this);
    }

    public void changeDimension(int width, int height) {
        this.width = width;
        this.height = height;

        dim.setSize(width, height);
    }

    public Dimension getDimension() {
        return dim;
    }

    public abstract void onClick();
    public abstract void inHover();
    public abstract void outHover();

    @Override
    public void mouseClicked(MouseEvent e) {
        onClick();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        inHover();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        outHover();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int raggioCurvatura = 40;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, width, height, raggioCurvatura, raggioCurvatura);
    }
}

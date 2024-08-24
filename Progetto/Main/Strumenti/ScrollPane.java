package Progetto.Main.Strumenti;

import javax.swing.JScrollPane;
import javax.swing.JComponent;
import javax.swing.JButton;

import Progetto.Main.Global;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollPane extends JScrollPane{
    
    public ScrollPane(Dimension dim, Color colore) {
        
        setPreferredSize(dim);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setBorder(null);
        getVerticalScrollBar().setUnitIncrement(16);
        getVerticalScrollBar().setUI(new ScrollPaneCustom());
        
    }
}

class ScrollPaneCustom extends BasicScrollBarUI {
    
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        Dimension zeroDim = new Dimension(0, 0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = Color.WHITE;
        this.trackColor = Global.COLORE_AVVISO;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        int curva = 10;
        int width = (int)(thumbBounds.width * 0.4);
        int height = (int)(thumbBounds.height * 0.5);

        g.setColor(thumbColor);
        g.fillRoundRect(thumbBounds.x + (thumbBounds.width - width), thumbBounds.y, width, height, curva, curva);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }
}

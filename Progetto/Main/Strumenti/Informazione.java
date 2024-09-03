package Progetto.Main.Strumenti;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;

import Progetto.Main.Global;

public class Informazione extends JLabel{

    private JLayeredPane panel;
    private int profonditaPanel, width, height;
    private Component parent;
    private final int MAX_CHAR = 30;

    public Informazione() {
        
        setFont(Global.FONT_PICCOLO);
        setForeground(Color.BLACK);
        setOpaque(false);
        setHorizontalAlignment(SwingUtilities.CENTER);
        setBackground(new Color(245, 245, 220));
        //setBorder(BorderFactory.createLineBorder(getBackground(), 3));


        setVisible(false);
    }

    public void mostra() {
        int x;

        Point screenPoint = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(screenPoint, panel);

        if(screenPoint.x + width > Global.FRAME_WIDTH) x = screenPoint.x - (screenPoint.x + width - Global.FRAME_WIDTH + 10);
        else x = screenPoint.x;

        setBounds(x, screenPoint.y - height, width, height);

        setVisible(true);
    }

    public void nascondi() {
        setVisible(false);
    }

    public void setTesto(String text) {
        setText(mabageText(text).toUpperCase());
    }

    public JLayeredPane getPanel(Component parent) {
        this.parent = parent;

        if(panel == null) findPanel();

        return panel;
    }

    private String mabageText(String text) {
        String[] paroletesto = text.split(" ");

        String nuovoTesto = "<html><p>";
        boolean aCapo = false;
        int counter = 0;
        width = (text.length() > MAX_CHAR) ? MAX_CHAR * (getFont().getSize() - 3)  : text.length() * (getFont().getSize() - 3);
        height = getFont().getSize() + 10;


        for(int i = 0; i < paroletesto.length; i++) {
            if(aCapo) {
                nuovoTesto += "<br>";
                aCapo = false;
                height += getFont().getSize() + 10;
                counter = 0;
            }

            if(paroletesto[i].contains("<")) {
                switch (paroletesto[i]) {
                    case "<br>": aCapo = true;
                    break;
                    default: nuovoTesto += paroletesto[i];
                    break;
                }
            } else {
                nuovoTesto += paroletesto[i] + " ";
                counter += paroletesto[i].length() + 1;
            }

            if(i < paroletesto.length - 1 && !paroletesto[i + 1].contains("<") && (counter + paroletesto[i + 1].length()) > MAX_CHAR) aCapo = true;
        }

        nuovoTesto += "</p></html>";

        return nuovoTesto;
    }

    private void findPanel() {
        int profondita;
        Component element = parent;
        profonditaPanel = 0;
        
        do{
            element = element.getParent();
        }while(!(element.getClass().getSimpleName().equals("Panel")));

        panel = (JLayeredPane)element;
        
        element = parent;

        do{
            profondita = ((JLayeredPane)panel).getLayer(element);
            profonditaPanel = (profondita > profonditaPanel) ? profondita : profonditaPanel;

            element = element.getParent();
        }while(profondita >= 0);

        panel.add(this, Integer.valueOf(profonditaPanel + 100));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int raggioCurvatura = 20;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), raggioCurvatura, raggioCurvatura);

        super.paintComponent(g);
    }
}
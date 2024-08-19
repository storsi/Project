package Progetto.Main;

import javax.swing.JLayeredPane;

import Progetto.Barre.PrimaBarra;
import Progetto.Barre.SecondaBarra;
import Progetto.Barre.TerzaBarra;

import java.awt.Component;
import java.awt.Dimension;

public class Panel extends JLayeredPane{

    private PrimaBarra pb;
    private SecondaBarra sb;
    private TerzaBarra tb;
    
    public Panel() {

        setLayout(null);
        setBounds(0, 0, Global.FRAME_WIDTH, Global.FRAME_HEIGHT);
        

        tb = new TerzaBarra();
        sb = new SecondaBarra(tb);
        pb = new PrimaBarra(sb);

        add(pb, JLayeredPane.DEFAULT_LAYER);
        add(sb, JLayeredPane.DEFAULT_LAYER);
        add(tb, JLayeredPane.DEFAULT_LAYER);
    }

    public void eliminaElemtno(String elemento) {
        for(Component comp : getComponents()) {
            if(comp.getClass().getSimpleName().equals(elemento)) {
                remove(comp);
                repaint();
                
            }
        }
    }
}

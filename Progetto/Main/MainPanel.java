package Progetto.Main;

import javax.swing.JLayeredPane;

import Progetto.Barre.PopUp;
import Progetto.Barre.PrimaBarra;
import Progetto.Barre.SecondaBarra;
import Progetto.Barre.TerzaBarra;

import java.awt.Component;

public class MainPanel extends JLayeredPane{

    private PrimaBarra pb;
    private SecondaBarra sb;
    private TerzaBarra tb;
    private PopUp popUp;
    
    public MainPanel() {

        setLayout(null);
        setBounds(0, 0, Global.FRAME_WIDTH, Global.FRAME_HEIGHT);

        popUp = new PopUp();
        

        tb = new TerzaBarra(popUp);
        sb = new SecondaBarra(tb);
        pb = new PrimaBarra(sb);

        add(pb, JLayeredPane.DEFAULT_LAYER);
        add(sb, JLayeredPane.DEFAULT_LAYER);
        add(tb, JLayeredPane.DEFAULT_LAYER);
        add(popUp, JLayeredPane.PALETTE_LAYER);
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

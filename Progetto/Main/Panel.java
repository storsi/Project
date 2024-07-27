package Progetto.Main;

import javax.swing.JLayeredPane;

import Progetto.Barre.PrimaBarra;
import Progetto.Barre.SecondaBarra;
import Progetto.Barre.TerzaBarra;

import java.awt.Dimension;

public class Panel extends JLayeredPane{

    private PrimaBarra pb;
    private SecondaBarra sb;
    private TerzaBarra tb;
    
    public Panel() {

        setPreferredSize(new Dimension(Global.FRAME_WIDTH, Global.FRAME_HEIGHT));
        setLayout(Global.FL_C_0_0);

        tb = new TerzaBarra();
        sb = new SecondaBarra(tb);
        pb = new PrimaBarra(sb);

        add(pb, JLayeredPane.DEFAULT_LAYER);
        add(sb, JLayeredPane.DEFAULT_LAYER);
        add(tb, JLayeredPane.DEFAULT_LAYER);
    }
}

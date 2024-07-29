package Progetto.Barre;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Progetto.Main.Global;
import Progetto.Main.Strumenti.BarraDiSeparazione;
import Progetto.Main.Strumenti.PanelPerBtn;

public class PanelTabella extends PanelPerBtn{

    private JLabel lbl_nome;
    private int width, height;
    private boolean lungo;
    private ParteBassa pb;
    private BarraDiSeparazione bds;

    public PanelTabella(String nome, boolean lungo, ParteBassa pb) {
        super();

        this.lungo = lungo;
        this.pb = pb;
        
        setUp(nome);
        setDimension(lungo);

        repaint();
    }

    public void setDimension(boolean lungo) {

        if(lungo) {
            width = (int)(pb.getPreferredSize().getWidth() * 0.9);
            height = (int)(pb.getPreferredSize().getHeight() * 0.05);

            lbl_nome.setPreferredSize(new Dimension((int)(width * 0.4), height / 2));
            bds.setVisible(false);
        } else {
            width = (int)(pb.getPreferredSize().getWidth() * 0.3);
            height = (int)(pb.getPreferredSize().getHeight() * 0.5);

            lbl_nome.setPreferredSize(new Dimension(width, (int)(height * 0.1)));
            bds.setWidth((int)(width * 0.8));
            bds.setVisible(true);
        }

        super.changeDimension(width, height);
    }

    private void setUp(String nome) {
        lbl_nome = new JLabel(nome, SwingConstants.CENTER);
        lbl_nome.setFont(Global.FONT_MEDIO);

        bds = new BarraDiSeparazione(0, Color.BLACK);

        add(lbl_nome);
        add(bds);

        
    }

    @Override
    public void onClick() {

    }

    @Override
    public void inHover() {
        
    }

    @Override
    public void outHover() {

    }
    
}
